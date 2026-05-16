package com.example.biskit.errors.NoExiste;

public class AdminNoExisteException extends RuntimeException {

  public AdminNoExisteException(Long id) {
    super("No se encontró el administrador con id " + id);
  }
}
