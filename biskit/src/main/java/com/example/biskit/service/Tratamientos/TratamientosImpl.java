package com.example.biskit.service.Tratamientos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.biskit.entities.Droga;
import com.example.biskit.entities.Tratamiento;
import com.example.biskit.entities.dtos.TratamientoDto;
import com.example.biskit.entities.vets.Vet;
import com.example.biskit.entities.pets.Pet;
import com.example.biskit.repo.TratamientosRepo;
import com.example.biskit.service.Pets.PetsService;
import com.example.biskit.service.Vets.VetService;

@Service
public class TratamientosImpl implements TratamientosService {

    @Autowired
    private TratamientosRepo tratamientosRepo;

    @Autowired
    private VetService vetService;

    @Autowired
    private PetsService petsService;

    @Autowired
    private DrogasService drogasService;

    @Override
    public Tratamiento getTratamientoById(Long id) {
        return tratamientosRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró tratamiento con id: " + id));
    }

    @Override
    public void addTratamiento(TratamientoDto tratamientoDto) {
        if (tratamientoDto == null) {
            throw new RuntimeException("El tratamiento no puede ser nulo");
        }

        if (tratamientoDto.getPetId() == null) {
            throw new RuntimeException("El tratamiento debe incluir una mascota válida");
        }

        if (tratamientoDto.getVetId() == null) {
            throw new RuntimeException("El tratamiento debe incluir un veterinario válido");
        }

        Pet pet = petsService.getPetById(tratamientoDto.getPetId());
        Vet vet = vetService.getVetById(tratamientoDto.getVetId());

        List<Droga> drogasPersistidas = new ArrayList<>();
        if (tratamientoDto.getDrogasIds() != null) {
            for (Long drogaId : tratamientoDto.getDrogasIds()) {
                if (drogaId != null) {
                    drogasPersistidas.add(drogasService.getDrogaById(drogaId));
                }
            }
        }

        Tratamiento tratamiento = new Tratamiento();
        tratamiento.setId(tratamientoDto.getId());
        tratamiento.setFecha(tratamientoDto.getFecha());
        tratamiento.setPet(pet);
        tratamiento.setVet(vet);
        tratamiento.setDrogas(drogasPersistidas);
        tratamientosRepo.save(tratamiento);
    }

    @Override
    @Transactional
    public void updateTratamiento(Long id, TratamientoDto tratamientoDto) {

        if (tratamientoDto == null) {
            throw new RuntimeException("El tratamiento no puede ser nulo");
        }

        if (tratamientoDto.getPetId() == null) {
            throw new RuntimeException("El tratamiento debe incluir una mascota válida");
        }

        if (tratamientoDto.getVetId() == null) {
            throw new RuntimeException("El tratamiento debe incluir un veterinario válido");
        }

        Pet pet = petsService.getPetById(tratamientoDto.getPetId());
        Vet vet = vetService.getVetById(tratamientoDto.getVetId());

        List<Droga> drogasPersistidas = new ArrayList<>();
        if (tratamientoDto.getDrogasIds() != null) {
            for (Long drogaId : tratamientoDto.getDrogasIds()) {
                if (drogaId != null) {
                    drogasPersistidas.add(drogasService.getDrogaById(drogaId));
                }
            }
        }

        Tratamiento existingTratamiento = tratamientosRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró tratamiento con id: " + id));

        existingTratamiento.setFecha(tratamientoDto.getFecha());
        existingTratamiento.setPet(pet);
        existingTratamiento.setVet(vet);
        existingTratamiento.setDrogas(drogasPersistidas);

        tratamientosRepo.save(existingTratamiento);

    }

    @Override
    public void deleteTratamiento(Long id) {
        Tratamiento tratamiento = tratamientosRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró tratamiento con id: " + id));

        Vet vet = tratamiento.getVet();
        Pet pet = tratamiento.getPet();
        List<Droga> drogas = tratamiento.getDrogas();

        vet.getTratamientos().remove(tratamiento);
        vetService.saveVet(vet);
        pet.getTratamientos().remove(tratamiento);
        petsService.updatePet(pet);

        for (Droga droga : drogas) {
            droga.getTratamientos().remove(tratamiento);
            drogasService.saveDroga(droga);
        }

        tratamientosRepo.delete(tratamiento);
    }

    @Override
    public List<Tratamiento> getTratamientosByPetId(Long petId) {
        return tratamientosRepo.findByPetId(petId);
    }

}