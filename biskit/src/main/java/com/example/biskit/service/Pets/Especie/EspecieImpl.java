package com.example.biskit.service.Pets.Especie;

import com.example.biskit.entities.pets.Especie;
import com.example.biskit.repo.pets.EspecieRepo;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class EspecieImpl implements EspecieService {

  @Autowired
  private EspecieRepo especieRepo;

  @Override
  public List<Especie> getAllEspecies() {
    return especieRepo.findAll();
  }

  @Override
  public Especie getEspecieById(Long id) {
    return especieRepo.findById(id).orElse(null);
  }

  @Override
  public Especie getEspecieByNombre(String nombre) {
    return especieRepo.findByNombre(nombre);
  }
}
