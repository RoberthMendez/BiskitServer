function updateTogglePill(toggle) {
  const input = toggle.querySelector("input");
  const pill = toggle.querySelector(".toggle-pill");
  const spanInactive = toggle.querySelector(".toggle-inactive");
  const spanActive = toggle.querySelector(".toggle-active");
  const target = input.checked ? spanActive : spanInactive;

  pill.style.width = `${target.offsetWidth}px`;
  pill.style.left = `${target.offsetLeft}px`;
  pill.style.backgroundColor = input.checked ? "#E1F8EA" : "#EDEDED";
}

function revertirCheckbox(id, nuevoEstado) {
  const checkbox = document.getElementById(`interruptor-${id}`);
  if (!checkbox) {
    return;
  }

  checkbox.checked = !nuevoEstado;
  const toggle = checkbox.closest(".toggle-switch");
  if (toggle) {
    updateTogglePill(toggle);
  }
}

function cambiarEstadoMascota(id, nuevoEstado) {
  fetch(`/vet/pets/update-estado/${id}`, {
    method: "PATCH",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ estado: nuevoEstado }),
  })
    .then((res) => {
      if (!res.ok) {
        throw new Error(`HTTP ${res.status}`);
      }
      return res.json();
    })
    .then((data) => {
      if (!data.ok) {
        console.error("Error al cambiar estado:", data.message);
        revertirCheckbox(id, nuevoEstado);
      }
    })
    .catch((error) => {
      console.error("Error al cambiar estado:", error);
      revertirCheckbox(id, nuevoEstado);
    });
}

window.addEventListener("load", () => {
  document.querySelectorAll(".toggle-switch").forEach((toggle) => {
    const input = toggle.querySelector("input");
    updateTogglePill(toggle);

    input.addEventListener("change", () => {
      updateTogglePill(toggle);
      cambiarEstadoMascota(input.dataset.petId, input.checked);
    });
  });
});
