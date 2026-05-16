package com.example.biskit.entities.DTOs.HorarioDia;

import com.example.biskit.entities.Citas.HorarioDia;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HorarioDiaMapper {
  HorarioDiaMapper INSTANCE = Mappers.getMapper(HorarioDiaMapper.class);

  @Mapping(target = "horaInicio", expression = "java(horarioDia.getTurno().getHoraInicio())")
  @Mapping(target = "horaFin", expression = "java(horarioDia.getTurno().getHoraFin())")
  HorarioDiaDTO convert(HorarioDia horarioDia);

  List<HorarioDiaDTO> toDTOList(List<HorarioDia> horariosDia);
}
