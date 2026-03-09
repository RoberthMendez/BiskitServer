window.addEventListener("load", () => {
  document.querySelectorAll(".toggle-switch").forEach((toggle) => {
    const input = toggle.querySelector("input");
    const pill = toggle.querySelector(".toggle-pill");
    const spanInactive = toggle.querySelector(".toggle-inactive");
    const spanActive = toggle.querySelector(".toggle-active");

    const updatePill = () => {
      const target = input.checked ? spanActive : spanInactive;

      pill.style.width = target.offsetWidth + "px";
      pill.style.left = target.offsetLeft + "px";
      pill.style.backgroundColor = input.checked ? "#E1F8EA" : "#EDEDED";
    };

    updatePill(); // inicializa correctamente
    input.addEventListener("change", updatePill);
  });
});
