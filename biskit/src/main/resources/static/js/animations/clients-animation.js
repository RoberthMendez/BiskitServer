(() => {
  const prefersReducedMotion = window.matchMedia("(prefers-reduced-motion: reduce)").matches;

  const backLink = document.querySelector(".back-link");
  const titulo = document.querySelector("#clients-titulo > div:first-child");
  const btnAgregar = document.querySelector("#clients-titulo > div:last-child");
  const buscador = document.querySelector("#clients-buscar");
  const headerEls = [backLink, titulo, btnAgregar, buscador].filter(Boolean);

  if (!prefersReducedMotion) {
    headerEls.forEach((el) => el.classList.add("reveal-up"));

    const HEADER_TRANSITION =
      "opacity 0.65s cubic-bezier(0.22,1,0.36,1), transform 0.65s cubic-bezier(0.22,1,0.36,1)";

    requestAnimationFrame(() => {
      requestAnimationFrame(() => {
        headerEls.forEach((el, i) => {
          setTimeout(() => {
            el.style.transition = HEADER_TRANSITION;
            el.classList.add("is-visible");
            setTimeout(() => {
              el.style.transition = "";
              el.classList.remove("reveal-up");
            }, 700);
          }, i * 80);
        });
      });
    });
  }

  const rows = [...document.querySelectorAll(".clients-row")];
  const cards = [...document.querySelectorAll(".clients-card")];
  const scrollTargets = [...rows, ...cards];

  if (!scrollTargets.length || prefersReducedMotion) return;

  scrollTargets.forEach((el) => el.classList.add("reveal-up"));

  const STAGGER_MS = 45;
  const REVEAL_TRANSITION =
    "opacity 0.65s cubic-bezier(0.22,1,0.36,1), transform 0.65s cubic-bezier(0.22,1,0.36,1)";

  const observer = new IntersectionObserver(
    (entries, currentObserver) => {
      const intersecting = entries.filter((e) => e.isIntersecting);
      if (!intersecting.length) return;

      intersecting
        .sort((a, b) =>
          a.target.compareDocumentPosition(b.target) & Node.DOCUMENT_POSITION_FOLLOWING ? -1 : 1,
        )
        .forEach((entry, i) => {
          const el = entry.target;
          currentObserver.unobserve(el);
          setTimeout(() => {
            el.style.transition = REVEAL_TRANSITION;
            el.classList.add("is-visible");
            setTimeout(() => {
              el.style.transition = "";
              el.classList.remove("reveal-up");
            }, 700);
          }, i * STAGGER_MS);
        });
    },
    { threshold: 0, rootMargin: "0px 0px -30px 0px" },
  );

  scrollTargets.forEach((el) => observer.observe(el));
})();
