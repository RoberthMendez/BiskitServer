package com.example.biskit.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VetsFiltrosDto {

    private Boolean estado;
    private String especialidad;
    private Integer tratamientos;
    private String pet; 
    
}