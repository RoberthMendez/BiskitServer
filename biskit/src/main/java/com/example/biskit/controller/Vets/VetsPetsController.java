package com.example.biskit.controller.Vets;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.biskit.service.Clients.ClientsService;
import com.example.biskit.service.Pets.PetsService;
import com.example.biskit.entities.pets.Pet;

@RestController
@RequestMapping("/vet/pets")
@CrossOrigin(origins = "http://localhost:4200")
public class VetsPetsController {

  @Autowired
  private PetsService petsService;

  @Autowired
  private ClientsService clientsService;

  // ================== MASCOTA ==================

  // ----- Crear Mascota (CREATE) -----
  @PostMapping("/add")
  public void agregarMascota(@RequestBody Pet pet) {
    pet = petsService.asignarRelacionesDePetPorIds(pet);
    clientsService.addPetToClient(pet.getOwner().getId(), pet);
    petsService.addPet(pet);
  }

  // ----- Mostrar Mascotas (READ) -----
  @GetMapping("")
  public List<Pet> mostrarMascotas() {
    return petsService.getPets();
  }

  // ----- Mostrar Mascota (READ) -----
  @GetMapping("/{id}")
  public Pet mostrarMascota(@PathVariable("id") Long id) {
    return petsService.getPetById(id);
  }

  // ----- Editar Mascota (UPDATE) -----
  @PutMapping("/update/{id}")
  public void updatePet(@PathVariable("id") Long id, @RequestBody Pet pet) {
    pet = petsService.asignarRelacionesDePetPorIds(pet);
    petsService.updatePet(pet);
  }

  // ----- Cambiar Estado de Mascota (PATCH) -----
  @PatchMapping("/update-estado/{id}")
  public void cambiarEstadoMascota(@PathVariable("id") Long id, @RequestBody Map<String, Boolean> body) {
    petsService.cambiarEstadoMascota(id, body.get("estado"));
  }

  // ----- Ver total de Mascotas -----
  @GetMapping("/count")
  public Long getTotalMascotas() {
    return petsService.getPetsCount();
  }

  // ----- Ver total de Mascotas Inactivas -----
  @GetMapping("/count/inactivos")
  public Long getTotalMascotasInactivas() {
    return petsService.getPetsInactivosCount();
  }

  // ----- Ver total de Mascotas Activas -----
  @GetMapping("/count/activos")
  public Long getTotalMascotasActivas() {
    return petsService.getPetsActivosCount();
  }

  // ----- Comprobar id de Mascota (GET) -----
  @GetMapping("/{id}/exists")
  public ResponseEntity<Void> checkPetId(@PathVariable Long id) {
      petsService.getPetById(id);
      return ResponseEntity.ok().build();
  }

}
