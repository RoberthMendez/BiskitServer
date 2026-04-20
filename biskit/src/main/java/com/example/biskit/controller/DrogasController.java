package com.example.biskit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.biskit.entities.Droga;
import com.example.biskit.service.Tratamientos.DrogasService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/drogas")
@CrossOrigin(origins = "http://localhost:4200")
public class DrogasController {

  @Autowired
  private DrogasService drogasService;

  // ----- Crear Droga (CREATE) -----
  @PostMapping("/add")
  public void crearDroga(@RequestBody Droga droga) {
      drogasService.saveDroga(droga);
  }

  // ----- Mostrar Drogas (READ) ------
  @GetMapping("")
  public List<Droga> mostrarDrogas() {
      return drogasService.getDrogas();
  }

  // ----- Actualizar Droga (UPDATE) -----
  @PutMapping("/update/{id}")
  public void actualizarDroga(@PathVariable Long id, @RequestBody Droga droga) {
      droga.setId(id);
      drogasService.saveDroga(droga);
  }

}
