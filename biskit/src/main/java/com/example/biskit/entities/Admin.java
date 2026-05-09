package com.example.biskit.entities;

import com.example.biskit.util.NoNormalizar;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class Admin extends EntidadBase implements Contactable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100) 
  private String nombre;

  @Column(nullable = false, length = 20, unique = true)
  @NoNormalizar
  private String correo;

  @Column(nullable = false, length = 20, unique = true)
  private String cedula;

  @OneToOne
  @JoinColumn(name = "credenciales_id")
  private Credenciales credenciales;
  
}
