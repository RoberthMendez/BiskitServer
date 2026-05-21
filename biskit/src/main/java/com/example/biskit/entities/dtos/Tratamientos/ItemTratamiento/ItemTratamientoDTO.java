package com.example.biskit.entities.DTOs.Tratamientos.ItemTratamiento;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemTratamientoDTO {

  private Long id;
  private String fecha;
  private String petNombre;
  private List<String> drogasNombres;
}
