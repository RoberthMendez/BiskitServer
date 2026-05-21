package com.example.biskit.entities.DTOs.Tratamientos.TratamientoDetalle;

import com.example.biskit.entities.DTOs.Droga.DrogaMapper;
import com.example.biskit.entities.DTOs.Pets.PetMapper;
import com.example.biskit.entities.Tratamiento;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { PetMapper.class, DrogaMapper.class })
public interface TratamientoDetalleMapper {
  TratamientoDetalleMapper INSTANCE = Mappers.getMapper(TratamientoDetalleMapper.class);

  @Mapping(target = "pet", source = "pet")
  @Mapping(target = "client", source = "pet.owner")
  @Mapping(target = "vet", source = "vet")
  @Mapping(target = "drogas", source = "drogas")
  TratamientoDetalleDTO toDto(Tratamiento tratamiento);

  List<TratamientoDetalleDTO> toDTOList(List<Tratamiento> tratamientos);
}
