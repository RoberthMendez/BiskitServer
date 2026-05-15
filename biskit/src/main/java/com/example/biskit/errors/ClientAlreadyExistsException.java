package com.example.biskit.errors;

public class ClientAlreadyExistsException extends RuntimeException {

  public ClientAlreadyExistsException(String correo) {
    super("Ya existe un cliente registrado con el correo " + correo);
  }
}
