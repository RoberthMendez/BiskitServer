package com.example.biskit.controller.Pets;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.biskit.service.Pets.Especie.EspecieService;
import com.example.biskit.entities.pets.Especie;

@RestController
@RequestMapping("/especies")
@CrossOrigin(origins = "http://localhost:4200")
public class EspeciesController {
  
  @Autowired
  private EspecieService especieService;

  @GetMapping("")
  public List<Especie> getEspecies() {
    return especieService.getAllEspecies();
  }

  
}
