package com.example.biskit.security;

import com.example.biskit.entities.Admin;
import com.example.biskit.entities.Client;
import com.example.biskit.entities.Credenciales;
import com.example.biskit.entities.Rol;
import com.example.biskit.entities.Vets.Vet;
import com.example.biskit.repo.CredencialesRepo;
import com.example.biskit.repo.RolRepo;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

  @Autowired
  private CredencialesRepo credencialesRepo;

  @Autowired
  RolRepo rolRepo;

  @Autowired
  private PasswordEncoder passwordEncoder;

  /*Unico método para traer la informacion de un usuario a traves de su username */
  /*Retorna un USerDetails, que es la entidad básica en springboot que unicamente tiene
   * username, password y authorities
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Credenciales userCredenciales = credencialesRepo
      .findByUsername(username)
      .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    UserDetails userDetails = new User(
      userCredenciales.getUsername(),
      userCredenciales.getPassword(),
      mapRolToAuthorities(userCredenciales.getRol())
    );

    return userDetails;
  }

  private Collection<GrantedAuthority> mapRolToAuthorities(Rol rol) {
    return List.of(new SimpleGrantedAuthority(rol.getNombre()));
  }

  public Credenciales vetToCredenciales(Vet vet) {
    return Credenciales.builder()
      .username(vet.getCorreo())
      .password(crearContraseña(vet.getNombre(), vet.getCedula()))
      .rol(rolRepo.findByNombre("VET"))
      .build();
  }

  public Credenciales clientToCredenciales(Client client) {
    return Credenciales.builder()
      .username(client.getCorreo())
      .password(crearContraseña(client.getNombre(), client.getCedula()))
      .rol(rolRepo.findByNombre("CLIENT"))
      .build();
  }

  public Credenciales adminToCredenciales(Admin admin) {
    return Credenciales.builder()
      .username(admin.getCorreo())
      .password(crearContraseña(admin.getNombre(), admin.getCedula()))
      .rol(rolRepo.findByNombre("ADMIN"))
      .build();
  }

  private String crearContraseña(String nombre, String cedula) {
    String parteNombre =
      nombre.length() >= 4 ? nombre.substring(0, 3).toLowerCase() : nombre.toLowerCase();
    String parteCedula = cedula.length() >= 3 ? cedula.substring(0, 3) : cedula;

    return passwordEncoder.encode(parteNombre + parteCedula);
  }
}
