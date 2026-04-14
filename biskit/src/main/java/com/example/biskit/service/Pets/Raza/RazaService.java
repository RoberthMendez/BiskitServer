package com.example.biskit.service.Pets.Raza;

import java.util.List;

import com.example.biskit.entities.pets.Raza;

public interface RazaService {

    public List<Raza> getAllRazas();

    public Raza getRazaById(Long id);

    public Raza getRazaByNombre(String nombre);

    public void saveRaza(Raza raza);
}
