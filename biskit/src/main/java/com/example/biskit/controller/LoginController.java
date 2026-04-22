package com.example.biskit.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.biskit.entities.Admin;
import com.example.biskit.entities.Client;
import com.example.biskit.entities.Credenciales;
import com.example.biskit.entities.dtos.RespuestaCredencialDto;
import com.example.biskit.entities.vets.Vet;
import com.example.biskit.service.Clients.ClientsService;
import com.example.biskit.service.Credenciales.CredencialesService;
import com.example.biskit.service.Vets.VetService;
import com.example.biskit.service.Admin.AdminsService;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

    @Autowired
    private CredencialesService credencialesService;

    @Autowired
    private ClientsService clientsService;

    @Autowired
    private VetService vetService;

    @Autowired
    private AdminsService adminService;

    @PostMapping
    public ResponseEntity<RespuestaCredencialDto> login(@RequestBody Credenciales credenciales) {
        if (credenciales == null || credenciales.getUsuario() == null || credenciales.getPassword() == null
                || credenciales.getUsuario().isBlank() || credenciales.getPassword().isBlank()) {
            return ResponseEntity.badRequest().body(crearRespuesta(null, "CREDENCIALES_INVALIDAS"));
        }

        if (!credencialesService.existeUsuario(credenciales.getUsuario())) {
            return ResponseEntity.badRequest().body(crearRespuesta(null, "CREDENCIALES_INVALIDAS"));
        }

        if (clientsService.autenticarClient(credenciales.getUsuario(), credenciales.getPassword())) {
            Client client = clientsService.findByUsuario(credenciales.getUsuario());
            return ResponseEntity.ok(crearRespuesta(client.getId(), "CLIENTE"));
        }

        if (vetService.autenticarVet(credenciales.getUsuario(), credenciales.getPassword())) {
            Vet vet = vetService.findByUsuario(credenciales.getUsuario());
            return ResponseEntity.ok(crearRespuesta(vet.getId(), "VETERINARIO"));
        }

        if (adminService.autenticarAdmin(credenciales.getUsuario(), credenciales.getPassword())) {
            Admin admin = adminService.findByUsuario(credenciales.getUsuario());
            return ResponseEntity.ok(crearRespuesta(admin.getId(), "ADMIN"));
        }

        return ResponseEntity.badRequest().body(crearRespuesta(null, "CREDENCIALES_INVALIDAS"));
    }

    private RespuestaCredencialDto crearRespuesta(Long id, String tipo) {
        return RespuestaCredencialDto.builder()
                .id(id)
                .tipo(tipo)
                .build();
    }
}
