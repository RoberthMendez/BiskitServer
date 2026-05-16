package com.example.biskit.errors;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class VeterinarioNoDisponibleException extends RuntimeException {

  public VeterinarioNoDisponibleException(String message) {
    super(message);
  }

  public VeterinarioNoDisponibleException() {
    super("El veterinario no está disponible");
  }

  public VeterinarioNoDisponibleException(String diaSemana, boolean porDia) {
    super("El veterinario no trabaja el dia " + diaSemana);
  }

  public VeterinarioNoDisponibleException(LocalTime inicio, LocalTime fin) {
    super(
      "El veterinario trabaja de " +
        inicio.format(DateTimeFormatter.ofPattern("hh:mm a")) +
        " a " +
        fin.format(DateTimeFormatter.ofPattern("hh:mm a")) +
        "."
    );
  }
}
