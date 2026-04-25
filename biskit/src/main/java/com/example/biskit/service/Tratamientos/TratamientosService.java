package com.example.biskit.service.Tratamientos;

import java.util.List;

import com.example.biskit.entities.dtos.DrogaTratamientoCountDto;
import com.example.biskit.entities.dtos.TratamientoDto;
import com.example.biskit.entities.Tratamiento;
import com.example.biskit.entities.dtos.TratamientosMesDto;

public interface TratamientosService {

    public Tratamiento getTratamientoById(Long id);

    public void addTratamiento(TratamientoDto tratamientoDto);

    public void addTratamiento(Tratamiento tratamiento);

    public void updateTratamiento(Long id, TratamientoDto tratamientoDto);

    public void deleteTratamiento(Long id);

    public List<Tratamiento> getTratamientosByPetId(Long petId);

    public List<TratamientosMesDto> getNumTratamientos6Meses();

    public List<DrogaTratamientoCountDto> getDrogaTratamientosMesCount();

    public List<Tratamiento> getTratamientosByVetId(Long vetId);

}
