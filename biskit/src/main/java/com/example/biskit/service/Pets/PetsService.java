package com.example.biskit.service.Pets;

import java.util.List;

import com.example.biskit.entities.pets.Pet;

public interface PetsService {

  public List<Pet> getPets();

  public Pet getPetById(Long id);

  public void addPet(Pet pet);

  public void updatePet(Pet pet);

  public void deletePet(Long id);

  public Pet asignarRelacionesDePetPorIds(Pet pet);

  public void cambiarEstadoMascota(Long id, boolean estado);

  public Long getPetsCount ();

  public Long getPetsInactivosCount();

  public Long getPetsActivosCount();

}
