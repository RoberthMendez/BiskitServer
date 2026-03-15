package com.example.biskit.service.Tratamientos;

import java.util.List;

import com.example.biskit.entities.Tratamiento;

public interface TratamientosService {

    public Tratamiento getTratamientoById(Long id);
    public void addTratamiento(Tratamiento tratamiento, List<Long> drogasIds);
    
}
