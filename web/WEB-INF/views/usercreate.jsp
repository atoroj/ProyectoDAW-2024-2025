<%-- 
    Document   : usercreate
    Created on : 30 oct 2024, 11:09:05
    Author     : Antonio
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Crear Usuario</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="/universidad/css/styles.css" rel="stylesheet" type="text/css">
    </head>
    <%@include file="shared/header.jsp" %>
    <body>
        <h1 class="titulostabla">Crear Usuario</h1>
        <c:if test="${!empty sessionScope.msg}">
            <div class="alert alert-success" role="alert">
                ${sessionScope.msg}
            </div>
            <c:remove var="msg" scope="session"/>
        </c:if>
        <c:if test="${!empty sessionScope.msgerror}">
            <div class="alert alert-danger" role="alert">
                ${sessionScope.msgerror}
            </div>
            <c:remove var="msgerror" scope="session"/>
        </c:if>
        <form action="/universidad/user/crearusuario" method="post" class="formularios">
            <div>
                <label for="name">Nombre:</label>
                <input class="form-control" type="text" id="name" name="name"  maxlength="20" required>
                <br><br>

                <label for="surname">Apellido:</label>
                <input class="form-control" type="text" id="surname" name="surname" maxlength="50" required>
                <br><br>

                <label for="nif">NIF:</label>
                <input class="form-control" type="text" id="nif" name="nif"  maxlength="9" required>
                <br><br>
            </div>
            <div>
                <label for="email">Correo Electrónico:</label>
                <input class="form-control" type="email" id="email" name="email" required>
                <br><br>

                <label for="phone">Teléfono:</label>
                <input class="form-control" type="tel" id="phone" name="phone"  maxlength="9" required>
                <br><br>

                <label for="pwd">Contraseña:</label>
                <input class="form-control" type="password" id="pwd" name="pwd"  maxlength="15" required>
                <br><br>
            </div>
            <label style="width: 100%"for="rol">Rol:</label>
            <select class="form-select" id="rol" name="rol" required>
                <option selected value="ALU">Alumno</option>
                <option value="PROF">Profesor</option>
                <option value="ADM">Administrador</option>
            </select>

            <button class="btn btn-primary" type="submit">Crear Usuario</button>
        </form>

    </body>
    <%@include file="shared/footer.jsp" %>
</html>

