package com.example.biskit.service.Citas;

import com.example.biskit.entities.citas.Cita;
import com.example.biskit.entities.dtos.CitaDto;
import java.util.List;

public interface CitasService {
  public void addCita(CitaDto citaDto);

  public void addCitaDataLoader(Cita cita);

  public List<Cita> getCitasSemanaByVetId(Long vetId, int numSemana);

  public void updateCita(Long id, CitaDto citaDto);

  public void deleteCita(Long id);
}
