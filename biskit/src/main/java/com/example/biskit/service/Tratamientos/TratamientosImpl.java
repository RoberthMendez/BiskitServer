package com.example.biskit.service.Tratamientos;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.biskit.entities.Droga;
import com.example.biskit.entities.Tratamiento;
import com.example.biskit.entities.dtos.DrogaTratamientoCount;
import com.example.biskit.entities.dtos.TratamientoDto;
import com.example.biskit.entities.vets.Vet;
import com.example.biskit.entities.pets.Pet;
import com.example.biskit.errors.MascotaInactivaException;
import com.example.biskit.errors.StockInsuficienteException;
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
    @Transactional
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

        if (!pet.isEstado()) {
            throw new MascotaInactivaException("La mascota está inactiva");
        }

        List<Droga> drogasPersistidas = new ArrayList<>();
        if (tratamientoDto.getDrogasIds() != null) {
            for (Long drogaId : tratamientoDto.getDrogasIds()) {
                if (drogaId != null) {

                    Droga droga = drogasService.getDrogaById(drogaId);
                    if (droga.getUnidadesDisponibles() <= 0) {
                        throw new StockInsuficienteException("No hay suficientes drogas para crear el tratamiento");
                    }

                    droga.setUnidadesDisponibles(droga.getUnidadesDisponibles() - 1);
                    droga.setUnidadesVendidas(droga.getUnidadesVendidas() + 1);
                    drogasService.saveDroga(droga);
                    drogasPersistidas.add(droga);
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
    public void addTratamiento(Tratamiento tratamiento) {

        List<Droga> drogasPersistidas = new ArrayList<>();
        if (tratamiento.getDrogas() != null) {
            for (Droga droga : tratamiento.getDrogas()) {
                if (droga != null) {

                    if (droga.getUnidadesDisponibles() <= 0) {
                        throw new StockInsuficienteException("No hay suficientes drogas para crear el tratamiento");
                    }

                    droga.setUnidadesDisponibles(droga.getUnidadesDisponibles() - 1);
                    droga.setUnidadesVendidas(droga.getUnidadesVendidas() + 1);
                    drogasService.saveDroga(droga);
                    drogasPersistidas.add(droga);
                }
            }
        }
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

    @Override
    public Long getUltimosTratamientosCount() {
        LocalDate seisMesesAtras = LocalDate.now().minusMonths(6);
        return tratamientosRepo.getUltimosTratamientosCount(seisMesesAtras);
    }

    @Override
    public List<DrogaTratamientoCount> getDrogaTratamientosMesCount() {
        LocalDate treintaDiasAtras = LocalDate.now().minusDays(30);
        List<Droga> drogasUltimoMes = tratamientosRepo.getDrogasDesde(treintaDiasAtras);
        List<DrogaTratamientoCount> drogaTratamientoCounts = new ArrayList<>();
        for (Droga droga : drogasUltimoMes) {
            Long count = tratamientosRepo.getNumTratamientosDrogaDesde(droga.getId(), treintaDiasAtras);
            drogaTratamientoCounts.add(new DrogaTratamientoCount(droga.getNombre(), count));
        }
        return drogaTratamientoCounts;
    }

}