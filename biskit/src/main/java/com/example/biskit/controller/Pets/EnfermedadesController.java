package com.example.biskit.controller.Pets;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.biskit.service.Pets.Enfermedad.EnfermedadService;
import com.example.biskit.entities.pets.Enfermedad;

@RestController
@RequestMapping("/enfermedades")
@CrossOrigin(origins = "http://localhost:4200")
public class EnfermedadesController {
  
  @Autowired
  private EnfermedadService enfermedadService;

  @GetMapping("")
  public List<Enfermedad> getEnfermedades() {
    return enfermedadService.getAllEnfermedades();
  }

  // ----- Crear Enfermedad (CREATE) -----
  @PostMapping("/add")
  public void crearEnfermedad(@RequestBody Enfermedad enfermedad) {
    if (enfermedad != null) {
      enfermedadService.saveEnfermedad(enfermedad);
    }
  }
  
}
