package com.example.biskit.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopDto {
    private Long top;
    private String nombre;
    private Long count;

}
