/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package daw.controllers;

import daw.modal.Asignatura;
import daw.modal.Usuario;
import daw.modal.UsuarioAsignatura;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.UserTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Antonio
 */
@WebServlet(name = "AsignaturaController", urlPatterns = {"/asignatura/*"})
public class AsignaturaController extends HttpServlet {

    @PersistenceContext(unitName = "UniversidadPU")
    private EntityManager em;
    @Resource
    private UserTransaction utx;
    private static final Logger Log = Logger.getLogger(UsuarioController.class.getName());
    HttpSession session;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String vista;
        String accion = "/main";
        String tipo = request.getServletPath();
        session = request.getSession();
        if (tipo.equals("/asignatura")) {
            if (request.getPathInfo() != null) {
                accion = request.getPathInfo();
            } else {
                accion = "error";
            }
        }
        switch (accion) {
            case "/main":
                vista = "main";
                break;
            case "/listarasignaturas":
                if (session.getAttribute("email") != null) {
                    List<Asignatura> asignaturaList;
                    TypedQuery<Asignatura> asignaturas = em.createNamedQuery("Asignatura.findAll", Asignatura.class);
                    asignaturaList = asignaturas.getResultList();
                    request.setAttribute("asignaturas", asignaturaList);
                    vista = "asignaturalist";
                } else {
                    vista = "error";
                }
                break;
            case "/misasignaturas":
                if (session.getAttribute("email") != null) {
                    request.setAttribute("asignaturas", misAsignaturas(request));
                    vista = "asignaturalist";
                } else {
                    vista = "error";
                }
                break;
            case "/nuevaasignatura":
                vista = "asignaturacreate";
                break;
            case "/matricula":
                if (session.getAttribute("email") != null) {
                    request.setAttribute("misasignaturas", misAsignaturas(request));
                    request.setAttribute("misasignaturasnomatriculadas", misAsignaturasNoMatriculadas(request));
                    vista = "matriculacion";
                } else {
                    vista = "error";
                }
                break;
            default:
                vista = "error";
        }
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/" + vista + ".jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getPathInfo();
        switch (accion) {
            case "/crearasignatura":
                String codigo = request.getParameter("codigo");
                String nombre = request.getParameter("nombre");

                try {
                    if (codigo.isEmpty() || nombre.isEmpty()) {
                        throw new NullPointerException();
                    }
                    Asignatura asignatura = new Asignatura(codigo, nombre);
                    guardarAsignatura(asignatura);
                    request.getSession().setAttribute("msg", "Asignatura creada con exito");
                    response.sendRedirect("http://localhost:8080/universidad/asignatura/listarasignaturas");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "/eliminar":

                break;
            case "/matricular":
                try {
                    long asignaturaId = Long.parseLong(request.getParameter("id"));
                    //Recupero usuario
                    Usuario user;
                    TypedQuery<Usuario> qUser = em.createNamedQuery("Usuario.findByEmail", Usuario.class);
                    qUser.setParameter("email", session.getAttribute("email"));
                    user = qUser.getSingleResult();
                    
                    

                    //Recupero asignatura
                    Asignatura asign;
                    TypedQuery<Asignatura> qAsign = em.createNamedQuery("Asignatura.findById", Asignatura.class);
                    qAsign.setParameter("id", asignaturaId);
                    asign = qAsign.getSingleResult();

                    UsuarioAsignatura userAsignatura = new UsuarioAsignatura(user, asign);
                    
                    user.getUsuarioAsignaturas().add(userAsignatura);
                    
                    utx.begin();
                    em.persist(userAsignatura);
                    em.merge(user);
                    utx.commit();
                    
                    response.setStatus(HttpServletResponse.SC_OK);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            default:
                throw new AssertionError();
        }
    }

    private void guardarAsignatura(Asignatura asignatura) {
        try {
            utx.begin();
            em.persist(asignatura);
            utx.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<Asignatura> misAsignaturas(HttpServletRequest request) {
        //Primera consulta para buscar al alumno
        Usuario user;
        List<Asignatura> misAsignaturas = new ArrayList<>();
        TypedQuery<Usuario> qUsuario = em.createNamedQuery("Usuario.findByEmail", Usuario.class);
        qUsuario.setParameter("email", session.getAttribute("email"));
        user = qUsuario.getSingleResult();

        //Guardo IDS de asignatura en una list, compruebo si el usuario tiene asignaturas previamente
        if (user.getUsuarioAsignaturas().size() != 0) {
            List<Long> asignaturasId = new ArrayList<>();
            for (UsuarioAsignatura usuarioAsignatura : user.getUsuarioAsignaturas()) {
                asignaturasId.add(usuarioAsignatura.getAsignatura().getId());
            }
            //Segunda consulta para buscar las asignaturad completas
            TypedQuery<Asignatura> qAsignaturas = em.createQuery("SELECT a FROM Asignatura a WHERE a.id IN :ids", Asignatura.class);
            qAsignaturas.setParameter("ids", asignaturasId);
            misAsignaturas = qAsignaturas.getResultList();
        }
        return misAsignaturas;
    }

    private List<Asignatura> misAsignaturasNoMatriculadas(HttpServletRequest request) {
        //Primera consulta para buscar al alumno
        Usuario user;
        TypedQuery<Usuario> qUsuario = em.createNamedQuery("Usuario.findByEmail", Usuario.class);
        qUsuario.setParameter("email", session.getAttribute("email"));
        user = qUsuario.getSingleResult();

        //Guardo IDS de asignatura en una list, compruebo si el usuario tiene asignaturas previamente
        List<Long> asignaturasId = new ArrayList<>();
        if (!user.getUsuarioAsignaturas().isEmpty() || user.getUsuarioAsignaturas() != null) {
            for (UsuarioAsignatura usuarioAsignatura : user.getUsuarioAsignaturas()) {
                asignaturasId.add(usuarioAsignatura.getAsignatura().getId());
            }
        }
        //Segunda consulta para buscar las asignaturad completas
        TypedQuery<Asignatura> qAsignaturas;
        if (asignaturasId.isEmpty()) {
            qAsignaturas = em.createQuery("SELECT a FROM Asignatura a", Asignatura.class);
        } else {
            qAsignaturas = em.createQuery("SELECT a FROM Asignatura a WHERE a.id NOT IN :ids", Asignatura.class);
            qAsignaturas.setParameter("ids", asignaturasId);
        }
        return qAsignaturas.getResultList();
    }

}
