package com.example.biskit.controller.Pets;

import com.example.biskit.entities.Pets.Enfermedad;
import com.example.biskit.service.Pets.Enfermedad.EnfermedadService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enfermedades")
public class EnfermedadesController {

  @Autowired
  private EnfermedadService enfermedadService;

  // ----- Mostrar Enfermedades (READ) -----
  // http://localhost:8080/enfermedades
  @GetMapping("")
  public ResponseEntity<List<Enfermedad>> getEnfermedades() {
    return new ResponseEntity<List<Enfermedad>>(
      enfermedadService.getAllEnfermedades(),
      HttpStatus.OK
    );
  }

  // ----- Crear Enfermedad (CREATE) -----
  // http://localhost:8080/enfermedades/add
  @PostMapping("/add")
  public ResponseEntity<Enfermedad> crearEnfermedad(@RequestBody Enfermedad enfermedad) {
    return new ResponseEntity<Enfermedad>(
      enfermedadService.saveEnfermedad(enfermedad),
      HttpStatus.CREATED
    );
  }
}
