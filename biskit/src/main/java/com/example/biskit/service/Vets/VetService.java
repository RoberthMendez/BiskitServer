package com.example.biskit.service.Vets;

import com.example.biskit.entities.vets.Vet;
import java.util.Collection;

public interface VetService {

    public Collection<Vet> getVets();

    public Vet getVetById(Long id);

    public void addVet(Vet vet);

}
