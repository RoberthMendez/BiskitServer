package com.example.biskit.service.Tratamientos;

import com.example.biskit.entities.DTOs.DrogaTratamientoCountDto;
import com.example.biskit.entities.DTOs.TopDto;
import com.example.biskit.entities.DTOs.TratamientoDto;
import com.example.biskit.entities.DTOs.TratamientosMesDto;
import com.example.biskit.entities.Tratamiento;
import java.util.List;

public interface TratamientosService {
  public Tratamiento getTratamientoById(Long id);

  public void addTratamiento(TratamientoDto tratamientoDto);

  public void addTratamiento(Tratamiento tratamiento);

  public void updateTratamiento(Long id, TratamientoDto tratamientoDto);

  public void deleteTratamiento(Long id);

  public List<Tratamiento> getTratamientosByPetId(Long petId);

  public List<TratamientosMesDto> getNumTratamientos6Meses();

  public List<DrogaTratamientoCountDto> getDrogaTratamientosMesCount();

  public List<Tratamiento> getTratamientosByVetId(Long vetId);

  public Long getVentasTotalesMes();

  public Long getGananciasTotalesMes();

  public Long countTratamientosUltimoMes();

  public List<TopDto> getTop5DrogasUltimoMes();
}
