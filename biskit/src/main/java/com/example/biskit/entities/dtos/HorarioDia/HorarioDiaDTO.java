package com.example.biskit.entities.DTOs.HorarioDia;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HorarioDiaDTO {

  private String diaSemana;

  @JsonFormat(pattern = "hh:mm a", locale = "en_US")
  private LocalTime horaInicio;

  @JsonFormat(pattern = "hh:mm a", locale = "en_US")
  private LocalTime horaFin;
}
