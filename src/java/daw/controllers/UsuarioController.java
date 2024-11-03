/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package daw.controllers;

import daw.modal.Usuario;
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
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 * @author Antonio
 */
@WebServlet(name = "UsuarioController", urlPatterns = {"/main", "/user/*", "/adm/*", "/prof/*"})
public class UsuarioController extends HttpServlet {

    @PersistenceContext(unitName = "UniversidadPU")
    private EntityManager em;
    @Resource
    private UserTransaction utx;
    private static final Logger Log = Logger.getLogger(UsuarioController.class.getName());
    HttpSession session;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String vista;
        String accion = "/main";
        String tipo = request.getServletPath();
        session = request.getSession();
        if (tipo.equals("/user")) {
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
            case "/listaralumnos":
                List<Usuario> alumList;
                TypedQuery<Usuario> alums = em.createQuery("SELECT u FROM Usuario u WHERE u.rol = :rol", Usuario.class);
                alums.setParameter("rol", "ALU");
                alumList = alums.getResultList();
                request.setAttribute("alumnos", alumList);
                vista = "userlist";
                break;
            case "/listarusuarios":
                if (!session.getAttribute("rol").equals("ADM")) {
                    vista = "error";
                } else {
                    List<Usuario> userList;
                    TypedQuery<Usuario> users = em.createNamedQuery("Usuario.findAll", Usuario.class);
                    userList = users.getResultList();
                    request.setAttribute("alumnos", userList);
                    vista = "userlist";
                }
                break;
            case "/nuevousuario":
                if (!session.getAttribute("rol").equals("ADM")) {
                    vista = "error";
                } else {
                    vista = "usercreate";
                }
                break;
            default:
                vista = "error";
        }
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/" + vista + ".jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getPathInfo();
        if (accion.equals("/crearusuario")) {
            String name = request.getParameter("name");
            String surname = request.getParameter("surname");
            String nif = request.getParameter("nif");
            String email = request.getParameter("email");
            int phone = Integer.valueOf(request.getParameter("phone"));

            String pwd = request.getParameter("pwd");
            String rol = request.getParameter("rol");
            try {
                if (name.isEmpty() || email.isEmpty() || nif.isEmpty() || pwd.isEmpty() || rol.isEmpty()) {
                    throw new NullPointerException();
                }
                Usuario user = new Usuario(email, name, surname, pwd, nif, rol, phone);
                guardarUsuario(user);
                response.sendRedirect("http://localhost:8080/universidad/user/listaralumnos");
            } catch (Exception e) {

            }
        }
    }

    public void guardarUsuario(Usuario user) {
        try {
            utx.begin();
            //TODO MODIFICAR ALUMNO
            em.persist(user);
            utx.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
