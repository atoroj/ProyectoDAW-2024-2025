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
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

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
                List<Usuario> alumList;
                TypedQuery<Usuario> alums = em.createQuery("SELECT u FROM Usuario u WHERE u.rol = :rol", Usuario.class);
                alums.setParameter("rol", "ALU");
                alumList = alums.getResultList();
                request.setAttribute("alumnos", alumList);
                vista = "userlist";
                break;
            case "/listarusuarios":
                if (session.getAttribute("rol").equals("ADM")) {
                    List<Usuario> userList;
                    TypedQuery<Usuario> users = em.createNamedQuery("Usuario.findAll", Usuario.class);
                    userList = users.getResultList();

                    request.setAttribute("alumnos", userList);
                    vista = "userlist";
                } else {
                    vista = "error";
                }
                break;
            case "/nuevousuario":
                if (session.getAttribute("rol").equals("ADM")) {
                    vista = "usercreate";
                } else {
                    vista = "error";
                }
                break;
            case "/profile":
                TypedQuery<Usuario> qUsuarioProfile = em.createNamedQuery("Usuario.findByEmail", Usuario.class);
                qUsuarioProfile.setParameter("email", session.getAttribute("email"));
                Usuario userProfile = qUsuarioProfile.getSingleResult();
                request.setAttribute("usuario", userProfile);
                vista = "profile";
                break;
            case "/asignatura":
                long usuarioId = Long.parseLong(request.getParameter("id"));
                TypedQuery<Usuario> qUsuario = em.createNamedQuery("Usuario.findById", Usuario.class);
                qUsuario.setParameter("id", usuarioId);
                Usuario user = qUsuario.getSingleResult();

                request.setAttribute("usuario", user);
                //Para poder utilizar el index en JSTL con foreach tengo que pasarlo a List ya que Set no tiene indices
                Set<UsuarioAsignatura> asignaturasSet = user.getUsuarioAsignaturas();
                List<UsuarioAsignatura> usuarioAsignaturaList = new ArrayList<>(asignaturasSet);
                request.setAttribute("usuarioasignatura", usuarioAsignaturaList);
                vista = "asignarnotas";
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
                if (session.getAttribute("rol").equals("ADM")) {
                    try {
                        String name = request.getParameter("name");
                        String surname = request.getParameter("surname");
                        String nif = request.getParameter("nif");
                        String email = request.getParameter("email");
                        String phoneString = request.getParameter("phone");

                        String pwd = Util.pwdMD5(request.getParameter("pwd"));
                        String rol = request.getParameter("rol");
                        if (Util.controlUsuario(session, email, name, surname, pwd, nif, rol, phoneString)) {
                            response.sendRedirect("/universidad/user/nuevousuario");
                            throw new Exception("Validación de usuario fallida");
                        }
                        int phone = Integer.valueOf(phoneString);
                        Usuario user = new Usuario(email, name, surname, pwd, nif, rol, phone);
                        guardarUsuario(user);
                        request.getSession().setAttribute("msg", "Usuario creado con exito");
                        response.sendRedirect("/universidad/user/listaralumnos");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    response.sendRedirect("/universidad/user/error");
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
                        user = em.find(Usuario.class, user.getId());
                        em.remove(user);
                        utx.commit();

                        session.setAttribute("msg", "Usuario eliminado con exito");
                        response.setStatus(HttpServletResponse.SC_OK);
                    } catch (Exception e) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        e.printStackTrace();
                    }
                } else {
                    response.sendRedirect("/universidad/user/error");
                }

                break;
            case "/editprofile":
                try {
                    long usuarioId = Long.parseLong(request.getParameter("id"));
                    Usuario user;
                    TypedQuery<Usuario> qUser = em.createNamedQuery("Usuario.findById", Usuario.class);
                    qUser.setParameter("id", usuarioId);
                    user = qUser.getSingleResult();

                    String email = request.getParameter("email");
                    int tlf = Integer.parseInt(request.getParameter("phone"));
                    String nif = request.getParameter("nif");

                    user.setEmail(email);
                    user.setPhone(tlf);
                    user.setNif(nif);

                    final Part imgPart = request.getPart("profileimg");
                    if (imgPart != null || !imgPart.getSubmittedFileName().equals("")) {
                        String relativePath = "" + File.separator + "img";
                        String absolutePath = getServletContext().getRealPath(relativePath);
                        String fileName = user.getId().toString();
                        System.out.println("COSA " + imgPart.getSubmittedFileName());
                        user.setRutaimg("/universidad" + File.separator + "img" + File.separator + fileName + ".jpg");
                        File f = new File(absolutePath + File.separator + fileName + ".jpg");
                        OutputStream fos = new FileOutputStream(f);
                        InputStream filecontent = imgPart.getInputStream();
                        int read = 0;
                        final byte[] bytes = new byte[1024];
                        while ((read = filecontent.read(bytes)) != -1) {
                            fos.write(bytes, 0, read);
                        }

                        fos.close();
                        filecontent.close();
                    }
                    guardarUsuario(user);
                    session.setAttribute("msg", "Perfil actualizado con exito");
                    response.sendRedirect("/universidad/user/profile");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                break;
            case "/nota":
                if (session.getAttribute("rol").equals("PROF")) {
                    try {
                        //Busco el usuario
                        long usuarioId = Long.parseLong(request.getParameter("userId"));
                        Usuario user;
                        TypedQuery<Usuario> qUser = em.createNamedQuery("Usuario.findById", Usuario.class);
                        qUser.setParameter("id", usuarioId);
                        user = qUser.getSingleResult();

                        Set<UsuarioAsignatura> userAsignaturas = new HashSet<>();
                        int index = 0;

                        while (request.getParameter("notas[" + index + "].codigo") != null) {
                            String codigo = request.getParameter("notas[" + index + "].codigo");
                            int nota = Integer.parseInt(request.getParameter("notas[" + index + "].nota"));
                            String curso = "2024-2025";

                            //Busco la asignatura
                            Asignatura asign;
                            TypedQuery<Asignatura> qAsign = em.createNamedQuery("Asignatura.findByCodigo", Asignatura.class);
                            qAsign.setParameter("codigo", codigo);
                            asign = qAsign.getSingleResult();

                            //Busco la relacion usuario-asignatura
                            UsuarioAsignatura uAsign;
                            TypedQuery<UsuarioAsignatura> qUserAsign = em.createQuery("SELECT ua FROM UsuarioAsignatura ua WHERE ua.usuario = :user AND ua.asignatura =:asign", UsuarioAsignatura.class);
                            qUserAsign.setParameter("user", user);
                            qUserAsign.setParameter("asign", asign);
                            uAsign = qUserAsign.getSingleResult();
                            uAsign.setCurso(curso);
                            uAsign.setNota(nota);

                            utx.begin();
                            em.merge(uAsign);
                            utx.commit();
                            index++;
                        }
                        response.setStatus(HttpServletResponse.SC_OK);
                        session.setAttribute("msg", "Notas actualizadas con exito");
                        response.sendRedirect("/universidad/user/listaralumnos");
                    } catch (Exception e) {
                        e.printStackTrace();
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    }
                } else {
                    response.sendRedirect("/universidad/user/error");
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

    private void guardarUsuario(Usuario user) {
        try {
            utx.begin();
            em.merge(user);
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
