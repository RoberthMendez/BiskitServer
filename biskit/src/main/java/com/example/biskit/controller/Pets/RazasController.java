package com.example.biskit.controller.Pets;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.biskit.service.Pets.Especie.EspecieService;
import com.example.biskit.service.Pets.Raza.RazaService;
import com.example.biskit.entities.pets.Especie;
import com.example.biskit.entities.pets.Raza;

@RestController
@RequestMapping("/razas")
@CrossOrigin(origins = "http://localhost:4200")
public class RazasController {
  
  @Autowired
  private RazaService razaService;

  @Autowired
  private EspecieService especieService;

  @GetMapping("")
  public List<Raza> getRazas() {
    return razaService.getAllRazas();
  }

    // Añadir Raza
  @PostMapping("/add")
  public void agregarRaza(@RequestBody Raza raza) {

    String nombreRaza = raza.getNombre() == null ? "" : raza.getNombre().trim();
    String nombreEspecie = raza.getEspecie() == null ? "" : raza.getEspecie().getNombre().trim();
    Raza razaExistente = razaService.getRazaByNombre(nombreRaza);
    Especie especie = especieService.getEspecieByNombre(nombreEspecie);
  
    if (razaExistente == null && especie != null) {
      raza = Raza.builder().nombre(nombreRaza).especie(especie).build();
      razaService.saveRaza(raza);
    }

  } 
  
}
