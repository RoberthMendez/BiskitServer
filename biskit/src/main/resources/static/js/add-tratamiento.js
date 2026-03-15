const container = document.getElementById("drogas-container");

function agregarDroga() {

    const primerCampo = container.children[0];
    const nuevoCampo = primerCampo.cloneNode(true);

    const select = nuevoCampo.querySelector("select");
    select.selectedIndex = 0;

    // Agregar botón eliminar si no tiene
    if (!nuevoCampo.querySelector("button")) {
        const boton = document.createElement("button");
        boton.type = "button";
        boton.onclick = function () { eliminarDroga(this); };
        boton.className = "text-red-500 font-bold px-2";
        boton.textContent = "✕";
        nuevoCampo.appendChild(boton);
    }

    container.appendChild(nuevoCampo);
    actualizarOpciones();
}

function eliminarDroga(boton) {
    const campo = boton.parentElement;
    campo.remove();
    actualizarOpciones();
}

function actualizarOpciones() {
    const selects = container.querySelectorAll("select");

    // Obtener valores seleccionados
    const valoresSeleccionados = [];
    selects.forEach(select => {
        if (select.value !== "") {
            valoresSeleccionados.push(select.value);
        }
    });

    // Primero, mostrar todas las opciones (para que reaparezcan al cambiar)
    selects.forEach(select => {
        select.querySelectorAll("option").forEach(option => {
            if (option.value !== "") {
                option.style.display = 'block';
            }
        });
    });

    // Luego, ocultar las opciones seleccionadas en otros selects
    selects.forEach(select => {
        const valorActual = select.value;
        select.querySelectorAll("option").forEach(option => {
            if (option.value === "") return;  // No ocultar la opción vacía
            if (valoresSeleccionados.includes(option.value) && option.value !== valorActual) {
                option.style.display = 'none';
            }
        });
    });
}

// Deshabilitar opciones de drogas al editar un tratamiento existente
document.addEventListener("DOMContentLoaded", actualizarOpciones);