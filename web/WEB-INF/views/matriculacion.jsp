<%-- 
    Document   : matriculacion
    Created on : 14 nov 2024, 11:10:48
    Author     : Antonio
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Crea o actualiza tu matricula</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="/universidad/css/styles.css" rel="stylesheet" type="text/css">
        <script src="/universidad/js/main.js"></script>
    </head>
    <%@include file="shared/header.jsp" %>
    <body>
        <h1>Crea o actualiza tu matricula</h1>
        <c:if test="${!empty sessionScope.msg}">
            <div class="alert alert-success" role="alert">
                ${sessionScope.msg}
            </div>
            <c:remove var="msg" scope="session" />
        </c:if>
        <div>
            <h2>Mis asignaturas</h2>
            <c:if test="${!empty requestScope.misasignaturas}">
                <table>
                    <tr>
                        <th>ID</th>
                        <th>Codigo</th>
                        <th>Nombre</th>
                    </tr>
                    <c:forEach var="misasignaturas" items="${requestScope.misasignaturas}">
                        <tr>
                            <td>${misasignaturas.id}</td>
                            <td>${misasignaturas.codigo}</td>
                            <td>${misasignaturas.nombre}</td>
                            <td><button onclick="eliminarAsignaturaMatricula(${misasignaturas.id})">Eliminar</button></td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
        </div>
        <div>
            <h2>Asignaturas no matriculadas</h2>
            <c:if test="${!empty requestScope.misasignaturasnomatriculadas}">
                <table>
                    <tr>
                        <th>ID</th>
                        <th>Codigo</th>
                        <th>Nombre</th>
                    </tr>
                    <c:forEach var="misasignaturasnomatriculadas" items="${requestScope.misasignaturasnomatriculadas}">
                        <tr>
                            <td>${misasignaturasnomatriculadas.id}</td>
                            <td>${misasignaturasnomatriculadas.codigo}</td>
                            <td>${misasignaturasnomatriculadas.nombre}</td>
                            <td><button onclick="anadirAsignaturaMatricula(${misasignaturasnomatriculadas.id})">Añadir</button></td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
        </div>
    </body>
    <%@include file="shared/footer.jsp" %>
</html>
