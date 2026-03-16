package com.example.biskit.service.Credenciales;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.biskit.repo.CredencialesRepo;
import com.example.biskit.entities.Credenciales;

import org.springframework.stereotype.Service;

@Service

public class CredencialesImpl implements CredencialesService {
    @Autowired
    private CredencialesRepo credencialesRepo;

    @Override
    public boolean existeUsuario(String usuario) {

        return credencialesRepo.existsByUsuario(usuario);
    }

    @Override
    public void addCredenciales(Credenciales credenciales) {
        credencialesRepo.save(credenciales);
    }

}
