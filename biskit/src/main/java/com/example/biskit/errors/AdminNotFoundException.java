package com.example.biskit.errors;

public class AdminNotFoundException extends RuntimeException {

    public AdminNotFoundException(Long id) {
        super("No se encontró el administrador con id " + id);
    }
    
}
