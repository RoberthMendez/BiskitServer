package com.example.biskit.service.Pets.Raza;

import com.example.biskit.entities.pets.Raza;
import java.util.List;
import java.util.Optional;

public interface RazaService {
  public List<Raza> getAllRazas();

  public Raza getRazaById(Long id);

  public Optional<Raza> getRazaByNombre(String nombre);

  public Raza saveRaza(Raza raza);
}
