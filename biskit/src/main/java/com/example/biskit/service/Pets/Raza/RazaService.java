package com.example.biskit.service.Pets.Raza;

import java.util.Collection;
import com.example.biskit.entities.pets.Raza;

public interface RazaService {

    public Collection<Raza> getAllRazas();

    public Raza getRazaById(Long id);

    public Raza getRazaByNombre(String nombre);

    public void saveRaza(Raza raza);
}
