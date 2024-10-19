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
    </head>
    <body>
        <h1>Alumnos</h1>
        <c:if test="${!empty requestScope.alumnos}">
            <table>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Apellidos</th>
                    <th>DNI</th>
                    <th>Email</th>
                    <th>Telefono</th>
                </tr>
                <c:forEach var="alumnos" items="${requestScope.alumnos}">
                    <tr>
                        <td>${alumnos.id}</td>
                        <td>${alumnos.name}</td>
                        <td>${alumnos.surname}</td>
                        <td>${alumnos.nif}</td>
                        <td>${alumnos.email}</td>
                        <td>${alumnos.phone}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </body>
</html>
