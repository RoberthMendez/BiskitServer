package com.example.biskit.service.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.biskit.entities.Admin;
import com.example.biskit.repo.AdminRepo;

@Service
public class AdminsImpl implements AdminsService {
    @Autowired
    private AdminRepo adminRepo;


    public Admin findByUsuario(String usuario) {
        return adminRepo.findAll().stream()
        .filter(admin -> admin.getCredenciales().getUsuario().equals(usuario))
        .findFirst()
        .orElse(null);
    }

    public boolean autenticarAdmin(String usuario, String contrasena) {
        return adminRepo.findAll().stream()
        .anyMatch(admin -> admin.getCredenciales().getUsuario().equals(usuario)
            && admin.getCredenciales().getPassword().equals(contrasena));
    }
}
