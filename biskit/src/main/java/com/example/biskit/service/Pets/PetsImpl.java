package com.example.biskit.service.Pets;

import com.example.biskit.entities.Client;
import com.example.biskit.entities.DTOs.KPIs.TopDTO;
import com.example.biskit.entities.DTOs.PetsFiltrosDTO;
import com.example.biskit.entities.Pets.Pet;
import com.example.biskit.entities.Tratamiento;
import com.example.biskit.errors.NoExiste.ClientNoExisteException;
import com.example.biskit.errors.NoExiste.PetNoExisteException;
import com.example.biskit.repo.ClientsRepo;
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
  private ClientsRepo clientsRepo;

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
    Pet petConRelaciones = asignarRelacionesDePetPorIds(pet);
    petConRelaciones = asignarOwnerDePetPorId(petConRelaciones);
    return petsRepo.save(petConRelaciones);
  }

  @Override
  public Pet updatePet(Long id, Pet pet) {
    Pet petExistente = petsRepo.findById(id).orElseThrow(() -> new PetNoExisteException(id));
    Pet petConRelaciones = asignarRelacionesDePetPorIds(pet);
    petConRelaciones = asignarOwnerDePetPorId(petConRelaciones);

    petExistente.setNombre(petConRelaciones.getNombre());
    petExistente.setEstado(petConRelaciones.isEstado());
    petExistente.setFechaNacimiento(petConRelaciones.getFechaNacimiento());
    petExistente.setPeso(petConRelaciones.getPeso());
    petExistente.setUrlFoto(petConRelaciones.getUrlFoto());
    petExistente.setEnfermedad(petConRelaciones.getEnfermedad());
    petExistente.setRaza(petConRelaciones.getRaza());
    petExistente.setOwner(
      petConRelaciones.getOwner() != null ? petConRelaciones.getOwner() : petExistente.getOwner()
    );

    return petsRepo.save(petExistente);
  }

  @Override
  @Transactional
  public void deletePet(Long id) {
    Pet pet = petsRepo.findById(id).orElseThrow(() -> new PetNoExisteException(id));

    List<Tratamiento> tratamientos = tratamientosRepo.findByPetId(id);
    tratamientosRepo.deleteAll(tratamientos);

    petsRepo.delete(pet);
  }

  @Override
  public Pet getPetById(Long id) {
    return petsRepo.findById(id).orElseThrow(() -> new PetNoExisteException(id));
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

  private Pet asignarOwnerDePetPorId(Pet pet) {
    if (pet == null) return null;

    if (pet.getOwner() != null && pet.getOwner().getId() != null) {
      Client owner = clientsRepo
        .findById(pet.getOwner().getId())
        .orElseThrow(() -> new ClientNoExisteException(pet.getOwner().getId()));
      pet.setOwner(owner);
    } else {
      pet.setOwner(null);
    }

    return pet;
  }

  @Override
  public Boolean cambiarEstadoMascota(Long id) {
    Pet pet = petsRepo.findById(id).orElseThrow(() -> new PetNoExisteException(id));
    pet.setEstado(!pet.isEstado());
    petsRepo.save(pet);
    return pet.isEstado();
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
  public List<TopDTO> getTop5Enfermedades() {
    List<Object[]> top5Enfermedades = petsRepo.findTop5Enfermedades(PageRequest.of(0, 5));
    List<TopDTO> top5EnfermedadesDto = new ArrayList<>();
    for (int i = 0; i < top5Enfermedades.size(); i++) {
      Object[] row = top5Enfermedades.get(i);
      String nombreEnfermedad = (String) row[0];
      Long countPets = ((Number) row[1]).longValue();
      top5EnfermedadesDto.add(new TopDTO((long) (i + 1), nombreEnfermedad, countPets));
    }
    return top5EnfermedadesDto;
  }

  @Override
  public List<Pet> getPetsFiltrados(PetsFiltrosDTO filtros) {
    return petsRepo.findAll(PetsSpecification.conFiltros(filtros));
  }

  @Override
  public Client getPetOwner(Long id) {
    return petsRepo
      .findById(id)
      .orElseThrow(() -> new PetNoExisteException(id))
      .getOwner();
  }

  @Override
  public List<Tratamiento> getPetTratamientos(Long id) {
    Pet pet = petsRepo.findById(id).orElseThrow(() -> new PetNoExisteException(id));
    return tratamientosRepo.findByPetId(pet.getId());
  }
}
