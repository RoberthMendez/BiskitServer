package com.example.biskit.errors;

public class VetNotFoundException extends RuntimeException {

    public VetNotFoundException(Long id) {
        super("No se encontró veterinario con id " + id);
    }
    
}
