/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function editarPerfil(){
    document.getElementById("email").disabled = false;
    document.getElementById("phone").disabled = false;
    document.getElementById("nif").disabled = false;
    document.getElementById("editarPerfil").style.display = "none";
    document.getElementById("guardarPerfil").style.display = "inline";
    document.getElementById("cancelarPerfil").style.display = "inline";
}
