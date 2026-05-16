package com.example.biskit.service.Pets.Especie;

import com.example.biskit.entities.Pets.Especie;
import java.util.List;

public interface EspecieService {
  public List<Especie> getAllEspecies();

  public Especie getEspecieById(Long id);

  public Especie getEspecieByNombre(String nombre);
}
