package com.example.biskit.controller.Tratamientos;

import com.example.biskit.entities.Droga;
import com.example.biskit.service.Tratamientos.DrogasService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/drogas")
@CrossOrigin(origins = "http://localhost:4200")
public class DrogasController {

  @Autowired
  private DrogasService drogasService;

  // ----- Crear Droga (CREATE) -----
  // http://localhost:8080/drogas/add
  @PostMapping("/add")
  public ResponseEntity<Droga> crearDroga(@RequestBody Droga droga) {
    return new ResponseEntity<>(drogasService.saveDroga(droga), HttpStatus.CREATED);
  }

  // ----- Mostrar Drogas (READ) ------
  // http://localhost:8080/drogas
  @GetMapping("")
  public ResponseEntity<List<Droga>> mostrarDrogas() {
    return new ResponseEntity<>(drogasService.getDrogas(), HttpStatus.OK);
  }

  // ----- Actualizar Droga (UPDATE) -----
  // http://localhost:8080/drogas/update/{id}
  @PutMapping("/update/{id}")
  public ResponseEntity<Droga> actualizarDroga(@PathVariable Long id, @RequestBody Droga droga) {
    droga.setId(id);
    return new ResponseEntity<>(drogasService.saveDroga(droga), HttpStatus.OK);
  }
}
