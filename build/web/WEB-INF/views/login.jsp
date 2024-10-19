<%-- 
    Document   : login
    Created on : 10 oct 2024, 10:43:03
    Author     : Antonio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <h1>Introduce tus datos</h1>
        <form action="/universidad/alumno" method="POST">
            <label>Nombre</label>
            <input id="name" type="text" name="name"/>
            <label>Contraseña</label>
            <input id="password" type="password" name="password"/>
            </br>
            <input type="submit" value="Enviar"/>
        </form>
    </body>
</html>
