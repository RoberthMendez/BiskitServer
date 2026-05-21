package com.example.biskit.entities.DTOs.Droga;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DrogaDTO {

  private Long id;
  private String nombre;
  private int unidadesDisponibles;
}
