package com.example.biskit.service.Credenciales;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.biskit.entities.Credenciales;
import com.example.biskit.repo.CredencialesRepo;

import jakarta.transaction.Transactional;

@Service
public class CredencialesImpl implements CredencialesService {

    @Autowired
    private CredencialesRepo credencialesRepo;

    @Override
    public Collection<Credenciales> getCredenciales() {
        return credencialesRepo.findAll();
    }

    @Override
    public Credenciales getCredencialesById(Long id) {
        return credencialesRepo.findById(id).orElseThrow(() -> new RuntimeException("No se encontraron credenciales con id: " + id));
    }

    @Override
    public void addCredenciales(Credenciales credenciales) {
        credencialesRepo.save(credenciales);
    }

    @Override
    @Transactional
    public void deleteCredenciales(Long id) {
        credencialesRepo.deleteById(id);
    }  

    @Override
    public boolean existeUsuario(String usuario) {

        return credencialesRepo.existsByUsuario(usuario);
    }
    
}
