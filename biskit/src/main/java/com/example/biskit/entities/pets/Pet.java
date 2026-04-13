package com.example.biskit.entities.pets;

import java.sql.Date;
import java.util.List;

import com.example.biskit.entities.Client;
import com.example.biskit.entities.Tratamiento;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

  @Column(name = "estado")
  private boolean estado;

  @Column(name = "fecha_nacimiento", nullable = false)
  private Date fechaNacimiento;

  @Column(name = "peso")
  private float peso;
  
  @Column(name = "url_foto", length = 255)
  private String urlFoto;

  @ManyToOne
  @JoinColumn(name = "enfermedad_id")
  private Enfermedad enfermedad;

  
  @ManyToOne
  private Client owner;

  @ManyToOne
  @JoinColumn(name = "raza_id")
  private Raza raza;

  @JsonIgnore
  @OneToMany(mappedBy = "pet")
  private List<Tratamiento> tratamientos;

}
