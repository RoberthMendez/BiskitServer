package com.example.biskit.service.Credenciales;

import java.util.Collection;
import com.example.biskit.entities.Credenciales;

public interface CredencialesService {

    public Collection<Credenciales> getCredenciales();

    public Credenciales getCredencialesById(Long id);

    public void addCredenciales(Credenciales credenciales);

    public void deleteCredenciales(Long id);
    
}
