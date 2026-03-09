package com.example.biskit.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "owner")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Pet {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "nombre", nullable = false, length = 100)
  private String nombre;

  @Column(name = "especie")
  @Enumerated(EnumType.STRING)
  private Especie especie; // Perro o Gato

  @Column(name = "raza", length = 100)
  private String raza;

  @Column(name = "estado")
  @Enumerated(EnumType.STRING)
  private Estado estado; // Activo o Inactivo

  @Column(name = "edad", nullable = false)
  private int edad;
  @Column(name = "peso")
  private float peso;
  @Column(name = "enfermedad", length = 100)
  private String enfermedad;
  @Column(name = "url_foto", length = 255)
  private String URLFoto;

  @ManyToOne
  private Client owner;

}
