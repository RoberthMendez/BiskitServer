package com.example.biskit.controller;

import com.example.biskit.entities.DTOs.Pets.PetDTO;
import com.example.biskit.entities.DTOs.Pets.PetMapper;
import com.example.biskit.entities.DTOs.PetsFiltrosDTO;
import com.example.biskit.entities.DTOs.VetsFiltrosDTO;
import com.example.biskit.entities.Vets.Vet;
import com.example.biskit.service.Pets.PetsService;
import com.example.biskit.service.Vets.VetService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/filtros")
public class FiltrosController {

  @Autowired
  PetsService petsService;

  @Autowired
  VetService vetsService;

  @GetMapping("/pets")
  public ResponseEntity<List<PetDTO>> getPetsFiltrados(
    @RequestParam(required = false) Boolean estado,
    @RequestParam(required = false) String especie,
    @RequestParam(required = false) String raza,
    @RequestParam(required = false) Integer edad,
    @RequestParam(required = false) Float peso,
    @RequestParam(required = false) String enfermedad,
    @RequestParam(required = false) Integer tratamientos,
    @RequestParam(required = false) Long vetId,
    @RequestParam(required = false) Boolean misMascotas
  ) {
    PetsFiltrosDTO filtros = new PetsFiltrosDTO(
      estado,
      especie,
      raza,
      edad,
      peso,
      enfermedad,
      tratamientos,
      vetId,
      misMascotas
    );
    return ResponseEntity.ok(PetMapper.INSTANCE.toDTOList(petsService.getPetsFiltrados(filtros)));
  }

  @GetMapping("/vets")
  public ResponseEntity<List<Vet>> getVetsFiltrados(
    @RequestParam(required = false) Boolean estado,
    @RequestParam(required = false) String especialidad,
    @RequestParam(required = false) Integer tratamientos,
    @RequestParam(required = false) String pet,
    @RequestParam(required = false) Long vetId
  ) {
    VetsFiltrosDTO filtros = new VetsFiltrosDTO(estado, especialidad, tratamientos, pet, vetId);
    return new ResponseEntity<>(vetsService.getVetsFiltrados(filtros), HttpStatus.OK);
  }
}
