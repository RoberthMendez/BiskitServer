package com.example.biskit.service.Tratamientos;

import com.example.biskit.entities.Droga;
import com.example.biskit.entities.Tratamiento;

import java.util.Collection;

public interface DrogasService {

    public Collection<Droga> getDrogas();

    public Droga getDrogaById(Long id);

    public void saveDroga(Droga droga);

}
