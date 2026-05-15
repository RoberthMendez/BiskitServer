package com.example.biskit.service.Admin;

import com.example.biskit.entities.Admin;
import com.example.biskit.errors.AdminNotFoundException;
import com.example.biskit.repo.AdminRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AdminsImpl implements AdminsService {

  @Autowired
  private AdminRepo adminRepo;

  @Override
  public Admin findById(Long id) {
    return adminRepo.findById(id).orElseThrow(() -> new AdminNotFoundException(id));
  }

  @Override
  public Admin findByUsuario(String usuario) {
    return adminRepo
      .findAll()
      .stream()
      .filter(admin -> admin.getCredenciales().getUsername().equals(usuario))
      .findFirst()
      .orElse(null);
  }

  @Override
  public boolean autenticarAdmin(String usuario, String contrasena) {
    return adminRepo
      .findAll()
      .stream()
      .anyMatch(
        admin ->
          admin.getCredenciales().getUsername().equals(usuario) &&
          admin.getCredenciales().getPassword().equals(contrasena)
      );
  }
}
