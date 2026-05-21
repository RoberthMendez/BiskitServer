package com.example.biskit.controller.Pets;

import com.example.biskit.entities.Pets.Raza;
import com.example.biskit.service.Pets.Raza.RazaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/razas")
@CrossOrigin(origins = "http://localhost:4200")
public class RazasController {

  @Autowired
  private RazaService razaService;

  // ----- Mostrar Razas (READ) -----
  // http://localhost:8080/razas
  @GetMapping("")
  public ResponseEntity<List<Raza>> getRazas() {
    return new ResponseEntity<>(razaService.getAllRazas(), HttpStatus.OK);
  }

  // ----- Crear Raza (CREATE) -----
  // http://localhost:8080/razas/add
  @PostMapping("/add")
  public ResponseEntity<Raza> agregarRaza(@RequestBody Raza raza) {
    return new ResponseEntity<>(razaService.saveRaza(raza), HttpStatus.CREATED);
  }
}
