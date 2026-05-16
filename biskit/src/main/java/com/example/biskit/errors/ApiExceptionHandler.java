package com.example.biskit.errors;

import com.example.biskit.errors.NoExiste.AdminNoExisteException;
import com.example.biskit.errors.NoExiste.ClientNoExisteException;
import com.example.biskit.errors.NoExiste.PetNoExisteException;
import com.example.biskit.errors.NoExiste.VetNoExisteException;
import com.example.biskit.errors.YaExiste.ClienteYaExisteException;
import com.example.biskit.errors.YaExiste.DrogaYaExisteException;
import com.example.biskit.errors.YaExiste.EnfermedadYaExisteException;
import com.example.biskit.errors.YaExiste.EspecialidadYaExisteException;
import com.example.biskit.errors.YaExiste.RazaYaExisteException;
import com.example.biskit.errors.YaExiste.VeterinarioYaExisteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(VeterinarioNoDisponibleException.class)
  public ResponseEntity<ErrorResponse> handleVetNotAvailable(VeterinarioNoDisponibleException ex) {
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

  @ExceptionHandler(VetNoExisteException.class)
  public ResponseEntity<ErrorResponse> handleVetNotFound(VetNoExisteException ex) {
    ErrorResponse error = new ErrorResponse(
      "Vet id no encontrado",
      ex.getMessage(),
      HttpStatus.BAD_REQUEST.value()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(VeterinarioYaExisteException.class)
  public ResponseEntity<ErrorResponse> handleVetAlreadyExists(VeterinarioYaExisteException ex) {
    ErrorResponse error = new ErrorResponse(
      "Veterinario ya existe",
      ex.getMessage(),
      HttpStatus.BAD_REQUEST.value()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(ClienteYaExisteException.class)
  public ResponseEntity<ErrorResponse> handleClientAlreadyExists(ClienteYaExisteException ex) {
    ErrorResponse error = new ErrorResponse(
      "Cliente ya existe",
      ex.getMessage(),
      HttpStatus.BAD_REQUEST.value()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(ClientNoExisteException.class)
  public ResponseEntity<ErrorResponse> handleClientNotFound(ClientNoExisteException ex) {
    ErrorResponse error = new ErrorResponse(
      "Client id no encontrado",
      ex.getMessage(),
      HttpStatus.BAD_REQUEST.value()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(PetNoExisteException.class)
  public ResponseEntity<ErrorResponse> handlePetNotFound(PetNoExisteException ex) {
    ErrorResponse error = new ErrorResponse(
      "Pet id no encontrado",
      ex.getMessage(),
      HttpStatus.BAD_REQUEST.value()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(AdminNoExisteException.class)
  public ResponseEntity<ErrorResponse> handleAdminNotFound(AdminNoExisteException ex) {
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
