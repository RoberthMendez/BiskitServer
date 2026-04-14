package com.example.biskit.service.Pets.Enfermedad;


import java.util.List;

import com.example.biskit.entities.pets.Enfermedad;

public interface EnfermedadService {

    public List<Enfermedad> getAllEnfermedades();

    public Enfermedad getEnfermedadById(Long id);

    public Enfermedad getEnfermedadByNombre(String nombre);

    public void saveEnfermedad(Enfermedad enfermedad);

}
