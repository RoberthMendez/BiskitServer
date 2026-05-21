package com.example.biskit.errors.YaExiste;

public class DrogaYaExisteException extends RuntimeException {

  public DrogaYaExisteException(String message) {
    super(message);
  }

  public DrogaYaExisteException(String nombreDroga, boolean porNombre) {
    super("Ya existe una droga con el nombre " + nombreDroga);
  }

  public DrogaYaExisteException() {
    super("La droga ya existe");
  }
}
