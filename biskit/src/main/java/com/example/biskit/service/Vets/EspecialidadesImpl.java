package com.example.biskit.service.Vets;

import com.example.biskit.entities.vets.Especialidad;
import com.example.biskit.errors.DrogaYaExisteException;
import com.example.biskit.repo.vets.EspecialidadRepo;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class EspecialidadesImpl implements EspecialidadesService {

  @Autowired
  private EspecialidadRepo especialidadesRepo;

  @Override
  public List<Especialidad> getEspecialidades() {
    return especialidadesRepo.findAll();
  }

  @Override
  public Especialidad getEspecialidadById(Long id) {
    return especialidadesRepo
      .findById(id)
      .orElseThrow(() -> new RuntimeException("No se encontró la especialidad con id: " + id));
  }

  @Override
  public Optional<Especialidad> getEspecialidadByNombre(String nombre) {
    return especialidadesRepo.findByNombreIgnoreCase(nombre);
  }

  @Override
  public Especialidad addEspecialidad(Especialidad especialidad) {
    Optional<Especialidad> especialidadExistente = getEspecialidadByNombre(
      especialidad.getNombre()
    );
    if (
      especialidadExistente.isPresent() &&
      (especialidad.getId() == null ||
        !especialidadExistente.get().getId().equals(especialidad.getId()))
    ) {
      throw new DrogaYaExisteException(
        "La especialidad " + especialidad.getNombre() + " ya existe."
      );
    }

    return especialidadesRepo.save(especialidad);
  }
}
