<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista de Asignaturas</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="/universidad/css/styles.css" rel="stylesheet" type="text/css">
        <script src="/universidad/js/main.js"></script>
    </head>
    <%@ include file="shared/header.jsp" %>
    <body>
        <h1>Asignatura</h1>

        <c:if test="${!empty sessionScope.msg}">
            <div class="alert alert-success" role="alert">
                ${sessionScope.msg}
            </div>
            <c:remove var="msg" scope="session"/>
        </c:if>

        <c:if test="${!empty requestScope.asignaturas}">
            <table class="table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Codigo</th>
                        <th>Nombre</th>
                        <c:if test="${sessionScope.rol == 'ALU'}">
                            <th>Nota</th>
                        </c:if>
                        
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="asignaturas" items="${requestScope.asignaturas}">
                        <tr>
                            <c:choose>
                                <c:when test="${sessionScope.rol == 'ALU'}">
                                    <td>${asignaturas.asignatura.id}</td>
                                    <td>${asignaturas.asignatura.codigo}</td>
                                    <td>${asignaturas.asignatura.nombre}</td>
                                    <td>
                                        <c:if test="${!empty asignaturas.nota}">
                                            ${asignaturas.nota}
                                        </c:if>
                                        <c:if test="${empty asignaturas.nota}">
                                            <span>Nota no asignada</span>
                                        </c:if>
                                    </td>
                                </c:when>
                                <c:otherwise>
                                    <td>${asignaturas.id}</td>
                                    <td>${asignaturas.codigo}</td>
                                    <td>${asignaturas.nombre}</td>
                                    <td>
                                        <c:if test="${sessionScope.rol == 'ADM'}">
                                            <button type="button" class="btn btn-danger" onclick="eliminarAsignatura(${asignaturas.id})">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-x-circle" viewBox="0 0 16 16">
                                                <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14m0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16"/>
                                                <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708"/>
                                                </svg>
                                            </button>
                                        </c:if>
                                    </td>
                                </c:otherwise>
                            </c:choose>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </body>
    <%@ include file="shared/footer.jsp" %>
</html>
