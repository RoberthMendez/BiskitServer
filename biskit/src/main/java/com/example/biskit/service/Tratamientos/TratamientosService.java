package com.example.biskit.service.Tratamientos;

import com.example.biskit.entities.DTOs.KPIs.DrogaTratamientoCountDTO;
import com.example.biskit.entities.DTOs.KPIs.TopDTO;
import com.example.biskit.entities.DTOs.Tratamientos.TratamientoDTO;
import com.example.biskit.entities.DTOs.Tratamientos.TratamientosMesDTO;
import com.example.biskit.entities.Tratamiento;
import java.util.List;

public interface TratamientosService {
  public Tratamiento getTratamientoById(Long id);

  public Tratamiento addTratamiento(TratamientoDTO tratamientoDto);

  public void addTratamiento(Tratamiento tratamiento);

  public void updateTratamiento(Long id, TratamientoDTO tratamientoDto);

  public void deleteTratamiento(Long id);

  public List<Tratamiento> getTratamientosByPetId(Long petId);

  public List<TratamientosMesDTO> getNumTratamientos6Meses();

  public List<DrogaTratamientoCountDTO> getDrogaTratamientosMesCount();

  public List<Tratamiento> getTratamientosByVetId(Long vetId);

  public Long getVentasTotalesMes();

  public Long getGananciasTotalesMes();

  public Long countTratamientosUltimoMes();

  public List<TopDTO> getTop5DrogasUltimoMes();
}
