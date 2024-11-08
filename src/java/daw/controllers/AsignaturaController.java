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
                List<Asignatura> asignaturaList;
                TypedQuery<Asignatura> asignaturas = em.createNamedQuery("Asignatura.findAll", Asignatura.class);
                asignaturaList = asignaturas.getResultList();
                request.setAttribute("asignaturas", asignaturaList);
                vista = "asignaturalist";
                break;
            case "/misasignaturas":
                //Primera consulta para buscar al alumno
                Usuario user;
                TypedQuery<Usuario> qUsuario = em.createNamedQuery("Usuario.findByEmail", Usuario.class);
                qUsuario.setParameter("email", session.getAttribute("email"));
                user = qUsuario.getSingleResult();

                //Guardo IDS de asignatura en una list, compruebo si el usuario tiene asignaturas previamente
                //PREGUNTAR SI ESTA BIEN
                if (user.getUsuarioAsignaturas().size() == 0) {
                    vista = "asignaturalist";
                } else {
                    List<Long> asignaturasId = new ArrayList<>();
                    for (UsuarioAsignatura usuarioAsignatura : user.getUsuarioAsignaturas()) {
                        asignaturasId.add(usuarioAsignatura.getId());
                    }

                    //Segunda consulta para buscar las asignaturad completas
                    List<Asignatura> misAsignaturas;
                    TypedQuery<Asignatura> qAsignaturas = em.createQuery("SELECT a FROM Asignatura a WHERE a.id IN :ids", Asignatura.class);
                    qAsignaturas.setParameter("ids", asignaturasId);
                    misAsignaturas = qAsignaturas.getResultList();
                    request.setAttribute("asignaturas", misAsignaturas);
                }
                vista = "asignaturalist";
                break;
            case "/nuevaasignatura":
                
                vista = "asignaturacreate";
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
    }

}
