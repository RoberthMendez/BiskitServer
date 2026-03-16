package com.example.biskit.service.Tratamientos;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import java.util.Collection;

import com.example.biskit.entities.Droga;
import com.example.biskit.repo.DrogasRepo;

import com.example.biskit.entities.Tratamiento;

@Service
public class DrogasImpl implements DrogasService {

    @Autowired
    private DrogasRepo drogasRepo;

    @Override
    public Collection<Droga> getDrogas() {
        return drogasRepo.findAll();
    }

    @Override
    public Droga getDrogaById(Long id) {
        return drogasRepo.findById(id).orElseThrow(() -> new RuntimeException("No se encontró droga con id: " + id));
    }

    @Override
    public void saveDroga(Droga droga) {
        drogasRepo.save(droga);
    }
}
