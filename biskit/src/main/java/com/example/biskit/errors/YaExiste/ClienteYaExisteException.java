package com.example.biskit.errors.YaExiste;

public class ClienteYaExisteException extends RuntimeException {

  public ClienteYaExisteException(String correo) {
    super("Ya existe un cliente registrado con el correo " + correo);
  }

  public ClienteYaExisteException() {
    super("Ya existe un cliente registrado");
  }
}
