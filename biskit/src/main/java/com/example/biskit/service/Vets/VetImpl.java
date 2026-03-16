package com.example.biskit.service.Vets;

import com.example.biskit.entities.vets.Vet;
import com.example.biskit.repo.vets.VetsRepo;

import org.springframework.stereotype.Service;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class VetImpl implements VetService {

    @Autowired
    private VetsRepo vetsRepo;

    @Override
    public Collection<Vet> getVets() {
        return vetsRepo.findAll();
    }

    @Override
    public Vet getVetById(Long id) {
        return vetsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró veterinario con id: " + id));
    }

    @Override
    public void saveVet(Vet vet) {
        vetsRepo.save(vet);
    }
}
