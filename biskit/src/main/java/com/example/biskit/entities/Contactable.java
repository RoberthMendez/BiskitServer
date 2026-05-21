package com.example.biskit.entities;

public interface Contactable {
    Long getId();
    String getNombre();
    String getCorreo();
    Credenciales getCredenciales();
}