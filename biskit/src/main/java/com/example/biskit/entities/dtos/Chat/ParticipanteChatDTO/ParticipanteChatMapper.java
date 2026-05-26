package com.example.biskit.entities.DTOs.Chat.ParticipanteChatDTO;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.biskit.entities.Chat.ParticipanteChat;

@Mapper
public interface ParticipanteChatMapper {
    ParticipanteChatMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(ParticipanteChatMapper.class);

    @Mapping(target = "credencialesId", source = "credenciales.id")
    @Mapping(target = "rol", source = "credenciales.rol.nombre")
    ParticipanteChatDTO toDTO(ParticipanteChat participanteChat);

    List<ParticipanteChatDTO> toDTOList(List<ParticipanteChat> participantesChat);
}
