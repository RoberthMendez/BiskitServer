package com.example.biskit.service.Pets;

import com.example.biskit.entities.Client;
import com.example.biskit.entities.DTOs.PetsFiltrosDto;
import com.example.biskit.entities.DTOs.TopDto;
import com.example.biskit.entities.Tratamiento;
import com.example.biskit.entities.pets.Pet;
import java.util.List;

public interface PetsService {
  public List<Pet> getPets();

  public Pet getPetById(Long id);

  public Pet addPet(Pet pet);

  public Pet updatePet(Long id, Pet pet);

  public void deletePet(Long id);

  public Pet asignarRelacionesDePetPorIds(Pet pet);

  public Boolean cambiarEstadoMascota(Long id);

  public Long getPetsCount();

  public Long getPetsInactivosCount();

  public Long getMascotasActivasCount();

  public Long getPetsActivosCount();

  public List<TopDto> getTop5Enfermedades();

  public List<Pet> getPetsFiltrados(PetsFiltrosDto filtros);

  public Client getPetOwner(Long id);

  public List<Tratamiento> getPetTratamientos(Long id);
}
