package com.example.biskit.service.Tratamientos;

import java.util.List;

import com.example.biskit.entities.dtos.TratamientoDto;
import com.example.biskit.entities.Tratamiento;

public interface TratamientosService {

    public Tratamiento getTratamientoById(Long id);

    public void addTratamiento(TratamientoDto tratamientoDto);

    public void updateTratamiento(Long id, TratamientoDto tratamientoDto);

    public void deleteTratamiento(Long id);

    public List<Tratamiento> getTratamientosByPetId(Long petId);

    public Long getUltimosTratamientosCount();

    public Long getTratamientosMedicamentoCount(long medicamentoId);

}
