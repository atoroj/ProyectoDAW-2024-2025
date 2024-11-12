<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Página principal</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="/universidad/css/styles.css" rel="stylesheet" type="text/css">
    </head>
    <body class="body">
        
        <%@include file="shared/header.jsp" %>

        <main class="container my-5">
            <section id="bienvenida" class="mb-4">
                <h2>Bienvenido a la Universidad de Tavizna</h2>
                <p>En la Universidad de Tavizna, nos dedicamos a brindar una educación de calidad para formar a los líderes del futuro. Explora nuestras carreras, conoce nuestras instalaciones y forma parte de nuestra comunidad académica.</p>
            </section>

            <section id="noticias">
                <h2>Últimas Noticias y Eventos</h2>
                <div class="card mb-3">
                    <div class="card-body">
                        <h3 class="card-title">Convocatoria para Nuevos Ingresos</h3>
                        <p class="card-text">¡Abiertas las inscripciones para el próximo semestre! Visita nuestra sección de admisiones para más información.</p>
                    </div>
                </div>
                <div class="card mb-3">
                    <div class="card-body">
                        <h3 class="card-title">Semana de Ciencia y Tecnología</h3>
                        <p class="card-text">Del 12 al 16 de noviembre, únete a conferencias y talleres con expertos del sector tecnológico. ¡No te lo pierdas!</p>
                    </div>
                </div>
            </section>
        </main>

        <%@include file="shared/footer.jsp" %>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
