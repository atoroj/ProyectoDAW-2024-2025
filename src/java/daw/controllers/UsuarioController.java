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
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import jakarta.transaction.UserTransaction;
import java.io.File;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 * @author Antonio
 */
@MultipartConfig
@WebServlet(name = "UsuarioController", urlPatterns = {"/main", "/user/*"})
public class UsuarioController extends HttpServlet {

    @PersistenceContext(unitName = "UniversidadPU")
    private EntityManager em;
    @Resource
    private UserTransaction utx;
    private static final Logger Log = Logger.getLogger(UsuarioController.class.getName());
    HttpSession session;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String vista = "";
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
                if (session.getAttribute("email") != null) {
                    List<Usuario> alumList;
                    TypedQuery<Usuario> alums = em.createQuery("SELECT u FROM Usuario u WHERE u.rol = :rol", Usuario.class);
                    alums.setParameter("rol", "ALU");
                    alumList = alums.getResultList();
                    request.setAttribute("alumnos", alumList);
                    vista = "userlist";
                } else {
                    vista = "error";
                }
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
            case "/profile":
                if (session.getAttribute("email") != null) {
                    Usuario user;
                    TypedQuery<Usuario> qUsuario = em.createNamedQuery("Usuario.findByEmail", Usuario.class);
                    qUsuario.setParameter("email", session.getAttribute("email"));
                    user = qUsuario.getSingleResult();
                    request.setAttribute("usuario", user);
                    vista = "profile";
                } else {
                    vista = "error";
                }
                break;
            case "/editprofle":
                Part imgPart = request.getPart("profileImg");
                //String rutaImg = "img" + File.separator + "prfileimg" + File.separator + idUsuario + imgPart;
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
        switch (accion) {
            case "/crearusuario":
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
                    request.getSession().setAttribute("msg", "Usuario creado con exito");
                    response.sendRedirect("http://localhost:8080/universidad/user/listaralumnos");
                } catch (Exception e) {

                }
                break;
            case "/eliminar":
                if (session.getAttribute("rol").equals("ADM")) {
                    try {
                        long usuarioId = Long.parseLong(request.getParameter("id"));
                        Usuario user;
                        TypedQuery<Usuario> qUser = em.createNamedQuery("Usuario.findById", Usuario.class);
                        qUser.setParameter("id", usuarioId);
                        user = qUser.getSingleResult();
                        utx.begin();

                        if (!em.contains(user)) {
                            user = em.merge(user);
                        }

                        em.remove(user);
                        utx.commit();
                        response.setStatus(HttpServletResponse.SC_OK);
                    } catch (Exception e) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        e.printStackTrace();
                    }
                }else{
                    response.sendRedirect("http://localhost:8080/universidad/user/error");
                }

                break;
            default:
                throw new AssertionError();
        }
    }

    private String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String fileName = null;

        for (String cd : contentDisposition.split(";")) {
            if (cd.trim().startsWith("filename")) {
                fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("", "");
                break;
            }
        }
        if (fileName != null && fileName.contains(".")) {
            fileName = fileName.substring(0, fileName.lastIndexOf('.'));
        }

        return fileName;
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
