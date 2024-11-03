<%-- 
    Document   : main
    Created on : 10 oct 2024, 11:03:45
    Author     : Antonio
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Main page</title>
    </head>
    <body>
        <header>
            <h1>Universidad de Tavizna</h1>
            <c:if test="${sessionScope.email == null}">
                <h2><a href="/universidad/login">Iniciar sesión</a></h2>
            </c:if>
            <c:if test="${sessionScope.email != null}">
                <h2><a href="/universidad/logout">Cerrar sesión</a></h2>
            </c:if>        
        
        </header>

        <nav>
            <ul>
                <li><a href="/universidad/sobre-nosotros">Sobre Nosotros</a></li>
                <li><a href="/universidad/carreras">Carreras</a></li>
                <li><a href="/universidad/admisiones">Admisiones</a></li>
                <li><a href="/universidad/contacto">Contacto</a></li>
                <c:if test="${sessionScope.email != null}">
                    <li><a href="/universidad/user/listaralumnos">Listar alumnos</a></li>
                </c:if>
                <c:if test="${sessionScope.email != null && sessionScope.rol == 'ADM'}">
                    <li><a href="/universidad/user/listarusuarios">Listar usuarios</a></li>
                </c:if>
                <c:if test="${sessionScope.email != null && sessionScope.rol == 'ALU'}">
                    <li><a href="/universidad/asignatura/misasignaturas">Mis asignaturas</a></li>
                </c:if>
                <c:if test="${sessionScope.email != null && sessionScope.rol == 'ADM'}">
                    <li><a href="/universidad/asignatura/listarasignaturas">Listar asignaturas</a></li>
                </c:if>
                
                
            </ul>
        </nav>

        <section id="bienvenida">
            <h2>Bienvenido a la Universidad de Tavizna</h2>
            <p>En la Universidad de Tavizna, nos dedicamos a brindar una educación de calidad para formar a los líderes del futuro. Explora nuestras carreras, conoce nuestras instalaciones y forma parte de nuestra comunidad académica.</p>
        </section>

        <section id="noticias">
            <h2>Últimas Noticias y Eventos</h2>
            <article>
                <h3>Convocatoria para Nuevos Ingresos</h3>
                <p>¡Abiertas las inscripciones para el próximo semestre! Visita nuestra sección de admisiones para más información.</p>
            </article>
            <article>
                <h3>Semana de Ciencia y Tecnología</h3>
                <p>Del 12 al 16 de noviembre, únete a conferencias y talleres con expertos del sector tecnológico. ¡No te lo pierdas!</p>
            </article>
        </section>
        <footer>
            <p>&copy; 2025 Universidad de Tavizna. Todos los derechos reservados.</p>
        </footer>
    </body>
</html>

