package com.example.biskit.service.Pets.Enfermedad;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.biskit.entities.pets.Enfermedad;
import com.example.biskit.repo.pets.EnfermedadRepo;

@Service
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
    public Enfermedad getEnfermedadByNombre(String nombre) {
        return enfermedadRepo.findByNombreIgnoreCase(nombre);
    }

    @Override
    public void saveEnfermedad(Enfermedad enfermedad) {
        enfermedadRepo.save(enfermedad);
    }

}
