package com.example.biskit.entities.DTOs.Chat.MensajeDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MensajeDTO {

    private Long id;
    private Long remitenteId;
    private String contenido;
    private String timestamp;
}
