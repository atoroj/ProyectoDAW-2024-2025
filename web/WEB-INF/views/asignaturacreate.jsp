<%-- 
    Document   : asignaturacreate
    Created on : 7 nov 2024, 10:39:03
    Author     : Antonio
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Crear Asignatura</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="/universidad/css/styles.css" rel="stylesheet" type="text/css">
    </head>
    <%@include file="shared/header.jsp" %>
    <body>
        <h1 class="titulostabla">Crear Asignatura</h1>
        <form class="formularios" action="/universidad/asignatura/crearasignatura" method="POST">
            <div>
                <label for="codigo">Código de Asignatura</label>
                <input type="text" id="codigo" name="codigo" required>
            </div>
            <div>
                <label for="nombre">Nombre de Asignatura</label>
                <input type="text" id="nombre" name="nombre" required>
            </div>
            <button class="btn btn-primary" type="submit">Crear asignatura</button>
        </form>
    </body>
    <%@include file="shared/footer.jsp" %>
</html>
