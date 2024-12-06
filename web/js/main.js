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

function anadirNota(userId) {
    const cuerpo = new URLSearchParams();
    const filas = document.querySelectorAll('table tr');

    filas.forEach((fila, index) => {
        if (index === 0)
            return; // Saltar el encabezado de la tabla
        const codigo = fila.cells[0].innerText; // Código de la asignatura
        const nota = fila.querySelector('input#nota').value; // Nota por cada fila
        cuerpo.append(`notas[${index - 1}].codigo`, codigo);
        cuerpo.append(`notas[${index - 1}].nota`, nota);
    });
        cuerpo.append("userId", userId);

    fetch(`/universidad/user/nota`, {
        method: 'POST',
        body: cuerpo
    }).then(response => {
        if (response.ok) {
            console.log("Exitoso");
        }
    });
}

function anadirFav(asignaturaId){
    const checkbox = document.querySelector(`#fav[value="${asignaturaId}"]`);
    const isChecked = checkbox.checked;
    fetch(`/universidad/asignatura/anadirfav?id=${asignaturaId}`, {
        method: 'POST'
    }).then(response => {
        if (response.ok) {
            console.log("Perfecto");
        } else {
            alert("Error al eliminar asignatura");
        }
    });
}