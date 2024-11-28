package daw.controllers;

import daw.modal.Usuario;
import daw.utilidad.Util;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.UserTransaction;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.logging.Logger;

/**
 *
 * @author Antonio
 */
@WebServlet(name = "LoginController", urlPatterns = {"/login", "/login/*", "/logout"})
public class LoginController extends HttpServlet {

    @PersistenceContext(unitName = "UniversidadPU")
    private EntityManager em;
    @Resource
    private UserTransaction utx;
    HttpSession session;
    private static final Logger Log = Logger.getLogger(LoginController.class.getName());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String vista = "";
        String accion = request.getServletPath();
        if (request.getServletPath().equals("/login")) {
            if (request.getPathInfo() != null) {
                accion = request.getPathInfo();
            }
        }
        switch (accion) {
            case "/login":
                vista = "login";
                break;
            case "/logout":
                if (session != null) {
                    session.invalidate();
                }
                response.sendRedirect("http://localhost:8080/universidad/main");
                break;
            default:
                vista = "error";
        }
        if (!vista.equals("")) {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/" + vista + ".jsp");
            rd.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = "";
        String vista = "";
        if (request.getServletPath().equals("/login")) {
            if (request.getPathInfo() != null) {
                accion = request.getPathInfo();
            } else {
                accion = "error";
            }
        }
        switch (accion) {
            case "/check":
                RequestDispatcher rd;
                try {
                    String email = request.getParameter("email");
                    String pwd = Util.pwdMD5(request.getParameter("password"));
                    boolean usuarioValidado = validarUsuario(request, email, pwd);
                    if (usuarioValidado) {
                        response.sendRedirect("http://localhost:8080/universidad/main");
                    } else {
                        vista = "login";
                        request.setAttribute("msg", "Email o contraseña incorrectos");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            default:
                vista = "error";
        }
        if (!vista.equals("")) {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/" + vista + ".jsp");
            rd.forward(request, response);
        }
    }

    private boolean validarUsuario(HttpServletRequest request, String email, String pwd) {
        boolean userValid = false;
        Usuario user;
        try {
            TypedQuery<Usuario> q = em.createNamedQuery("Usuario.findByEmailAndPass", Usuario.class);
            q.setParameter("email", email);
            q.setParameter("pwd", pwd);
            user = q.getSingleResult();
            if (user != null) {
                session = request.getSession();
                session.setAttribute("email", user.getEmail());
                session.setAttribute("rol", user.getRol());
                userValid = true;
            }
        } catch (NoResultException e) {

        }

        return userValid;
    }
}
