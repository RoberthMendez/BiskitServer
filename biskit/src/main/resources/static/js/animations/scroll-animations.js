(() => {
  const prefersReducedMotion = window.matchMedia("(prefers-reduced-motion: reduce)").matches;

  if (prefersReducedMotion) {
    document.body.classList.add("hero-ready");
  } else {
    requestAnimationFrame(() => {
      requestAnimationFrame(() => {
        document.body.classList.add("hero-ready");
      });
    });
  }

  const selectors = [
    "#sobre-biskit > article",
    "#sobre-biskit + section > article",
    "#servicios > p",
    "#servicios > h2",
    "#servicios > article > div",
    "#proceso > p",
    "#proceso > h2",
    "#proceso article .paso-numero",
    "#por-que > div",
    "#razones > div",
    "#cta > *",
    "#contacto > *",
  ];

  const animationTargets = selectors.flatMap((sel) => [...document.querySelectorAll(sel)]);

  if (!animationTargets.length) {
    return;
  }

  if (prefersReducedMotion) {
    animationTargets.forEach((el) => el.classList.add("is-visible"));
    return;
  }

  animationTargets.forEach((el) => el.classList.add("reveal-up"));

  const STAGGER_MS = 55;
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

  animationTargets.forEach((el) => observer.observe(el));
})();
