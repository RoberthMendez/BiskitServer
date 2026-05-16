package com.example.biskit.entities.DTOs.Tratamientos.TratamientoDetalle;

import com.example.biskit.entities.Client;
import com.example.biskit.entities.DTOs.Droga.DrogaDTO;
import com.example.biskit.entities.DTOs.Pets.PetDTO;
import com.example.biskit.entities.Vets.Vet;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TratamientoDetalleDTO {

  private LocalDate fecha;
  private List<DrogaDTO> drogas;
  private PetDTO pet;
  private Client client;
  private Vet vet;
}
