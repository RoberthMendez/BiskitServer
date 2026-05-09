package com.example.biskit.service.Credenciales;

public interface CorreosService {

    public void enviarBienvenida(String destinatario, String nombre,String usuario, String password);

    public String construirCuerpo(String nombre, String usuario, String password);
    
}
