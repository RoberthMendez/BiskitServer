package com.example.biskit.service.Vets;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.biskit.entities.vets.Especialidad;
import com.example.biskit.repo.vets.EspecialidadRepo;

@Service
public class EspecialidadesImpl implements EspecialidadesService {

    @Autowired
    private EspecialidadRepo especialidadesRepo;

    @Override
    public List<Especialidad> getEspecialidades() {
        return especialidadesRepo.findAll();
    }

    @Override
    public Especialidad getEspecialidadById(Long id) {
        return especialidadesRepo.findById(id).orElseThrow(() -> new RuntimeException("No se encontró la especialidad con id: " + id));
    }

    @Override
    public Especialidad getEspecialidadByNombre(String nombre) {
        return especialidadesRepo.findByNombreIgnoreCase(nombre).orElse(null);
    }

    @Override
    public void addEspecialidad(Especialidad especialidad) {
        especialidadesRepo.save(especialidad);
    }

}
