package com.example.biskit.entities.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetsFiltrosDTO {

  private Boolean estado;
  private String especie;
  private String raza;
  private Integer edad;
  private Float peso;
  private String enfermedad;
  private Integer tratamientos;
  private Long vetId;
  private Boolean misMascotas;
}
