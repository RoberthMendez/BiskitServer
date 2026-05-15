package com.example.biskit.service.Pets.Enfermedad;

import com.example.biskit.entities.pets.Enfermedad;
import com.example.biskit.errors.EnfermedadYaExisteException;
import com.example.biskit.repo.pets.EnfermedadRepo;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class EnfermedadImpl implements EnfermedadService {

  @Autowired
  private EnfermedadRepo enfermedadRepo;

  @Override
  public List<Enfermedad> getAllEnfermedades() {
    return enfermedadRepo.findAll();
  }

  @Override
  public Enfermedad getEnfermedadById(Long id) {
    return enfermedadRepo.findById(id).orElse(null);
  }

  @Override
  public Optional<Enfermedad> getEnfermedadByNombre(String nombre) {
    return enfermedadRepo.findByNombreIgnoreCase(nombre);
  }

  @Override
  public Enfermedad saveEnfermedad(Enfermedad enfermedad) {
    Optional<Enfermedad> enfermedadExistente = getEnfermedadByNombre(enfermedad.getNombre());
    if (
      enfermedadExistente.isPresent() &&
      (enfermedad.getId() == null || !enfermedadExistente.get().getId().equals(enfermedad.getId()))
    ) {
      throw new EnfermedadYaExisteException(
        "La enfermedad '" + enfermedad.getNombre() + "' ya existe."
      );
    }
    return enfermedadRepo.save(enfermedad);
  }
}
