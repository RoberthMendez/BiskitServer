package com.example.biskit.service.Vets;

import com.example.biskit.entities.vets.Especialidad;
import java.util.Collection;

public interface EspecialidadesService {

    public Collection<Especialidad> getEspecialidades();

    public Especialidad getEspecialidadById(Long id);

    public Especialidad getEspecialidadByNombre(String nombre);

    public void addEspecialidad(Especialidad especialidad);
    
}
