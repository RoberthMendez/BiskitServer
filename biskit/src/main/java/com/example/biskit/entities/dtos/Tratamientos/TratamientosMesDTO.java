package com.example.biskit.entities.DTOs.Tratamientos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TratamientosMesDTO {

  private String mes;
  private Long numTratamientos;
}
