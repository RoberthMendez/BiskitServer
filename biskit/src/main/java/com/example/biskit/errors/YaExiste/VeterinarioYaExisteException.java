package com.example.biskit.errors.YaExiste;

public class VeterinarioYaExisteException extends RuntimeException {

  public VeterinarioYaExisteException(String correo) {
    super("Ya existe un veterinario registrado con el correo " + correo);
  }

  public VeterinarioYaExisteException() {
    super("Ya existe un veterinario registrado");
  }
}
