package com.example.biskit.errors.NoExiste;

public class ClientNoExisteException extends RuntimeException {

  public ClientNoExisteException(Long id) {
    super("No se encontró el cliente con id " + id);
  }
}
