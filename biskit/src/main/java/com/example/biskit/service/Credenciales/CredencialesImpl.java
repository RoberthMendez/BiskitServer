package com.example.biskit.service.Credenciales;

import com.example.biskit.entities.Contactable;
import com.example.biskit.entities.Credenciales;
import com.example.biskit.repo.CredencialesRepo;
import com.example.biskit.service.Admin.AdminsService;
import com.example.biskit.service.Clients.ClientsService;
import com.example.biskit.service.Vets.VetService;
import jakarta.transaction.Transactional;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CredencialesImpl implements CredencialesService {

  @Autowired
  private CredencialesRepo credencialesRepo;

  @Autowired
  private CorreosService correosService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  @Lazy
  private ClientsService clientsService;

  @Autowired
  @Lazy
  private VetService vetService;

  @Autowired
  @Lazy
  private AdminsService adminService;

  @Override
  public Collection<Credenciales> getCredenciales() {
    return credencialesRepo.findAll();
  }

  @Override
  public Credenciales getCredencialesById(Long id) {
    return credencialesRepo
      .findById(id)
      .orElseThrow(() -> new RuntimeException("No se encontraron credenciales con id: " + id));
  }

  @Override
  public void addCredenciales(Credenciales credenciales) {
    credencialesRepo.save(credenciales);
  }

  @Override
  @Transactional
  public void deleteCredenciales(Long id) {
    credencialesRepo.deleteById(id);
  }

  @Override
  public boolean existeUsuario(String usuario) {
    return credencialesRepo.existsByUsername(usuario);
  }

  @Override
  public void updatePassword(Long idUsuario, Credenciales credenciales) {
    Credenciales credencialesExistente = credencialesRepo
      .findByUsername(credenciales.getUsername())
      .orElseThrow(() ->
        new RuntimeException(
          "No se encontraron credenciales con usuario: " + credenciales.getUsername()
        )
      );

    credencialesExistente.setPassword(passwordEncoder.encode(credenciales.getPassword()));
    credencialesRepo.save(credencialesExistente);
  }

  @Override
  public void enviarCorreoResetPassword(String correo) {
    Contactable contactable = null;

    if (clientsService.findByUsuario(correo) != null) contactable = clientsService.findByUsuario(
      correo
    );
    else if (vetService.findByUsuario(correo) != null) contactable = vetService.findByUsuario(
      correo
    );
    else if (adminService.findByUsuario(correo) != null) contactable = adminService.findByUsuario(
      correo
    );

    if (contactable == null) throw new RuntimeException(
      "No se encontró un usuario con ese correo."
    );

    correosService.enviarCorreoResetPassword(contactable);
  }
}
