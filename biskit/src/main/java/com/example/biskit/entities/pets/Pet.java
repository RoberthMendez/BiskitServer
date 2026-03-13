package com.example.biskit.entities.pets;

import java.sql.Date;

import com.example.biskit.entities.Client;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

  @Column(name = "estado")
  private boolean estado;
  @Column(name = "fecha_nacimiento", nullable = false)
  private Date fechaNacimiento;
  @Column(name = "peso")
  private float peso;
  @Column(name = "url_foto", length = 255)
  private String URLFoto;

  @ManyToOne
  @JoinColumn(name = "enfermedad_id")
  private Enfermedad enfermedad;

  @ManyToOne
  @JoinColumn(name = "especie_id")
  private Especie especie;

  @ManyToOne
  private Client owner;

  @ManyToOne
  @JoinColumn(name = "raza_id")
  private Raza raza;

  public int getEdad() {
    Date fechaActual = new Date(System.currentTimeMillis());
    long edadEnMilisegundos = fechaActual.getTime() - this.fechaNacimiento.getTime();
    int edadEnAnios = (int) (edadEnMilisegundos / (1000L * 60 * 60 * 24 * 365));
    return edadEnAnios;
  }

}
