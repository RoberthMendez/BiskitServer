package com.example.biskit.service.Pets.Enfermedad;

import java.util.Collection;
import com.example.biskit.entities.pets.Enfermedad;

public interface EnfermedadService {

    public Collection<Enfermedad> getAllEnfermedades();

    public Enfermedad getEnfermedadById(Long id);

    public Enfermedad getEnfermedadByNombre(String nombre);

    public void saveEnfermedad(Enfermedad enfermedad);

}
