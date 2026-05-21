package com.example.biskit.service.Pets.Raza;

import com.example.biskit.entities.Pets.Raza;
import com.example.biskit.errors.YaExiste.RazaYaExisteException;
import com.example.biskit.repo.pets.RazaRepo;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RazaImpl implements RazaService {

  @Autowired
  private RazaRepo razaRepo;

  @Override
  public List<Raza> getAllRazas() {
    return razaRepo.findAll();
  }

  @Override
  public Raza getRazaById(Long id) {
    return razaRepo.findById(id).orElse(null);
  }

  @Override
  public Optional<Raza> getRazaByNombre(String nombre) {
    return razaRepo.findByNombreIgnoreCase(nombre);
  }

  @Override
  public Raza saveRaza(Raza raza) {
    Optional<Raza> razaExistente = getRazaByNombre(raza.getNombre());
    if (
      razaExistente.isPresent() &&
      (raza.getId() == null || !razaExistente.get().getId().equals(raza.getId()))
    ) {
      throw new RazaYaExisteException("Ya existe la raza " + raza.getNombre());
    }

    return razaRepo.save(raza);
  }
}
