package com.example.biskit.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.example.biskit.entities.pets.Pet;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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

  @Column(name = "nombre", nullable = false, length = 100)
  private String nombre;

  @Column(name = "cedula", nullable = false, unique = true, length = 20)
  private String cedula;

  @Column(name = "correo", nullable = false, unique = true, length = 100)
  private String correo;

  @Column(name = "celular", nullable = false, length = 20)
  private String celular;
  
  @OneToOne
  @JoinColumn(name = "credenciales_id")
  private Credenciales credenciales;

  @JsonIgnore
  @OneToMany(mappedBy = "owner")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private List<Pet> pets;
  
}
