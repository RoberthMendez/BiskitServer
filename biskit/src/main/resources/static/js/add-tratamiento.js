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

const container = document.getElementById("drogas-container");

function agregarDroga() {
    const primerCampo = container.children[0];
    const nuevoCampo = primerCampo.cloneNode(true);
    const index = container.children.length;

    // Update IDs
    nuevoCampo.querySelector('input[type="text"]').id = 'drogaSearch' + index;
    nuevoCampo.querySelector('input[type="hidden"]').id = 'drogaId' + index;
    nuevoCampo.querySelector('div[id^="drogaDropdown"]').id = 'drogaDropdown' + index;

    // Clear values
    nuevoCampo.querySelector('input[type="text"]').value = '';
    nuevoCampo.querySelector('input[type="hidden"]').value = '';

    // Add button if not present
    if (!nuevoCampo.querySelector("button")) {
        const boton = document.createElement("button");
        boton.type = "button";
        boton.onclick = function () { eliminarDroga(this); };
        boton.className = "text-red-500 font-bold px-2";
        boton.textContent = "✕";
        nuevoCampo.appendChild(boton);
    }

    container.appendChild(nuevoCampo);

    // Initialize dropdown for new field
    const hidden = nuevoCampo.querySelector('input[type="hidden"]');
    const input = hidden.previousElementSibling.querySelector('input[type="text"]');
    const fieldContainer = hidden.parentElement;
    const dropdown = fieldContainer.querySelector('div[id^="drogaDropdown"]');
    initSearchDropdown({
        inputId: input.id,
        dropdownId: dropdown.id,
        hiddenInputId: hidden.id,
        optionSelector: ".droga-option",
        searchableFields: ["nombre"],
        onSelect: () => actualizarOpciones()
    });

    actualizarOpciones();
}

function eliminarDroga(boton) {
    const campo = boton.parentElement;
    campo.remove();
    actualizarOpciones();
}

function actualizarOpciones() {
    const hiddenInputs = container.querySelectorAll('input[type="hidden"][name="drogasIds"]');
    const valoresSeleccionados = Array.from(hiddenInputs).map(input => input.value).filter(v => v !== '');

    const dropdowns = container.querySelectorAll('div[id^="drogaDropdown"]');
    dropdowns.forEach(dropdown => {
        const options = dropdown.querySelectorAll('.droga-option');
        const currentHidden = dropdown.parentElement.querySelector('input[type="hidden"]');
        const currentValue = currentHidden ? currentHidden.value : '';

        options.forEach(option => {
            const id = option.dataset.id;
            if (valoresSeleccionados.includes(id) && id !== currentValue) {
                option.style.display = 'none';
            } else {
                option.style.display = 'block';
            }
        });
    });
}

// Deshabilitar opciones de drogas al editar un tratamiento existente
document.addEventListener("DOMContentLoaded", () => {
    // Initialize dropdowns for all drug fields
    const hiddenInputs = container.querySelectorAll('input[type="hidden"][name="drogasIds"]');
    hiddenInputs.forEach((hidden) => {
        const input = hidden.previousElementSibling.querySelector('input[type="text"]');
        const fieldContainer = hidden.parentElement;
        const dropdown = fieldContainer.querySelector('div[id^="drogaDropdown"]');
        if (input && dropdown) {
            initSearchDropdown({
                inputId: input.id,
                dropdownId: dropdown.id,
                hiddenInputId: hidden.id,
                optionSelector: ".droga-option",
                searchableFields: ["nombre"],
                onSelect: () => actualizarOpciones()
            });
        }
    });
    actualizarOpciones();
});

initSearchDropdown({
  inputId: "mascotaSearch",
  dropdownId: "mascotaDropdown",
  hiddenInputId: "mascotaId",
  optionSelector: ".mascota-option",
  searchableFields: ["nombre", "especie", "raza"],
});

initSearchDropdown({
  inputId: "vetSearch",
  dropdownId: "vetDropdown",
  hiddenInputId: "vetId",
  optionSelector: ".vet-option",
  searchableFields: ["nombre"],
});