package com.example.biskit.controller.Vets;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/vet")
@CrossOrigin(origins = "http://localhost:4200")
public class VetsPetsController {

  @Autowired
  private PetsService petsService;

  @Autowired
  private ClientsService clientsService;

  // ================== MASCOTA ==================

  // ----- Crear Mascota (CREATE) -----
  @PostMapping("/pets/add")
  public void agregarMascota(@RequestBody Pet pet) {
    pet = petsService.asignarRelacionesDePetPorIds(pet);
    clientsService.addPetToClient(pet.getOwner().getId(), pet);
    petsService.addPet(pet);
  }

  // ----- Mostrar Mascotas (READ) -----
  @GetMapping("/pets")
  public List<Pet> mostrarMascotas() {
    return petsService.getPets();
  }

  // ----- Mostrar Mascota (READ) -----
  @GetMapping("/pets/{id}")
  public Pet mostrarMascota(@PathVariable("id") Long id) {
    return petsService.getPetById(id);
  }

  // ----- Editar Mascota (UPDATE) -----
  @PutMapping("/pets/update/{id}")
  public void updatePet(@PathVariable("id") Long id, @RequestBody Pet pet) {
    pet = petsService.asignarRelacionesDePetPorIds(pet);
    petsService.updatePet(pet);
  }

  // ----- Cambiar Estado de Mascota (DELETE) -----
  @PatchMapping("/pets/update-estado/{id}")
  public void cambiarEstadoMascota(@PathVariable("id") Long id, @RequestBody Map<String, Boolean> body) {
    petsService.cambiarEstadoMascota(id, body.get("estado"));
  }


}
