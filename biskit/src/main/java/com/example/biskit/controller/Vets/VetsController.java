package com.example.biskit.controller.Vets;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.biskit.entities.pets.Pet;
import com.example.biskit.entities.vets.Vet;
import com.example.biskit.service.Vets.VetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/vets")
@CrossOrigin(origins = "http://localhost:4200")
public class VetsController {

  @Autowired
  private VetService vetService;

  // ----- Crear Veterinario (CREATE) -----
  @PostMapping("/add")
  public void crearVeterinario(@RequestBody Vet vet) {
      vetService.addVet(vet);
  }

  // ----- Mostrar Veterinarios (READ) -----
  @GetMapping("")
  public List<Vet> mostrarVets() {
      return vetService.getVets();
  }

  // ----- Mostrar Veterinario por ID (READ) -----
  @GetMapping("/{id}")
  public Vet getVetById(@PathVariable Long id) {
      return vetService.getVetById(id);
  }

  // ----- Aactualizar Veterinario (UPDATE) -----
  @PutMapping("update/{id}")
  public void actualizarVet(@PathVariable Long id, @RequestBody Vet vet) {
      vet.setId(id);
      vetService.saveVet(vet);
  }


  // ----- Contar Tratamientos de un Veterinario -----
  @GetMapping("/{id}/tratamientos/count")
  public Long getVetTratamientosCount(@PathVariable Long id) {
      return vetService.getVetTratamientosCount(id);
  }

  // ----- Obtener Mascotas Tratadas por un Veterinario -----
  @GetMapping("/{id}/pets")
  public List<Pet> getPetsTratadosPorVet(@PathVariable Long id) {
      return vetService.getPetsTratadosPorVet(id);
  }

}
