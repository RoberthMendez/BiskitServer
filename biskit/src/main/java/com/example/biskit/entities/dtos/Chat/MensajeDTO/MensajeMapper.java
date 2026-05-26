package com.example.biskit.entities.DTOs.Chat.MensajeDTO;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.biskit.entities.Chat.Mensaje;

@Mapper
public interface MensajeMapper {
    MensajeMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(MensajeMapper.class);

    @Mapping(target = "remitenteId", source = "remitente.credenciales.id")
    MensajeDTO toDTO(Mensaje mensaje);

    List<MensajeDTO> toDTOList(List<Mensaje> mensajes);
}
