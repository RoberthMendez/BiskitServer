package com.example.biskit.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopEnfermedadDto {
    private Long top;
    private String enfermedadNombre;
    private Long countPets;

}
