package com.example.biskit.service.Pets;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.biskit.entities.pets.Pet;
import com.example.biskit.errors.PetNotFoundException;
import com.example.biskit.repo.pets.PetsRepo;

@Service
public class PetsImpl implements PetsService {

  @Autowired
  private PetsRepo petsRepo;

  @Override
  public Collection<Pet> getPets() {
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
  public void deletePet(Long id) {
    Pet pet = petsRepo.findById(id).orElseThrow(() -> new PetNotFoundException(id));
    pet.setEstado(false);
    petsRepo.save(pet);
  }

  @Override
  public Pet getPetById(Long id) {
    return petsRepo.findById(id)
        .orElseThrow(() -> new PetNotFoundException(id));
  }
}
