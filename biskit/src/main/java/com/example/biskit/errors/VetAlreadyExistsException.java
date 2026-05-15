package com.example.biskit.errors;

public class VetAlreadyExistsException extends RuntimeException {

  public VetAlreadyExistsException(String correo) {
    super("Ya existe un veterinario registrado con el correo " + correo);
  }
}
