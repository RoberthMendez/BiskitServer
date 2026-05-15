package com.example.biskit.controller;

import com.example.biskit.entities.Admin;
import com.example.biskit.entities.Client;
import com.example.biskit.entities.Credenciales;
import com.example.biskit.entities.dtos.LoginDto;
import com.example.biskit.entities.dtos.RespuestaCredencialDto;
import com.example.biskit.entities.vets.Vet;
import com.example.biskit.security.JWTGenerator;
import com.example.biskit.service.Admin.AdminsService;
import com.example.biskit.service.Clients.ClientsService;
import com.example.biskit.service.Credenciales.CredencialesService;
import com.example.biskit.service.Vets.VetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  JWTGenerator jwtGenerator;

  @PostMapping
  public ResponseEntity<RespuestaCredencialDto> login(@RequestBody Credenciales credenciales) {
    System.out.println("----------------- INTENTO LOGIN ------------------ " + credenciales);
    if (
      credenciales == null ||
      credenciales.getUsername() == null ||
      credenciales.getPassword() == null ||
      credenciales.getUsername().isBlank() ||
      credenciales.getPassword().isBlank()
    ) {
      return ResponseEntity.badRequest().body(crearRespuesta(null, "CREDENCIALES_INVALIDAS"));
    }

    if (!credencialesService.existeUsuario(credenciales.getUsername())) {
      return ResponseEntity.badRequest().body(crearRespuesta(null, "CREDENCIALES_INVALIDAS"));
    }

    if (clientsService.autenticarClient(credenciales.getUsername(), credenciales.getPassword())) {
      Client client = clientsService.findByUsuario(credenciales.getUsername());
      return ResponseEntity.ok(crearRespuesta(client.getId(), "CLIENTE"));
    }

    if (vetService.autenticarVet(credenciales.getUsername(), credenciales.getPassword())) {
      Vet vet = vetService.findByUsuario(credenciales.getUsername());
      if (vet.isEstado() == false) {
        return ResponseEntity.badRequest().body(crearRespuesta(null, "VETERINARIO_INACTIVO"));
      }
      return ResponseEntity.ok(crearRespuesta(vet.getId(), "VETERINARIO"));
    }

    if (adminService.autenticarAdmin(credenciales.getUsername(), credenciales.getPassword())) {
      Admin admin = adminService.findByUsuario(credenciales.getUsername());
      return ResponseEntity.ok(crearRespuesta(admin.getId(), "ADMIN"));
    }

    return ResponseEntity.badRequest().body(crearRespuesta(null, "CREDENCIALES_INVALIDAS"));
  }

  // ----- Login -----
  @PostMapping("/nuevo")
  public ResponseEntity<?> loginJwt(@RequestBody Credenciales credenciales) {
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        credenciales.getUsername(),
        credenciales.getPassword()
      )
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);

    String rol = authentication.getAuthorities().iterator().next().getAuthority();

    String token = jwtGenerator.generateToken(authentication);

    return ResponseEntity.ok(new LoginDto(token, rol));
  }

  private RespuestaCredencialDto crearRespuesta(Long id, String tipo) {
    return RespuestaCredencialDto.builder().id(id).tipo(tipo).build();
  }

  // ----- Restablecimiento de Contraseña -----
  @PutMapping("/{idUsuario}/reset-password")
  public void cambiarPassword(
    @PathVariable Long idUsuario,
    @RequestBody Credenciales credenciales
  ) {
    credencialesService.updatePassword(idUsuario, credenciales);
  }

  // ----- Enviar Correo para Cambiar Contraseña -----
  @PostMapping("/forgot-password")
  public void correoResetPassword(@RequestBody String correo) {
    credencialesService.enviarCorreoResetPassword(correo);
  }
}
