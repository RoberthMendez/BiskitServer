package com.example.biskit.errors.YaExiste;

public class EspecialidadYaExisteException extends RuntimeException {

  public EspecialidadYaExisteException(String message) {
    super(message);
  }

  public EspecialidadYaExisteException(String nombreEspecialidad, boolean porNombre) {
    super("Ya existe la especialidad " + nombreEspecialidad);
  }

  public EspecialidadYaExisteException() {
    super("La especialidad ya existe");
  }
}
