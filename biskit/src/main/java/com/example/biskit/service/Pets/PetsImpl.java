package com.example.biskit.service.Pets;

import com.example.biskit.entities.Tratamiento;
import com.example.biskit.entities.dtos.PetsFiltrosDto;
import com.example.biskit.entities.dtos.TopDto;
import com.example.biskit.entities.pets.Pet;
import com.example.biskit.errors.PetNotFoundException;
import com.example.biskit.repo.TratamientosRepo;
import com.example.biskit.repo.pets.PetsRepo;
import com.example.biskit.service.Pets.Enfermedad.EnfermedadService;
import com.example.biskit.service.Pets.Raza.RazaService;
import com.example.biskit.specifications.PetsSpecification;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Transactional
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
  public Pet addPet(Pet pet) {
    return petsRepo.save(pet);
  }

  @Override
  public Pet updatePet(Pet pet) {
    return petsRepo.save(pet);
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
    return petsRepo.findById(id).orElseThrow(() -> new PetNotFoundException(id));
  }

  @Override
  public Pet asignarRelacionesDePetPorIds(Pet pet) {
    if (pet == null) return null;

    if (pet.getRaza() != null && pet.getRaza().getId() != null) {
      pet.setRaza(razaService.getRazaById(pet.getRaza().getId()));
    } else {
      pet.setRaza(null);
    }

    if (pet.getEnfermedad() != null && pet.getEnfermedad().getId() != null) {
      pet.setEnfermedad(enfermedadService.getEnfermedadById(pet.getEnfermedad().getId()));
    } else {
      pet.setEnfermedad(null);
    }

    return pet;
  }

  @Override
  public Pet cambiarEstadoMascota(Long id, boolean estado) {
    Pet pet = petsRepo.findById(id).orElseThrow(() -> new PetNotFoundException(id));
    pet.setEstado(estado);
    return petsRepo.save(pet);
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
  public List<TopDto> getTop5Enfermedades() {
    List<Object[]> top5Enfermedades = petsRepo.findTop5Enfermedades(PageRequest.of(0, 5));
    List<TopDto> top5EnfermedadesDto = new ArrayList<>();
    for (int i = 0; i < top5Enfermedades.size(); i++) {
      Object[] row = top5Enfermedades.get(i);
      String nombreEnfermedad = (String) row[0];
      Long countPets = ((Number) row[1]).longValue();
      top5EnfermedadesDto.add(new TopDto((long) (i + 1), nombreEnfermedad, countPets));
    }
    return top5EnfermedadesDto;
  }

  @Override
  public List<Pet> getPetsFiltrados(PetsFiltrosDto filtros) {
    return petsRepo.findAll(PetsSpecification.conFiltros(filtros));
  }
}
