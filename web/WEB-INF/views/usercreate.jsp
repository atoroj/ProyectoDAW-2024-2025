<%-- 
    Document   : usercreate
    Created on : 30 oct 2024, 11:09:05
    Author     : Antonio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Crear Usuario</title>
    </head>
    <body>

        <h1>Formulario de Creación de Usuario</h1>

        <form action="/universidad/user/crearusuario" method="post">
            <label for="name">Nombre:</label>
            <input type="text" id="name" name="name" required>
            <br><br>
            
            <label for="surname">Apellido:</label>
            <input type="text" id="surname" name="surname" required>
            <br><br>
            
            <label for="nif">NIF:</label>
            <input type="text" id="nif" name="nif" required>
            <br><br>
            
            <label for="email">Correo Electrónico:</label>
            <input type="email" id="email" name="email" required>
            <br><br>

            <label for="phone">Teléfono:</label>
            <input type="tel" id="phone" name="phone" required>
            <br><br>

            <label for="pwd">Contraseña:</label>
            <input type="password" id="pwd" name="pwd" required>
            <br><br>

            <label for="rol">Rol:</label>
            <select id="rol" name="rol" required>
                <option value="ALU">Alumno</option>
                <option value="PROF">Profesor</option>
                <option value="ADM">Administrador</option>
            </select>
            <br><br>

            <button type="submit">Crear Usuario</button>
        </form>

    </body>
</html>

