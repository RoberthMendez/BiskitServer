package com.example.biskit.errors;

public class StockInsuficienteException extends RuntimeException {

  public StockInsuficienteException(String message) {
    super(message);
  }

  public StockInsuficienteException(String nombreDroga, int disponibles, int requeridas) {
    super(
      "No hay suficientes unidades de " +
        nombreDroga +
        " en stock (disponibles: " +
        disponibles +
        ", requeridas: " +
        requeridas +
        ")"
    );
  }

  public StockInsuficienteException(String nombreDroga, boolean porNombre) {
    super("No hay suficientes unidades de " + nombreDroga + " en stock");
  }

  public StockInsuficienteException() {
    super("Stock insuficiente");
  }
}
