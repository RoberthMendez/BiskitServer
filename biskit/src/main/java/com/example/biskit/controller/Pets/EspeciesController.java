package com.example.biskit.controller.Pets;

import com.example.biskit.entities.pets.Especie;
import com.example.biskit.service.Pets.Especie.EspecieService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/especies")
@CrossOrigin(origins = "http://localhost:4200")
public class EspeciesController {

  @Autowired
  private EspecieService especieService;

  // http://localhost:8080/especies
  @GetMapping("")
  public ResponseEntity<List<Especie>> getEspecies() {
    return new ResponseEntity<List<Especie>>(especieService.getAllEspecies(), HttpStatus.OK);
  }
}
