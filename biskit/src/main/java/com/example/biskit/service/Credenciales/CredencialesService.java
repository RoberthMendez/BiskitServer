package com.example.biskit.service.Credenciales;

import com.example.biskit.entities.Credenciales;

public interface CredencialesService {
    public boolean existeUsuario(String usuario);

    public void addCredenciales(Credenciales credenciales);
}
