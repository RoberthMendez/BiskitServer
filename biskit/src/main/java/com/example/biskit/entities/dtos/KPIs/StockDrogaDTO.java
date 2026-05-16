package com.example.biskit.entities.DTOs.KPIs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockDrogaDTO {

  private String drogaNombre;
  private Long stockActual;
}
