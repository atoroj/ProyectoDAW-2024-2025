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
        <title>Lista de Asignaturas</title>
    </head>
    <body>
        <h1>Asignatura</h1>
        <c:if test="${!empty requestScope.asignaturas}">
            <table>
                <tr>
                    <th>ID</th>
                    <th>Codigo</th>
                    <th>Nombre</th>
                </tr>
                <c:forEach var="asignaturas" items="${requestScope.asignaturas}">
                    <tr>
                        <td>${asignaturas.id}</td>
                        <td>${asignaturas.codigo}</td>
                        <td>${asignaturas.nombre}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </body>
</html>
