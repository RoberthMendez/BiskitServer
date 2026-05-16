package com.example.biskit.entities.DTOs.Tratamientos;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TratamientoDTO {

  private Long id;
  private LocalDate fecha;
  private Long petId;
  private Long vetId;
  private List<Long> drogasIds;
}
