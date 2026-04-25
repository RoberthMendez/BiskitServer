package com.example.biskit.service.Pets;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.biskit.entities.Tratamiento;
import com.example.biskit.entities.dtos.TopEnfermedadDto;
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
  public Long getPetsCount() {
    return petsRepo.count();
  }

  @Override
  public Long getMascotasActivasCount() {
    return petsRepo.countByEstadoTrue();
  }

  @Override
  public Long getPetsInactivosCount() {
    return petsRepo.countByEstadoFalse();
  }

  @Override
  public Long getPetsActivosCount() {
    return petsRepo.countByEstadoTrue();
  }

  @Override
  public List<TopEnfermedadDto> getTop5Enfermedades() {
    List<Object[]> top5Enfermedades = petsRepo.findTop5Enfermedades(PageRequest.of(0, 5));
    List<TopEnfermedadDto> top5EnfermedadesDto = new ArrayList<>();
    for (int i = 0; i < top5Enfermedades.size(); i++) {
      Object[] row = top5Enfermedades.get(i);
      String nombreEnfermedad = (String) row[0];
      Long countPets = ((Number) row[1]).longValue();
      top5EnfermedadesDto.add(new TopEnfermedadDto((long) (i + 1), nombreEnfermedad, countPets));
    }
    return top5EnfermedadesDto;
  }
}
