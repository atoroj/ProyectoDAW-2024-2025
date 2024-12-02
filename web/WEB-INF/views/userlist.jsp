<%-- 
    Document   : userlist
    Created on : 19 oct 2024, 11:37:00
    Author     : Antonio
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista de usuarios</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="/universidad/css/styles.css" rel="stylesheet" type="text/css">
        <script src="/universidad/js/main.js"></script>
    </head>
    <%@include file="shared/header.jsp" %>
    <body>
        <h1>Alumnos</h1>
        <c:if test="${!empty sessionScope.msg}">
            <div class="alert alert-success" role="alert">
                ${sessionScope.msg}
            </div>
            <c:remove var="msg" scope="session"/>
        </c:if>
        <c:if test="${!empty requestScope.alumnos}">
            <table class="table">
                <tr>
                    <th>ID</th>
                    <th>Imagen</th>
                    <th>Nombre</th>
                    <th>Apellidos</th>
                    <th>DNI</th>
                    <th>Email</th>
                    <th>Telefono</th>
                    <th></th>
                </tr>
                <c:forEach var="alumnos" items="${requestScope.alumnos}">
                    <tr>
                        <td>${alumnos.id}</td>
                        <c:if test="${!empty alumnos.rutaimg}">
                            <td><img class="img-tabla" src="${alumnos.rutaimg}" alt="${alumnos.name}"></td>
                            </c:if>
                            <c:if test="${empty alumnos.rutaimg}">
                            <td><img class="img-tabla" src="/universidad/img/default.jpg" alt="imgdefault"/></td>
                            </c:if>
                        <td>${alumnos.name}</td>
                        <td>${alumnos.surname}</td>
                        <td>${alumnos.nif}</td>
                        <td>${alumnos.email}</td>
                        <td>${alumnos.phone}</td>
                        <c:if test="${sessionScope.rol == 'ADM'}">
                            <td >
                                <button class="btn btn-danger" onclick="eliminarUsuario(${alumnos.id})">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-x-circle" viewBox="0 0 16 16">
                                    <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14m0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16"/>
                                    <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708"/>
                                    </svg>
                                </button>
                            </td>
                        </c:if>
                        <c:if test="${sessionScope.rol == 'PROF'}">
                            <td >
                                <a href="/universidad/user/asignatura?id=${alumnos.id}">
                                    <button class="btn btn-success" >
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-plus-square" viewBox="0 0 16 16">
                                        <path d="M14 1a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1zM2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2z"/>
                                        <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4"/>
                                        </svg> 
                                    </button>
                                </a>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </body>
    <%@include file="shared/footer.jsp" %>
</html>
