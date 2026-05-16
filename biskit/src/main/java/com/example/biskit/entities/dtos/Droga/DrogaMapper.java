package com.example.biskit.entities.DTOs.Droga;

import com.example.biskit.entities.Droga;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DrogaMapper {
  DrogaMapper INSTANCE = Mappers.getMapper(DrogaMapper.class);

  DrogaDTO convert(Droga droga);

  List<DrogaDTO> toDTOList(List<Droga> drogas);
}
