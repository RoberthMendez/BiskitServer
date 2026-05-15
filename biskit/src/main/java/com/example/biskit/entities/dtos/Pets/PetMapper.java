package com.example.biskit.entities.DTOs.Pets;

import com.example.biskit.entities.pets.Pet;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PetMapper {
  PetMapper INSTANCE = Mappers.getMapper(PetMapper.class);

  @Mapping(target = "edad", expression = "java(calcularEdad(pet.getFechaNacimiento()))")
  @Mapping(target = "enfermedad", source = "enfermedad.nombre")
  @Mapping(target = "owner", source = "owner.nombre")
  @Mapping(target = "raza", source = "raza.nombre")
  @Mapping(target = "especie", source = "raza.especie.nombre")
  PetDTO convert(Pet pet);

  List<PetDTO> toDTOList(List<Pet> pets);

  default int calcularEdad(Date fechaNacimiento) {
    if (fechaNacimiento == null) {
      return 0;
    }

    return Period.between(fechaNacimiento.toLocalDate(), LocalDate.now()).getYears();
  }
}
