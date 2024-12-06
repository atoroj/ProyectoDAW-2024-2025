/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package daw.controllers;

import daw.modal.Asignatura;
import daw.modal.Usuario;
import daw.modal.UsuarioAsignatura;
import daw.utilidad.Util;
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
import java.util.Set;
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
            case "/listarasignaturas":
                if (session.getAttribute("email") != null && (session.getAttribute("rol").equals("PROF") || session.getAttribute("rol").equals("ADM"))) {
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
                if (session.getAttribute("email") != null && session.getAttribute("rol").equals("ALU")) {

                    Usuario user;
                    TypedQuery<Usuario> qUser = em.createNamedQuery("Usuario.findByEmail", Usuario.class);
                    qUser.setParameter("email", session.getAttribute("email"));
                    user = qUser.getSingleResult();

                    Set<UsuarioAsignatura> asignaturasSet = user.getUsuarioAsignaturas();
                    List<UsuarioAsignatura> usuarioAsignaturaList = new ArrayList<>(asignaturasSet);

                    request.setAttribute("asignaturas", usuarioAsignaturaList);
                    vista = "asignaturalist";
                } else {
                    vista = "error";
                }
                break;
            case "/nuevaasignatura":
                if (session.getAttribute("email") != null && (session.getAttribute("rol").equals("ADM") || session.getAttribute("rol").equals("PROF"))) {
                    vista = "asignaturacreate";
                } else {
                    vista = "error";
                }

                break;
            case "/matricula":
                if (session.getAttribute("email") != null && session.getAttribute("rol").equals("ALU")) {
                    request.setAttribute("misasignaturas", Util.misAsignaturas(em, session, (String) session.getAttribute("email")));
                    request.setAttribute("misasignaturasnomatriculadas", Util.misAsignaturasNoMatriculadas(em, session, (String) session.getAttribute("email")));
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
                if (session.getAttribute("email") != null && session.getAttribute("rol").equals("ADM")) {
                    String codigo = request.getParameter("codigo");
                    String nombre = request.getParameter("nombre");

                    try {
                        if (codigo.isEmpty() || nombre.isEmpty()) {
                            throw new NullPointerException();
                        }
                        Asignatura asignatura = new Asignatura(codigo, nombre);
                        guardarAsignatura(asignatura);
                        request.getSession().setAttribute("msg", "Asignatura creada con exito");
                        response.sendRedirect("/asignatura/listarasignaturas");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    response.sendRedirect("/universidad/user/error");
                }

                break;
            case "/eliminar":
                if (session.getAttribute("email") != null && session.getAttribute("rol").equals("ADM")) {
                    try {
                        long asignaturaId = Long.parseLong(request.getParameter("id"));
                        Asignatura asign;
                        TypedQuery<Asignatura> qAsign = em.createNamedQuery("Asignatura.findById", Asignatura.class);
                        qAsign.setParameter("id", asignaturaId);
                        asign = qAsign.getSingleResult();
                        utx.begin();
                        if (!em.contains(asign)) {
                            asign = em.merge(asign);
                        }
                        em.remove(asign);
                        utx.commit();
                        response.setStatus(HttpServletResponse.SC_OK);
                        session.setAttribute("msg", "Asignatura eliminada con éxito");
                    } catch (Exception e) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        e.printStackTrace();
                    }
                } else {
                    response.sendRedirect("/universidad/user/error");
                }
                break;
            case "/matricular":
                if (session.getAttribute("email") != null && session.getAttribute("rol").equals("ALU")) {
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
                        em.merge(user);
                        utx.commit();
                        session.setAttribute("msg", "Asignatura matriculada con éxito");
                        response.setStatus(HttpServletResponse.SC_OK);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    response.sendRedirect("/universidad/user/error");
                }

                break;
            case "/desmatricular":
                if (session.getAttribute("email") != null && session.getAttribute("rol").equals("ALU")) {
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

                        UsuarioAsignatura userAsignatura;
                        TypedQuery<UsuarioAsignatura> qUserAsign = em.createQuery("SELECT u FROM UsuarioAsignatura u WHERE u.usuario = :usuario AND u.asignatura = :asignatura", UsuarioAsignatura.class);
                        qUserAsign.setParameter("usuario", user);
                        qUserAsign.setParameter("asignatura", asign);
                        userAsignatura = qUserAsign.getSingleResult();
                        System.out.println("ASIGNATURA " + userAsignatura);

                        user.getUsuarioAsignaturas().remove(userAsignatura);

                        utx.begin();
                        em.merge(user);

                        //Lo he visto en stackoverflow, hay que recuperar la entidad si no está vinculada, y se recupera asi
                        if (!em.contains(userAsignatura)) {
                            userAsignatura = em.merge(userAsignatura);
                        }
                        em.remove(userAsignatura);
                        utx.commit();
                        session.setAttribute("msg", "Asignatura desmatriculada con éxito");
                        response.setStatus(HttpServletResponse.SC_OK);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    response.sendRedirect("/universidad/user/error");
                }
                break;
            case "/anadirfav":
                long asignaturaId = Long.parseLong(request.getParameter("id"));
                
                Asignatura asign;
                TypedQuery<Asignatura> qAsign = em.createNamedQuery("Asignatura.findById", Asignatura.class);
                qAsign.setParameter("id", asignaturaId);
                asign = qAsign.getSingleResult();
                
                List<Asignatura> favoritos = (List<Asignatura>) session.getAttribute("favoritos");
                if (favoritos == null) {
                    favoritos = new ArrayList<>();
                }
                if(!favoritos.contains(asign)){
                    favoritos.add(asign);
                }else{
                    favoritos.remove(asign);
                }
                
                session.setAttribute("favoritos", favoritos);
                System.out.println("PRUEBA " + session.getAttribute("favoritos"));
                response.setStatus(HttpServletResponse.SC_OK);
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
}
