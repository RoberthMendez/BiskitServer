package com.example.biskit.service.Citas;

import com.example.biskit.entities.DTOs.CitaDTO;
import com.example.biskit.entities.citas.Cita;
import java.util.List;

public interface CitasService {
  public Cita addCita(CitaDTO citaDto);

  public void addCitaDataLoader(Cita cita);

  public List<Cita> getCitasSemanaByVetId(Long vetId, int numSemana);

  public Cita updateCita(Long id, CitaDTO citaDto);

  public void deleteCita(Long id);
}
