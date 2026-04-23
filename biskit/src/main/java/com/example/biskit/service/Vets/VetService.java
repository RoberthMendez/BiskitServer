package com.example.biskit.service.Vets;

import com.example.biskit.entities.vets.Vet;
import java.util.List;

public interface VetService {

    public List<Vet> getVets();

    public Vet getVetById(Long id);

    public void addVet(Vet vet);

    public void saveVet(Vet vet);

    public boolean autenticarVet(String usuario, String contrasena);

    public Vet findByUsuario(String usuario);

    public Long getVetsInactivosCount();

}
