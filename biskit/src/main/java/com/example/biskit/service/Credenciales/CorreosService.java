package com.example.biskit.service.Credenciales;

import com.example.biskit.entities.Citas.Cita;
import com.example.biskit.entities.Client;
import com.example.biskit.entities.Contactable;

public interface CorreosService {

    public void enviarBienvenida(Client cliente);

    public String construirCuerpo(String nombre, String usuario, String password, String linkResetPassword);
    
    public void enviarCorreoResetPassword(Contactable contactable);

    public String construirCuerpoResetPassword(String nombre, String linkResetPassword);

    public void enviarConfirmacionCita(Cita cita, Client owner);

}
