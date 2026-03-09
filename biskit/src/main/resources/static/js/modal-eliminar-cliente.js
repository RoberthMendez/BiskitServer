let urlEliminar = null;

function abrirModalEliminar(url) {
  urlEliminar = url;
  const modal = document.getElementById("modalEliminar");
  modal.classList.remove("hidden");
  modal.classList.add("flex");
}

function cerrarModalEliminar() {
  const modal = document.getElementById("modalEliminar");
  modal.classList.add("hidden");
  modal.classList.remove("flex");
  urlEliminar = null;
}

function confirmarEliminar() {
  if (urlEliminar){
    window.location.href = urlEliminar;
  }
}

// Cerrar al hacer click fuera del modal
document
  .getElementById("modalEliminar")
  .addEventListener("click", function (e) {
    if (e.target.id === "modalEliminar") {
      cerrarModalEliminar();
    }
  });