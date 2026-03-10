package com.example.biskit.service.Pets;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.biskit.entities.Pet;
import com.example.biskit.repo.PetsRepo;

import com.example.biskit.errors.PetNotFoundException;

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
    petsRepo.deleteById(id);
  }
  @Override
public Pet getPetById(Long id) {
  return petsRepo.findById(id)
      .orElseThrow(() -> new PetNotFoundException(id));
}
}
