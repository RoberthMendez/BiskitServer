package com.example.biskit.service.Admin;

import com.example.biskit.entities.Admin;

public interface AdminsService {

    public Admin findById(Long id);

    public Admin findByUsuario(String usuario);

    public boolean autenticarAdmin(String usuario, String contrasena);

}
