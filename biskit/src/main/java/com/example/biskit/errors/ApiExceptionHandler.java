package com.example.biskit.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(VetNotAvailableException.class)
  public ResponseEntity<ErrorResponse> handleVetNotAvailable(VetNotAvailableException ex) {
    ErrorResponse error = new ErrorResponse(
      "Veterinario no disponible",
      ex.getMessage(),
      HttpStatus.BAD_REQUEST.value()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(MascotaInactivaException.class)
  public ResponseEntity<ErrorResponse> handleMascotaInactiva(MascotaInactivaException ex) {
    ErrorResponse error = new ErrorResponse(
      "Regla de negocio",
      ex.getMessage(),
      HttpStatus.BAD_REQUEST.value()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(StockInsuficienteException.class)
  public ResponseEntity<ErrorResponse> handleStockInsuficiente(StockInsuficienteException ex) {
    ErrorResponse error = new ErrorResponse(
      "Regla de negocio",
      ex.getMessage(),
      HttpStatus.BAD_REQUEST.value()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(VetNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleVetNotFound(VetNotFoundException ex) {
    ErrorResponse error = new ErrorResponse(
      "Vet id no encontrado",
      ex.getMessage(),
      HttpStatus.BAD_REQUEST.value()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(VetAlreadyExistsException.class)
  public ResponseEntity<ErrorResponse> handleVetAlreadyExists(VetAlreadyExistsException ex) {
    ErrorResponse error = new ErrorResponse(
      "Veterinario ya existe",
      ex.getMessage(),
      HttpStatus.BAD_REQUEST.value()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(ClientAlreadyExistsException.class)
  public ResponseEntity<ErrorResponse> handleClientAlreadyExists(ClientAlreadyExistsException ex) {
    ErrorResponse error = new ErrorResponse(
      "Cliente ya existe",
      ex.getMessage(),
      HttpStatus.BAD_REQUEST.value()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(ClientNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleClientNotFound(ClientNotFoundException ex) {
    ErrorResponse error = new ErrorResponse(
      "Client id no encontrado",
      ex.getMessage(),
      HttpStatus.BAD_REQUEST.value()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(PetNotFoundException.class)
  public ResponseEntity<ErrorResponse> handlePetNotFound(PetNotFoundException ex) {
    ErrorResponse error = new ErrorResponse(
      "Pet id no encontrado",
      ex.getMessage(),
      HttpStatus.BAD_REQUEST.value()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(AdminNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleAdminNotFound(AdminNotFoundException ex) {
    ErrorResponse error = new ErrorResponse(
      "Admin id no encontrado",
      ex.getMessage(),
      HttpStatus.BAD_REQUEST.value()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
    ErrorResponse error = new ErrorResponse(
      "Error interno",
      "Ocurrio un error inesperado",
      HttpStatus.INTERNAL_SERVER_ERROR.value()
    );
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
    ErrorResponse error = new ErrorResponse(
      "Parámetro faltante",
      ex.getMessage(),
      HttpStatus.BAD_REQUEST.value()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(EnfermedadYaExisteException.class)
  public ResponseEntity<ErrorResponse> handleEnfermedadYaExiste(EnfermedadYaExisteException ex) {
    ErrorResponse error = new ErrorResponse(
      "Enfermedad ya existe",
      ex.getMessage(),
      HttpStatus.BAD_REQUEST.value()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(RazaYaExisteException.class)
  public ResponseEntity<ErrorResponse> handleRazaYaExiste(RazaYaExisteException ex) {
    ErrorResponse error = new ErrorResponse(
      "Raza ya existe",
      ex.getMessage(),
      HttpStatus.BAD_REQUEST.value()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(DrogaYaExisteException.class)
  public ResponseEntity<ErrorResponse> handleDrogaYaExiste(DrogaYaExisteException ex) {
    ErrorResponse error = new ErrorResponse(
      "Droga ya existe",
      ex.getMessage(),
      HttpStatus.BAD_REQUEST.value()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(EspecialidadYaExisteException.class)
  public ResponseEntity<ErrorResponse> handleEspecialidadYaExiste(
    EspecialidadYaExisteException ex
  ) {
    ErrorResponse error = new ErrorResponse(
      "Especialidad ya existe",
      ex.getMessage(),
      HttpStatus.BAD_REQUEST.value()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }
}
