/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daw.utilidad;

import daw.modal.Asignatura;
import daw.modal.Usuario;
import daw.modal.UsuarioAsignatura;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

/**
 *
 * @author Antonio
 */
public class Util {

    public static String pwdMD5(String pwd) throws NoSuchAlgorithmException {
        String prefijo, sujifo;
        prefijo = "%$·87heq";
        sujifo = "/(!pasqw563)";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(pwd.getBytes());

            Formatter formatter = new Formatter();
            for (byte b : hashBytes) {
                formatter.format("%02x", b);
            }
            return prefijo+formatter.toString()+sujifo;
        }catch(NoSuchAlgorithmException e){
            throw new RuntimeException("Error al encriptar: "+e);
        }

    }
    
    public static List<Asignatura> misAsignaturas(EntityManager em, HttpSession session, String email) {
        //Primera consulta para buscar al alumno
        Usuario user;
        List<Asignatura> misAsignaturas = new ArrayList<>();
        TypedQuery<Usuario> qUsuario = em.createNamedQuery("Usuario.findByEmail", Usuario.class);
        qUsuario.setParameter("email", email);
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
    
    public static List<Asignatura> misAsignaturasNoMatriculadas(EntityManager em, HttpSession session, String email) {
        //Primera consulta para buscar al alumno
        Usuario user;
        TypedQuery<Usuario> qUsuario = em.createNamedQuery("Usuario.findByEmail", Usuario.class);
        qUsuario.setParameter("email", email);
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
