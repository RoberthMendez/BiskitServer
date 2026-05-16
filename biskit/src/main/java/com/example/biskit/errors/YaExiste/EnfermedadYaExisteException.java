package com.example.biskit.errors.YaExiste;

public class EnfermedadYaExisteException extends RuntimeException {

  public EnfermedadYaExisteException(String message) {
    super(message);
  }

  public EnfermedadYaExisteException(String nombreEnfermedad, boolean porNombre) {
    super("Ya existe la enfermedad " + nombreEnfermedad);
  }

  public EnfermedadYaExisteException() {
    super("La enfermedad ya existe");
  }
}
