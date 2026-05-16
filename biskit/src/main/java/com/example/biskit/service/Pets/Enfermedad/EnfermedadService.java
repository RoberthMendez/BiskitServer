package com.example.biskit.service.Pets.Enfermedad;

import com.example.biskit.entities.Pets.Enfermedad;
import java.util.List;
import java.util.Optional;

public interface EnfermedadService {
  public List<Enfermedad> getAllEnfermedades();

  public Enfermedad getEnfermedadById(Long id);

  public Optional<Enfermedad> getEnfermedadByNombre(String nombre);

  public Enfermedad saveEnfermedad(Enfermedad enfermedad);
}
