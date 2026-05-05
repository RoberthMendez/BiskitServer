package com.example.biskit.service.Pets;

import java.util.List;

import com.example.biskit.entities.pets.Pet;
import com.example.biskit.entities.dtos.PetsFiltrosDto;
import com.example.biskit.entities.dtos.TopDto;

public interface PetsService {

  public List<Pet> getPets();

  public Pet getPetById(Long id);

  public Pet addPet(Pet pet);

  public Pet updatePet(Pet pet);

  public void deletePet(Long id);

  public Pet asignarRelacionesDePetPorIds(Pet pet);

  public Pet cambiarEstadoMascota(Long id, boolean estado);

  public Long getPetsCount();

  public Long getPetsInactivosCount();

  public Long getMascotasActivasCount();

  public Long getPetsActivosCount();

  public List<TopDto> getTop5Enfermedades();

  public List<Pet> getPetsFiltrados(PetsFiltrosDto filtros);

}
