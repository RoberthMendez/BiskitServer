package com.example.biskit.service.Tratamientos;

import com.example.biskit.entities.Droga;

import java.util.List;

public interface DrogasService {

    public List<Droga> getDrogas();

    public Droga getDrogaById(Long id);

    public void saveDroga(Droga droga);

}
