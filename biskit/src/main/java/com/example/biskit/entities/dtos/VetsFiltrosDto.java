package com.example.biskit.entities.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VetsFiltrosDTO {

  private Boolean estado;
  private String especialidad;
  private Integer tratamientos;
  private String pet;
  private Long vetId;
}
