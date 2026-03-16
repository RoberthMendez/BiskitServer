package com.example.biskit.service.Tratamientos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.biskit.entities.Droga;
import com.example.biskit.entities.Tratamiento;
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
    public void addTratamiento(Tratamiento tratamiento, List<Long> drogasIds) {

        Vet vet = vetService.getVetById(tratamiento.getVet().getId());
        Pet pet = petsService.getPetById(tratamiento.getPet().getId());
        tratamiento.setPet(pet);
        tratamiento.setVet(vet);

        for (Long drogaId : drogasIds) {
            tratamiento.getDrogas().add(drogasService.getDrogaById(drogaId));
        }

        tratamientosRepo.save(tratamiento);

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

}
