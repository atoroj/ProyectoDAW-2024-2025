<%-- 
    Document   : profile
    Created on : 7 nov 2024, 10:38:56
    Author     : Antonio
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Perfil usuario</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="/universidad/css/styles.css" rel="stylesheet" type="text/css">
        <script src="/universidad/js/main.js"></script>
    </head>
    <%@include file="shared/header.jsp" %>
    <body>
        <div class="contenedor-perfil">


            <div class="perfil-detalles">

                <form action="/universidad/user/editprofile" enctype="multipart/form-data">
                    <div class="profile-picture">
                        <p>IMAGEN</p>
                    </div>
                    <input type="file" id="profileImg">
                    <h2>${requestScope.usuario.name} ${requestScope.usuario.surname}</h2>
                    <label>
                        <strong>Correo electrónico:</strong> <input type="text" id="email" name="email" value="${requestScope.usuario.email}" disabled="true"/>
                    </label>
                    <label>
                        <strong>Teléfono:</strong> <input type="text" id="phone" name="phone" value="${requestScope.usuario.phone}" disabled="true"/>
                    </label>
                    <label>
                        <strong>NIF:</strong> <input type="text" id="nif" name="nif" value="${requestScope.usuario.nif}" disabled="true"/>
                    </label>

                    <button class="btn btn-danger editar-perfil" style="display: none" id="cancelarPerfil">
                        Cancelar
                    </button>
                    <button type="submit" class="btn btn-success editar-perfil" style="display: none" id="guardarPerfil">
                        Guardar
                    </button>
                </form>
                <button class="btn btn-primary editar-perfil" id="editarPerfil" onclick="editarPerfil()" >
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil" viewBox="0 0 16 16">
                    <path d="M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168zM11.207 2.5 13.5 4.793 14.793 3.5 12.5 1.207zm1.586 3L10.5 3.207 4 9.707V10h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.293zm-9.761 5.175-.106.106-1.528 3.821 3.821-1.528.106-.106A.5.5 0 0 1 5 12.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.468-.325"/>
                    </svg>
                    Editar perfil
                </button>
            </div>
        </div>
    </body>
    <%@include file="shared/footer.jsp" %>
</html>
