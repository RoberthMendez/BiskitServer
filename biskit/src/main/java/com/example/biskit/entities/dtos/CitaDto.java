package com.example.biskit.entities.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CitaDto {

  private Long id;

  private String diaSemana;

  @DateTimeFormat(pattern = "hh:mm a")
  @JsonFormat(pattern = "hh:mm a", locale = "en_US")
  private LocalTime hora;

  private String tipoCitaNombre;

  private Integer duracionMinutos;

  private Long petId;

  private String petNombre;

  private String ownerNombre;

  private Long vetId;
}
