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

import org.springframework.http.HttpStatus;

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
  public ResponseEntity<Void> agregarMascota(@RequestBody Pet pet) {

    pet = petsService.asignarRelacionesDePetPorIds(pet);
    clientsService.addPetToClient(pet.getOwner().getId(), pet);
    Pet addedPet = petsService.addPet(pet);

    if (addedPet == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  // ----- Mostrar Mascotas (READ) -----
  @GetMapping("")
  public ResponseEntity<List<Pet>> mostrarMascotas() {
    return ResponseEntity.ok(petsService.getPets());
  }

  // ----- Mostrar Mascota (READ) -----
  @GetMapping("/{id}")
  public ResponseEntity<Pet> mostrarMascota(@PathVariable("id") Long id) {
    Pet pet = petsService.getPetById(id);
    if (pet == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return ResponseEntity.ok(pet);
  }

  // ----- Editar Mascota (UPDATE) -----
  @PutMapping("/update/{id}")
  public ResponseEntity<Void> updatePet(@PathVariable("id") Long id, @RequestBody Pet pet) {
    pet = petsService.asignarRelacionesDePetPorIds(pet);
    Pet updatedPet = petsService.updatePet(pet);

    if (updatedPet == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    return ResponseEntity.ok().build();
  }

  // ----- Cambiar Estado de Mascota (PATCH) -----
  @PatchMapping("/update-estado/{id}")
  public ResponseEntity<Void> cambiarEstadoMascota(@PathVariable("id") Long id,
      @RequestBody Map<String, Boolean> body) {
    Pet updatedPet = petsService.cambiarEstadoMascota(id, body.get("estado"));
    if (updatedPet == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    return ResponseEntity.ok().build();
  }

  // ----- Ver total de Mascotas -----
  @GetMapping("/count")
  public ResponseEntity<Long> getTotalMascotas() {
    Long count = petsService.getPetsCount();
    if (count == null) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    return ResponseEntity.ok(count);
  }

  // ----- Ver total de Mascotas Inactivas -----
  @GetMapping("/count/inactivos")
  public ResponseEntity<Long> getTotalMascotasInactivas() {
    Long count = petsService.getPetsInactivosCount();
    if (count == null) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    return ResponseEntity.ok(count);
  }

  // ----- Ver total de Mascotas Activas -----
  @GetMapping("/count/activos")
  public ResponseEntity<Long> getTotalMascotasActivas() {
    Long count = petsService.getPetsActivosCount();
    if (count == null) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    return ResponseEntity.ok(count);
  }

  // ----- Comprobar id de Mascota (GET) -----
  @GetMapping("/{id}/exists")
  public ResponseEntity<Void> checkPetId(@PathVariable Long id) {
    if (petsService.getPetById(id) == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return ResponseEntity.ok().build();
  }

}
