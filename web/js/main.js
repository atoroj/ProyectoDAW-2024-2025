/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function editarPerfil() {
    document.getElementById("email").disabled = false;
    document.getElementById("phone").disabled = false;
    document.getElementById("nif").disabled = false;
    document.getElementById("profileimg").style.display = "inline";
    document.getElementById("editarPerfil").style.display = "none";
    document.getElementById("guardarPerfil").style.display = "inline";
    document.getElementById("cancelarPerfil").style.display = "inline";
}

function anadirAsignaturaMatricula(asignaturaId) {
    fetch(`/universidad/asignatura/matricular?id=${asignaturaId}`, {
        method: 'POST'
    }).then(response => {
        if (response.ok) {
            location.reload();
        } else {
            alert("Error al añadir la asignatura");
        }
    });
}

function eliminarAsignaturaMatricula(asignaturaId) {
    fetch(`/universidad/asignatura/desmatricular?id=${asignaturaId}`, {
        method: 'POST'
    }).then(response => {
        if (response.ok) {
            location.reload();
        } else {
            alert("Error al eliminar la asignatura");
        }
    });
}

function eliminarUsuario(usuarioId) {
    fetch(`/universidad/user/eliminar?id=${usuarioId}`, {
        method: 'POST'
    }).then(response => {
        if (response.ok) {
            location.reload();
        } else {
            alert("Error al eliminar alumno");
        }
    });
}

function eliminarAsignatura(asignaturaId) {
    fetch(`/universidad/asignatura/eliminar?id=${asignaturaId}`, {
        method: 'POST'
    }).then(response => {
        if (response.ok) {
            location.reload();
        } else {
            alert("Error al eliminar asignatura");
        }
    });
}
