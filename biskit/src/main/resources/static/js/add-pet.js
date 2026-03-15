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
  additionalFilter,
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
      const extraMatches = additionalFilter ? additionalFilter(option) : true;

      option.style.display = textMatches && extraMatches ? "block" : "none";
    });
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
  inputId: "clienteSearch",
  dropdownId: "clienteDropdown",
  hiddenInputId: "clienteId",
  optionSelector: ".cliente-option",
  searchableFields: ["nombre", "cedula"],
});

initSearchDropdown({
  inputId: "especieSearch",
  dropdownId: "especieDropdown",
  hiddenInputId: "especieId",
  optionSelector: ".especie-option",
  searchableFields: ["nombre"],
  onSelect: ({ id }) => filterRazasByEspecie(id),
});

const especieSearchInputGlobal = document.getElementById("especieSearch");
if (especieSearchInputGlobal) {
  especieSearchInputGlobal.addEventListener("input", () => {
    if (!especieSearchInputGlobal.value) {
      const especieIdHidden = document.getElementById("especieId");
      if (especieIdHidden) especieIdHidden.value = "";
      filterRazasByEspecie(null);
    }
  });
}

initSearchDropdown({
  inputId: "razaSearch",
  dropdownId: "razaDropdown",
  hiddenInputId: "razaId",
  optionSelector: ".raza-option",
  searchableFields: ["nombre"],
  additionalFilter: (option) => {
    const currentEspecieId = especieIdInput ? especieIdInput.value : "";
    return !currentEspecieId || option.dataset.especieId === currentEspecieId;
  },
});

function filterRazasByEspecie(especieId) {
  const razaSearchEl = document.getElementById("razaSearch");
  const razaIdEl = document.getElementById("razaId");
  if (razaSearchEl) razaSearchEl.value = "";
  if (razaIdEl) razaIdEl.value = "";

  const razaDropdownEl = document.getElementById("razaDropdown");
  if (!razaDropdownEl) return;

  razaDropdownEl.querySelectorAll(".raza-option").forEach((opt) => {
    if (!especieId || opt.dataset.especieId === String(especieId)) {
      opt.style.display = "block";
    } else {
      opt.style.display = "none";
    }
  });
}

initSearchDropdown({
  inputId: "enfermedadSearch",
  dropdownId: "enfermedadDropdown",
  hiddenInputId: "enfermedadId",
  optionSelector: ".enfermedad-option",
  searchableFields: ["nombre"],
});

const openAddRazaModalButton = document.getElementById("openAddRazaModal");
const addRazaModal = document.getElementById("addRazaModal");
const closeAddRazaModalButton = document.getElementById("closeAddRazaModal");
const cancelAddRazaButton = document.getElementById("cancelAddRaza");
const saveNewRazaButton = document.getElementById("saveNewRaza");
const newRazaNombreInput = document.getElementById("newRazaNombre");
const addRazaError = document.getElementById("addRazaError");
const razaDropdown = document.getElementById("razaDropdown");
const razaSearchInput = document.getElementById("razaSearch");
const razaIdInput = document.getElementById("razaId");
const especieIdInput = document.getElementById("especieId");

function clearRazaError() {
  if (!addRazaError) {
    return;
  }
  addRazaError.textContent = "";
  addRazaError.classList.add("hidden");
}

function showRazaError(message) {
  if (!addRazaError) {
    return;
  }
  addRazaError.textContent = message;
  addRazaError.classList.remove("hidden");
}

function openAddRazaModal() {
  if (!addRazaModal) {
    return;
  }

  addRazaModal.classList.remove("hidden");
  addRazaModal.classList.add("flex");

  if (!especieIdInput || !especieIdInput.value) {
    showRazaError("Selecciona una especie antes de agregar la raza");
    return;
  }

  clearRazaError();
  if (newRazaNombreInput) {
    newRazaNombreInput.value = "";
    newRazaNombreInput.focus();
  }
}

function closeAddRazaModal() {
  if (!addRazaModal) {
    return;
  }
  addRazaModal.classList.remove("flex");
  addRazaModal.classList.add("hidden");
}

async function saveNewRaza() {
  if (
    !newRazaNombreInput ||
    !saveNewRazaButton ||
    !razaDropdown ||
    !razaSearchInput ||
    !razaIdInput ||
    !especieIdInput
  ) {
    return;
  }

  const nombre = newRazaNombreInput.value.trim();
  const idEspecie = especieIdInput.value;

  if (!idEspecie) {
    showRazaError("Selecciona una especie antes de agregar la raza");
    return;
  }

  if (!nombre) {
    showRazaError("Ingresa el nombre de la raza");
    return;
  }

  clearRazaError();
  saveNewRazaButton.disabled = true;

  try {
    const body = new URLSearchParams({ nombre, idEspecie });
    const response = await fetch("/vet/pets/razas/add", {
      method: "POST",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8",
      },
      body: body.toString(),
    });

    if (!response.ok) {
      throw new Error("No se pudo guardar la raza");
    }

    const result = await response.json();
    if (!result.ok) {
      showRazaError(result.message || "No se pudo guardar la raza");
      return;
    }

    const selector = `.raza-option[data-id="${result.id}"]`;
    let option = razaDropdown.querySelector(selector);

    if (!option) {
      option = document.createElement("div");
      option.className =
        "raza-option px-4 py-3 hover:bg-blue-50 cursor-pointer border-b border-gray-100";
      option.dataset.id = String(result.id);
      option.dataset.nombre = result.nombre;
      option.dataset.especieId = String(result.idEspecie || "");

      const text = document.createElement("p");
      text.className = "font-semibold text-sm text-gray-800";
      text.textContent = result.nombre;
      option.appendChild(text);

      const addButtonRow = document.getElementById("openAddRazaModal");
      if (addButtonRow && addButtonRow.parentNode === razaDropdown) {
        razaDropdown.insertBefore(option, addButtonRow);
      } else {
        razaDropdown.appendChild(option);
      }
    }

    razaSearchInput.value = result.nombre;
    razaIdInput.value = String(result.id);

    closeAddRazaModal();
    showDropdown(razaDropdown);
  } catch (_error) {
    showRazaError("Ocurrió un error al guardar la raza");
  } finally {
    saveNewRazaButton.disabled = false;
  }
}

if (openAddRazaModalButton) {
  openAddRazaModalButton.addEventListener("click", openAddRazaModal);
}

if (closeAddRazaModalButton) {
  closeAddRazaModalButton.addEventListener("click", closeAddRazaModal);
}

if (cancelAddRazaButton) {
  cancelAddRazaButton.addEventListener("click", closeAddRazaModal);
}

if (saveNewRazaButton) {
  saveNewRazaButton.addEventListener("click", saveNewRaza);
}

if (newRazaNombreInput) {
  newRazaNombreInput.addEventListener("keydown", (event) => {
    if (event.key === "Enter") {
      event.preventDefault();
      saveNewRaza();
    }
  });
}

if (addRazaModal) {
  addRazaModal.addEventListener("click", (event) => {
    if (event.target === addRazaModal) {
      closeAddRazaModal();
    }
  });
}

const openAddEnfermedadModalButton = document.getElementById(
  "openAddEnfermedadModal",
);
const addEnfermedadModal = document.getElementById("addEnfermedadModal");
const closeAddEnfermedadModalButton = document.getElementById(
  "closeAddEnfermedadModal",
);
const cancelAddEnfermedadButton = document.getElementById(
  "cancelAddEnfermedad",
);
const saveNewEnfermedadButton = document.getElementById("saveNewEnfermedad");
const newEnfermedadNombreInput = document.getElementById("newEnfermedadNombre");
const addEnfermedadError = document.getElementById("addEnfermedadError");
const enfermedadDropdown = document.getElementById("enfermedadDropdown");
const enfermedadSearchInput = document.getElementById("enfermedadSearch");
const enfermedadIdInput = document.getElementById("enfermedadId");

function clearEnfermedadError() {
  if (!addEnfermedadError) {
    return;
  }
  addEnfermedadError.textContent = "";
  addEnfermedadError.classList.add("hidden");
}

function showEnfermedadError(message) {
  if (!addEnfermedadError) {
    return;
  }
  addEnfermedadError.textContent = message;
  addEnfermedadError.classList.remove("hidden");
}

function openAddEnfermedadModal() {
  if (!addEnfermedadModal) {
    return;
  }
  clearEnfermedadError();
  addEnfermedadModal.classList.remove("hidden");
  addEnfermedadModal.classList.add("flex");
  if (newEnfermedadNombreInput) {
    newEnfermedadNombreInput.value = "";
    newEnfermedadNombreInput.focus();
  }
}

function closeAddEnfermedadModal() {
  if (!addEnfermedadModal) {
    return;
  }
  addEnfermedadModal.classList.remove("flex");
  addEnfermedadModal.classList.add("hidden");
}

async function saveNewEnfermedad() {
  if (
    !newEnfermedadNombreInput ||
    !saveNewEnfermedadButton ||
    !enfermedadDropdown ||
    !enfermedadSearchInput ||
    !enfermedadIdInput
  ) {
    return;
  }

  const nombre = newEnfermedadNombreInput.value.trim();
  if (!nombre) {
    showEnfermedadError("Ingresa el nombre de la enfermedad");
    return;
  }

  clearEnfermedadError();
  saveNewEnfermedadButton.disabled = true;

  try {
    const body = new URLSearchParams({ nombre });
    const response = await fetch("/vet/pets/enfermedades/add", {
      method: "POST",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8",
      },
      body: body.toString(),
    });

    if (!response.ok) {
      throw new Error("No se pudo guardar la enfermedad");
    }

    const result = await response.json();
    if (!result.ok) {
      showEnfermedadError(result.message || "No se pudo guardar la enfermedad");
      return;
    }

    const selector = `.enfermedad-option[data-id="${result.id}"]`;
    let option = enfermedadDropdown.querySelector(selector);

    if (!option) {
      option = document.createElement("div");
      option.className =
        "enfermedad-option px-4 py-3 hover:bg-blue-50 cursor-pointer border-b border-gray-100";
      option.dataset.id = String(result.id);
      option.dataset.nombre = result.nombre;

      const text = document.createElement("p");
      text.className = "font-semibold text-sm text-gray-800";
      text.textContent = result.nombre;
      option.appendChild(text);

      const addButtonRow = document.getElementById("openAddEnfermedadModal");
      if (addButtonRow && addButtonRow.parentNode === enfermedadDropdown) {
        enfermedadDropdown.insertBefore(option, addButtonRow);
      } else {
        enfermedadDropdown.appendChild(option);
      }
    }

    enfermedadSearchInput.value = result.nombre;
    enfermedadIdInput.value = String(result.id);

    closeAddEnfermedadModal();
    showDropdown(enfermedadDropdown);
  } catch (_error) {
    showEnfermedadError("Ocurrió un error al guardar la enfermedad");
  } finally {
    saveNewEnfermedadButton.disabled = false;
  }
}

if (openAddEnfermedadModalButton) {
  openAddEnfermedadModalButton.addEventListener(
    "click",
    openAddEnfermedadModal,
  );
}

if (closeAddEnfermedadModalButton) {
  closeAddEnfermedadModalButton.addEventListener(
    "click",
    closeAddEnfermedadModal,
  );
}

if (cancelAddEnfermedadButton) {
  cancelAddEnfermedadButton.addEventListener("click", closeAddEnfermedadModal);
}

if (saveNewEnfermedadButton) {
  saveNewEnfermedadButton.addEventListener("click", saveNewEnfermedad);
}

if (newEnfermedadNombreInput) {
  newEnfermedadNombreInput.addEventListener("keydown", (event) => {
    if (event.key === "Enter") {
      event.preventDefault();
      saveNewEnfermedad();
    }
  });
}

if (addEnfermedadModal) {
  addEnfermedadModal.addEventListener("click", (event) => {
    if (event.target === addEnfermedadModal) {
      closeAddEnfermedadModal();
    }
  });
}
