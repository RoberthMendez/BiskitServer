package com.example.biskit.entities.DTOs.Tratamientos.ItemTratamiento;

import com.example.biskit.entities.Tratamiento;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ItemTratamientoMapper {
  ItemTratamientoMapper INSTANCE = Mappers.getMapper(ItemTratamientoMapper.class);

  @Mapping(target = "petNombre", expression = "java(tratamiento.getPet().getNombre())")
  @Mapping(
    target = "drogasNombres",
    expression = "java(tratamiento.getDrogas().stream().map(d -> d.getNombre()).toList())"
  )
  ItemTratamientoDTO convert(Tratamiento tratamiento);

  List<ItemTratamientoDTO> toDTOList(List<Tratamiento> tratamientos);
}
