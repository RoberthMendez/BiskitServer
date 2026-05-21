package com.example.biskit.entities.DTOs.KPIs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopDTO {

  private Long top;
  private String nombre;
  private Long count;
}
