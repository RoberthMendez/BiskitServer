package com.example.biskit.service.Tratamientos;

import java.util.List;

import com.example.biskit.entities.Tratamiento;
import com.example.biskit.entities.dtos.TratamientoDto;

public interface TratamientosService {

    public Tratamiento getTratamientoById(Long id);

    public void addTratamiento(TratamientoDto tratamientoDto);

    public void updateTratamiento(Tratamiento tratamiento);

    public void deleteTratamiento(Long id);

    public List<Tratamiento> getTratamientosByPetId(Long petId);

}
