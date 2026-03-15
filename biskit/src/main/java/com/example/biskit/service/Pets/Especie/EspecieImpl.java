package com.example.biskit.service.Pets.Especie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.biskit.repo.pets.EspecieRepo;
import com.example.biskit.entities.pets.Especie;
import java.util.Collection;

@Service
public class EspecieImpl implements EspecieService {

    @Autowired
    private EspecieRepo especieRepo;

    @Override
    public Collection<Especie> getAllEspecies() {
        return especieRepo.findAll();
    }

    @Override
    public Especie getEspecieById(Long id) {
        return especieRepo.findById(id).orElse(null);
    }

}
