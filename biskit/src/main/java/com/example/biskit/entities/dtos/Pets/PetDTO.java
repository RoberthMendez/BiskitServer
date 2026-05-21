package com.example.biskit.entities.DTOs.Pets;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetDTO {

  private Long id;
  private String nombre;
  private boolean estado;
  private int edad;
  private float peso;
  private String urlFoto;
  private String enfermedad;
  private String owner;
  private String raza;
  private String especie;
}
