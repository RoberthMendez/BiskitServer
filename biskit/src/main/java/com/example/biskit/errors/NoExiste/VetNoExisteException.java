package com.example.biskit.errors.NoExiste;

public class VetNoExisteException extends RuntimeException {

  public VetNoExisteException(Long id) {
    super("No se encontró el veterinario con id " + id);
  }
}
