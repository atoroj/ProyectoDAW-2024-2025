<%-- 
    Document   : asignarnotas
    Created on : 29 nov 2024, 11:44:27
    Author     : Antonio
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="/universidad/css/styles.css" rel="stylesheet" type="text/css">
        <script src="/universidad/js/main.js"></script>
    </head>
    <body>
        <%@include file="shared/header.jsp" %>

        <h1>Asignar notas</h1>
        <c:if test="${!empty sessionScope.msg}">
            <div class="alert alert-success" role="alert">
                ${sessionScope.msg}
            </div>
            <c:remove var="msg" scope="session"/>
        </c:if>
        <h2>${requestScope.usuario.name} ${requestScope.usuario.surname}</h2>
        <h3>${requestScope.usuario.nif}</h3>
        <c:if test="${!empty requestScope.misasignaturas}">
            <table>
                <tr>
                    <th>Codigo</th>
                    <th>Asignatura</th>
                    <th>Nota</th>
                </tr>

                <c:forEach var="misasignaturas" items="${requestScope.misasignaturas}" varStatus="status">
                    <c:if test="${requestScope.usuarioasignatura[status.index].nota == '0'}">
                        <tr>
                            <td>${misasignaturas.codigo}</td>
                            <td>${misasignaturas.nombre}</td>
                            <td><input type="text" id="nota" value="${requestScope.usuarioasignatura[status.index].nota}"/></td>
                            <td>${requestScope.usuarioasignatura[status.index].asignatura.codigo}</td>
                        </tr> 
                    </c:if>
                </c:forEach>
            </table>
            <button onclick="anadirNota(${requestScope.usuario.id})">Asignar notas</button>
        </c:if>
        <%@include file="shared/footer.jsp" %>
    </body>
</html>
