package com.example.biskit.service.Pets.Raza;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import com.example.biskit.entities.pets.Raza;
import com.example.biskit.repo.pets.RazaRepo;

@Service
public class RazaImpl implements RazaService {

    @Autowired
    private RazaRepo razaRepo;

    @Override
    public Collection<Raza> getAllRazas() {
        return razaRepo.findAll();
    }

    @Override
    public Raza getRazaById(Long id) {
        return razaRepo.findById(id).orElse(null);
    }

    @Override
    public Raza getRazaByNombre(String nombre) {
        return razaRepo.findByNombreIgnoreCase(nombre);
    }

    @Override
    public void saveRaza(Raza raza) {
        razaRepo.save(raza);
    }

}
