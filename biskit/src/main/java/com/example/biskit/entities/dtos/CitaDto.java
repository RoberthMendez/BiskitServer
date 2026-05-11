package com.example.biskit.entities.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

import com.example.biskit.entities.citas.TipoCita;
import com.example.biskit.entities.pets.Pet;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CitaDto {

  private Long id;

  private String diaSemana;

  private LocalTime hora;

  private TipoCita tipoCita;

  private Pet pet;
  
}
