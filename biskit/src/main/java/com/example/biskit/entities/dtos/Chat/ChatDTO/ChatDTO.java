package com.example.biskit.entities.DTOs.Chat.ChatDTO;

import lombok.Data;

import java.util.List;

import com.example.biskit.entities.DTOs.Chat.MensajeDTO.MensajeDTO;
import com.example.biskit.entities.DTOs.Chat.ParticipanteChatDTO.ParticipanteChatDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatDTO {

    private Long id;
    private List<ParticipanteChatDTO> participantes;
    private List<MensajeDTO> mensajes;
}
