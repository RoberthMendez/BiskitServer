package com.example.biskit.controller.Pets;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.biskit.service.Pets.Raza.RazaService;
import com.example.biskit.entities.pets.Raza;

@RestController
@RequestMapping("/razas")
@CrossOrigin(origins = "http://localhost:4200")
public class RazasController {
  
  @Autowired
  private RazaService razaService;

  @GetMapping("")
  public List<Raza> getRazas() {
    return razaService.getAllRazas();
  }
  
}
