
function carga() {
    var div = document.getElementById("clientes");
    div.addEventListener('click', mostrarProfesor, false);
    document.getElementById("wp").style.display = 'none';
    var div2 = document.getElementById("productos");
	div2.addEventListener('click', mostrarAlumno, false);

}

function mostrarProfesor() {
    var div = document.getElementById("clientes");
    if (div.click) {
        document.getElementById("formularioIsmaelFinal").style.display = 'none';
        document.getElementById("wp").style.display = 'block';
    }
}

function mostrarAlumno() {
    var div2 = document.getElementById("productos");
    if (div2.click) {
        document.getElementById("wp").style.display = 'none';
        document.getElementById("formularioIsmaelFinal").style.display = 'block';

    }
}

window.addEventListener('load', carga, false);