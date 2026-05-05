package com.example.biskit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.example.biskit.entities.dtos.PetsFiltrosDto;
import com.example.biskit.entities.dtos.VetsFiltrosDto;
import com.example.biskit.entities.pets.Pet;
import com.example.biskit.service.Pets.PetsService;
import com.example.biskit.service.Vets.VetService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/filtros")
public class FiltrosController {

  @Autowired
  PetsService petsService;

  @Autowired
  VetService vetsService;

  @GetMapping("/pets")
  public ResponseEntity<List<Pet>> getPetsFiltrados(
    @RequestParam(required = false) Boolean estado,
    @RequestParam(required = false) String especie,
    @RequestParam(required = false) String raza,
    @RequestParam(required = false) Integer edad,
    @RequestParam(required = false) Float peso,
    @RequestParam(required = false) String enfermedad,
    @RequestParam(required = false) Integer tratamientos
  ) {

    PetsFiltrosDto filtros = new PetsFiltrosDto(estado, especie, raza, edad, peso, enfermedad, tratamientos);
    return ResponseEntity.ok(petsService.getPetsFiltrados(filtros));

  }

  @GetMapping("/vets")
  public ResponseEntity<?> getVetsFiltrados(
      @RequestParam(required = false) Boolean estado,
      @RequestParam(required = false) String especialidad,
      @RequestParam(required = false) Integer tratamientos,
      @RequestParam(required = false) String pet,
      @RequestParam(required = false) Boolean misMascotas,
      @RequestParam(required = false) Long vetId
  ) {
        if (misMascotas && vetId == null) {
          throw new IllegalArgumentException("Se debe enviar el id del veterinario cuando misMascotas=true");
        }
        if (misMascotas) {
            return ResponseEntity.ok(vetsService.getPetsTratadosPorVet(vetId));
        }

        VetsFiltrosDto filtros = new VetsFiltrosDto(estado, especialidad, tratamientos, pet, misMascotas, vetId);
        return ResponseEntity.ok(vetsService.getVetsFiltrados(filtros));
  }
  
  
}
