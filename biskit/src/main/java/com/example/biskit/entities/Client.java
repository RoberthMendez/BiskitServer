package com.example.biskit.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;

@Getter
@Setter
@ToString(exclude = "pets")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Client {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "nombre", nullable = false)
  private String nombre;
  @Column(name = "cedula", nullable = false, unique = true)
  private String cedula;
  @Column(name = "correo")
  private String correo;
  @Column(name = "celular")
  private String celular;
  @Column(name = "usuario", unique = true)
  private String usuario;
  @Column(name = "password")
  private String password;

  @OneToMany(mappedBy = "owner")
  private List<Pet> pets;
}
