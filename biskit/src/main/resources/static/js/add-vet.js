const DROPDOWN_ANIMATION_MS = 200;

function showDropdown(dropdown) {
  if (!dropdown.classList.contains("hidden")) {
    return;
  }

  dropdown.classList.remove("hidden");

  requestAnimationFrame(() => {
    dropdown.classList.remove(
      "opacity-0",
      "-translate-y-1",
      "scale-95",
      "pointer-events-none",
    );
    dropdown.classList.add("opacity-100", "translate-y-0", "scale-100");
  });
}

function hideDropdown(dropdown) {
  dropdown.classList.remove("opacity-100", "translate-y-0", "scale-100");
  dropdown.classList.add(
    "opacity-0",
    "-translate-y-1",
    "scale-95",
    "pointer-events-none",
  );

  window.setTimeout(() => {
    if (dropdown.classList.contains("opacity-0")) {
      dropdown.classList.add("hidden");
    }
  }, DROPDOWN_ANIMATION_MS);
}

function initSearchDropdown({
  inputId,
  dropdownId,
  hiddenInputId,
  optionSelector,
  searchableFields,
  onSelect,
}) {
  const input = document.getElementById(inputId);
  const dropdown = document.getElementById(dropdownId);
  const hiddenInput = document.getElementById(hiddenInputId);

  if (!input || !dropdown || !hiddenInput) {
    return;
  }

  const container = dropdown.parentElement;
  const getOptions = () => dropdown.querySelectorAll(optionSelector);

  input.addEventListener("focus", () => {
    showDropdown(dropdown);
  });

  input.addEventListener("input", () => {
    const value = input.value.toLowerCase();
    const options = getOptions();

    options.forEach((option) => {
      const textMatches = searchableFields.some((field) => {
        const rawValue = option.dataset[field] || "";
        return rawValue.toLowerCase().includes(value);
      });

      option.style.display = textMatches ? "block" : "none";
    });

    // Si el usuario borra el campo, limpiar el hidden input
    if (!input.value) {
      hiddenInput.value = "";
    }
  });

  dropdown.addEventListener("click", (event) => {
    const option = event.target.closest(optionSelector);
    if (!option || !dropdown.contains(option)) {
      return;
    }

    input.value = option.dataset.nombre || "";
    hiddenInput.value = option.dataset.id || "";
    hideDropdown(dropdown);

    if (onSelect) {
      onSelect({ id: option.dataset.id, nombre: option.dataset.nombre });
    }
  });

  document.addEventListener("click", (event) => {
    if (!container.contains(event.target)) {
      hideDropdown(dropdown);
    }
  });
}

initSearchDropdown({
  inputId: "especialidadSearch",
  dropdownId: "especialidadDropdown",
  hiddenInputId: "especialidadId",
  optionSelector: ".especialidad-option",
  searchableFields: ["nombre"],
});

// ----- Modal y funcionalidad para agregar especialidad -----
const openAddEspecialidadModalButton = document.getElementById(
  "openAddEspecialidadModal",
);
const addEspecialidadModal = document.getElementById("addEspecialidadModal");
const closeAddEspecialidadModalButton = document.getElementById(
  "closeAddEspecialidadModal",
);
const cancelAddEspecialidadButton = document.getElementById(
  "cancelAddEspecialidad",
);
const saveNewEspecialidadButton = document.getElementById(
  "saveNewEspecialidad",
);
const newEspecialidadNombreInput = document.getElementById(
  "newEspecialidadNombre",
);
const addEspecialidadError = document.getElementById("addEspecialidadError");
const especialidadDropdown = document.getElementById("especialidadDropdown");
const especialidadSearchInput = document.getElementById("especialidadSearch");
const especialidadIdInput = document.getElementById("especialidadId");

function clearEspecialidadError() {
  if (!addEspecialidadError) {
    return;
  }
  addEspecialidadError.textContent = "";
  addEspecialidadError.classList.add("hidden");
}

function showEspecialidadError(message) {
  if (!addEspecialidadError) {
    return;
  }
  addEspecialidadError.textContent = message;
  addEspecialidadError.classList.remove("hidden");
}

function openAddEspecialidadModal() {
  if (!addEspecialidadModal) {
    return;
  }
  clearEspecialidadError();
  addEspecialidadModal.classList.remove("hidden");
  addEspecialidadModal.classList.add("flex");
  if (newEspecialidadNombreInput) {
    newEspecialidadNombreInput.value = "";
    newEspecialidadNombreInput.focus();
  }
}

function closeAddEspecialidadModal() {
  if (!addEspecialidadModal) {
    return;
  }
  addEspecialidadModal.classList.remove("flex");
  addEspecialidadModal.classList.add("hidden");
}

async function saveNewEspecialidad() {
  if (
    !newEspecialidadNombreInput ||
    !saveNewEspecialidadButton ||
    !especialidadDropdown ||
    !especialidadSearchInput ||
    !especialidadIdInput
  ) {
    return;
  }

  const nombre = newEspecialidadNombreInput.value.trim();

  if (!nombre) {
    showEspecialidadError("Ingresa el nombre de la especialidad");
    return;
  }

  clearEspecialidadError();
  saveNewEspecialidadButton.disabled = true;

  try {
    const body = new URLSearchParams({ nombre });
    const response = await fetch("/admin/vets/especialidades/add", {
      method: "POST",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8",
      },
      body: body.toString(),
    });

    if (!response.ok) {
      showEspecialidadError("Error al agregar la especialidad");
      return;
    }

    const result = await response.json();
    if (!result.ok) {
      showEspecialidadError(result.message || "Error al agregar la especialidad");
      return;
    }

    const selector = `.especialidad-option[data-id="${result.id}"]`;
    let option = especialidadDropdown.querySelector(selector);

    if (!option) {
      option = document.createElement("div");
      option.className =
        "especialidad-option px-4 py-3 hover:bg-blue-50 cursor-pointer border-b border-gray-100";
      option.dataset.id = result.id;
      option.dataset.nombre = result.nombre;
      const p = document.createElement("p");
      p.className = "font-semibold text-sm text-gray-800";
      p.textContent = result.nombre;
      option.appendChild(p);
      especialidadDropdown.appendChild(option);
    }

    especialidadSearchInput.value = result.nombre;
    especialidadIdInput.value = result.id;
    closeAddEspecialidadModal();
  } catch (_error) {
    showEspecialidadError("Error al agregar la especialidad");
  } finally {
    saveNewEspecialidadButton.disabled = false;
  }
}

if (openAddEspecialidadModalButton) {
  openAddEspecialidadModalButton.addEventListener(
    "click",
    openAddEspecialidadModal,
  );
}

if (closeAddEspecialidadModalButton) {
  closeAddEspecialidadModalButton.addEventListener(
    "click",
    closeAddEspecialidadModal,
  );
}

if (cancelAddEspecialidadButton) {
  cancelAddEspecialidadButton.addEventListener(
    "click",
    closeAddEspecialidadModal,
  );
}

if (saveNewEspecialidadButton) {
  saveNewEspecialidadButton.addEventListener("click", saveNewEspecialidad);
}

if (newEspecialidadNombreInput) {
  newEspecialidadNombreInput.addEventListener("keydown", (event) => {
    if (event.key === "Enter") {
      saveNewEspecialidad();
    }
  });
}

if (addEspecialidadModal) {
  addEspecialidadModal.addEventListener("click", (event) => {
    if (event.target === addEspecialidadModal) {
      closeAddEspecialidadModal();
    }
  });
}