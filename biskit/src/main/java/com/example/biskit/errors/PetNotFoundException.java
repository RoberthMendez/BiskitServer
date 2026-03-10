package com.example.biskit.errors;

public class PetNotFoundException extends RuntimeException {

    public PetNotFoundException(Long id) {
        super("No se encontró la mascota con id " + id);
    }

}
