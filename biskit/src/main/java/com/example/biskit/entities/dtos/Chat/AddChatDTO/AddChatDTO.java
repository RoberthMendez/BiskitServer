package com.example.biskit.entities.DTOs.Chat.AddChatDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddChatDTO {

    private Long idCliente;
    private Long idVeterinario;
}