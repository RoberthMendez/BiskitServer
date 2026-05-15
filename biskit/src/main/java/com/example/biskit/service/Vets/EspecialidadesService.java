package com.example.biskit.service.Vets;

import com.example.biskit.entities.vets.Especialidad;
import java.util.List;
import java.util.Optional;

public interface EspecialidadesService {
  public List<Especialidad> getEspecialidades();

  public Especialidad getEspecialidadById(Long id);

  public Optional<Especialidad> getEspecialidadByNombre(String nombre);

  public Especialidad addEspecialidad(Especialidad especialidad);
}
