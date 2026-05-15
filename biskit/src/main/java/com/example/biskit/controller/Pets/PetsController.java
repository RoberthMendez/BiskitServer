package com.example.biskit.controller.Pets;

import com.example.biskit.entities.Client;
import com.example.biskit.entities.DTOs.Pets.PetDTO;
import com.example.biskit.entities.DTOs.Pets.PetMapper;
import com.example.biskit.entities.Tratamiento;
import com.example.biskit.entities.pets.Pet;
import com.example.biskit.service.Pets.PetsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pets")
@CrossOrigin(origins = "http://localhost:4200")
public class PetsController {

  @Autowired
  private PetsService petsService;

  // ----- Crear Mascota (CREATE) -----
  // http://localhost:8080/pets/add
  @PostMapping("/add")
  public ResponseEntity<Pet> agregarMascota(@RequestBody Pet pet) {
    return new ResponseEntity<>(petsService.addPet(pet), HttpStatus.CREATED);
  }

  // ----- Mostrar Mascotas (READ) -----
  // http://localhost:8080/pets
  @GetMapping("")
  public ResponseEntity<List<PetDTO>> mostrarMascotas() {
    return new ResponseEntity<>(PetMapper.INSTANCE.toDTOList(petsService.getPets()), HttpStatus.OK);
  }

  // ----- Mostrar Mascota (READ) -----
  // http://localhost:8080/pets/{id}
  @GetMapping("/{id}")
  public ResponseEntity<PetDTO> mostrarMascota(@PathVariable("id") Long id) {
    return new ResponseEntity<>(
      PetMapper.INSTANCE.convert(petsService.getPetById(id)),
      HttpStatus.OK
    );
  }

  // ----- Mostrar Dueño de la Mascota (READ) -----
  // http://localhost:8080/pets/{id}/owner
  @GetMapping("/{id}/owner")
  public ResponseEntity<Client> mostrarDueñoMascota(@PathVariable("id") Long id) {
    return new ResponseEntity<>(petsService.getPetOwner(id), HttpStatus.OK);
  }

  // ----- Mostrar Tratamientos de una Mascota (READ) ------
  // http://localhost:8080/pets/{id}/tratamientos
  @GetMapping("/{id}/tratamientos")
  public ResponseEntity<List<Tratamiento>> getTratamientosMascota(@PathVariable Long id) {
    return new ResponseEntity<>(petsService.getPetTratamientos(id), HttpStatus.OK);
  }

  // ----- Editar Mascota (UPDATE) -----
  // http://localhost:8080/pets/update/{id}
  @PutMapping("/update/{id}")
  public ResponseEntity<Pet> updatePet(@PathVariable("id") Long id, @RequestBody Pet pet) {
    return new ResponseEntity<>(petsService.updatePet(id, pet), HttpStatus.OK);
  }

  // ----- Cambiar Estado de Mascota (PATCH) -----
  // http://localhost:8080/pets/update-estado/{id}
  @PatchMapping("/update-estado/{id}")
  public ResponseEntity<Boolean> cambiarEstadoMascota(@PathVariable("id") Long id) {
    return new ResponseEntity<>(petsService.cambiarEstadoMascota(id), HttpStatus.OK);
  }

  // http://localhost:8080/pets/count
  @GetMapping("/count")
  public ResponseEntity<Long> getTotalMascotas() {
    return new ResponseEntity<>(petsService.getPetsCount(), HttpStatus.OK);
  }

  // http://localhost:8080/pets/count/inactivos
  @GetMapping("/count/inactivos")
  public ResponseEntity<Long> getTotalMascotasInactivas() {
    return new ResponseEntity<>(petsService.getPetsInactivosCount(), HttpStatus.OK);
  }

  // http://localhost:8080/pets/count/activos
  @GetMapping("/count/activos")
  public ResponseEntity<Long> getTotalMascotasActivas() {
    return new ResponseEntity<>(petsService.getPetsActivosCount(), HttpStatus.OK);
  }

  // ----- Comprobar id de Mascota (GET) -----
  // http://localhost:8080/pets/{id}/exists
  @GetMapping("/{id}/exists")
  public ResponseEntity<Void> checkPetId(@PathVariable Long id) {
    petsService.getPetById(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
