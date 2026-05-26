package com.example.biskit.entities.DTOs.Chat.ChatDTO;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.biskit.entities.Chat.Chat;
import com.example.biskit.entities.DTOs.Chat.MensajeDTO.MensajeDTO;
import com.example.biskit.entities.DTOs.Chat.ParticipanteChatDTO.ParticipanteChatDTO;

@Mapper
public interface ChatMapper {
    ChatMapper INSTANCE = Mappers.getMapper(ChatMapper.class);

    @Mapping(target = "participantes", source = "participantesChatDTO")
    @Mapping(target = "mensajes", source = "mensajesDTO")
    ChatDTO toDTO(Chat chat, List<ParticipanteChatDTO> participantesChatDTO, List<MensajeDTO> mensajesDTO);
}
