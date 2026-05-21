package com.example.biskit.errors;

public class MascotaInactivaException extends RuntimeException {

  public MascotaInactivaException(String message) {
    super(message);
  }

  public MascotaInactivaException() {
    super("La mascota está inactiva");
  }
}
