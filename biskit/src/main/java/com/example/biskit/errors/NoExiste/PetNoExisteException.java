package com.example.biskit.errors.NoExiste;

public class PetNoExisteException extends RuntimeException {

  public PetNoExisteException(Long id) {
    super("No se encontró la mascota con id " + id);
  }
}
