<header class="header">
    <h1 href="/universidad/main">Universidad de Tavizna</h1>
    <c:if test="${sessionScope.email == null}">
        <a href="/universidad/login" class="btn btn-light">Iniciar sesi�n</a>
    </c:if>
    <c:if test="${sessionScope.email != null}">
        <a href="/universidad/logout" class="btn btn-light">Cerrar sesi�n</a>
    </c:if>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <!-- <li class="nav-item"><a class="nav-link" href="/universidad/sobre-nosotros">Sobre Nosotros</a></li>
                    <li class="nav-item"><a class="nav-link" href="/universidad/carreras">Carreras</a></li>
                    <li class="nav-item"><a class="nav-link" href="/universidad/admisiones">Admisiones</a></li>
                    <li class="nav-item"><a class="nav-link" href="/universidad/contacto">Contacto</a></li> -->
                    <c:if test="${sessionScope.email != null}">
                        <li class="nav-item"><a class="nav-link" href="/universidad/user/listaralumnos">Alumnos</a></li>
                    </c:if>
                    <c:if test="${sessionScope.email != null && sessionScope.rol == 'ADM'}">
                        <li class="nav-item"><a class="nav-link" href="/universidad/user/listarusuarios">Usuarios</a></li>
                    </c:if>
                    <c:if test="${sessionScope.email != null && sessionScope.rol == 'ALU'}">
                        <li class="nav-item"><a class="nav-link" href="/universidad/asignatura/misasignaturas">Mis asignaturas</a></li>
                    </c:if>
                    <c:if test="${sessionScope.email != null && sessionScope.rol == 'ADM'}">
                        <li class="nav-item"><a class="nav-link" href="/universidad/asignatura/listarasignaturas">Asignaturas</a></li>
                    </c:if>
                    <c:if test="${sessionScope.email != null}">
                        <li class="nav-item"><a class="nav-link" href="/universidad/user/profile">Perfil</a></li>
                    </c:if>
                    <c:if test="${sessionScope.email != null && sessionScope.rol == 'ADM'}">
                        <li class="nav-item"><a class="nav-link" href="/universidad/user/nuevousuario">Crear usuario</a></li>
                    </c:if>
                    <c:if test="${sessionScope.email != null && (sessionScope.rol == 'ADM' || sessionScope.rol == 'PROF')}">
                        <li class="nav-item"><a class="nav-link" href="/universidad/asignatura/nuevaasignatura">Crear asignatura</a></li>
                    </c:if>
                </ul>
            </div>
        </div>
    </nav>
</header>
