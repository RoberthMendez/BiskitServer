package com.example.biskit.errors.YaExiste;

public class RazaYaExisteException extends RuntimeException {

  public RazaYaExisteException(String message) {
    super(message);
  }

  public RazaYaExisteException(String nombreRaza, boolean porNombre) {
    super("Ya existe la raza " + nombreRaza);
  }

  public RazaYaExisteException() {
    super("La raza ya existe");
  }
}
