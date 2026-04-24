package com.example.biskit.service.Pets;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.biskit.entities.Tratamiento;
import com.example.biskit.entities.pets.Pet;
import com.example.biskit.errors.PetNotFoundException;
import com.example.biskit.repo.TratamientosRepo;
import com.example.biskit.repo.pets.PetsRepo;

import com.example.biskit.service.Pets.Enfermedad.EnfermedadService;
import com.example.biskit.service.Pets.Raza.RazaService;

import jakarta.transaction.Transactional;

@Service
public class PetsImpl implements PetsService {

  @Autowired
  private PetsRepo petsRepo;

  @Autowired
  private RazaService razaService;

  @Autowired
  private EnfermedadService enfermedadService;

  @Autowired
  private TratamientosRepo tratamientosRepo;

  @Override
  public List<Pet> getPets() {
    return petsRepo.findAll();
  }

  @Override
  public void addPet(Pet pet) {
    petsRepo.save(pet);
  }

  @Override
  public void updatePet(Pet pet) {
    petsRepo.save(pet);
  }

  @Override
  @Transactional
  public void deletePet(Long id) {

    Pet pet = petsRepo.findById(id).orElseThrow(() -> new PetNotFoundException(id));

    List<Tratamiento> tratamientos = tratamientosRepo.findByPetId(id);
    tratamientosRepo.deleteAll(tratamientos);

    petsRepo.delete(pet);

  }

  @Override
  public Pet getPetById(Long id) {
    return petsRepo.findById(id)
        .orElseThrow(() -> new PetNotFoundException(id));
  }

  @Override
  public Pet asignarRelacionesDePetPorIds(Pet pet) {
    pet.setRaza(razaService.getRazaById(pet.getRaza().getId()));
    pet.setEnfermedad(enfermedadService.getEnfermedadById(pet.getEnfermedad().getId()));
    return pet;
  }

  @Override
  public void cambiarEstadoMascota(Long id, boolean estado) {
    Pet pet = petsRepo.findById(id).orElseThrow(() -> new PetNotFoundException(id));
    pet.setEstado(estado);
    petsRepo.save(pet);
  }

  @Override
  public Long getMascotasCount() {
    return petsRepo.count();
  }

  @Override
  public Long getMascotasInactivasCount() {
    return petsRepo.countByEstadoFalse();
  }
}
