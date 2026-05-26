package com.example.biskit.service.Citas;

import com.example.biskit.entities.Citas.Cita;
import com.example.biskit.entities.DTOs.CitaDto;
import java.util.List;

public interface CitasService {
  public Cita addCita(CitaDto citaDto, int numSemana);

  public void addCitaDataLoader(Cita cita);

  public List<Cita> getCitasSemanaByVetId(Long vetId, int numSemana);

  public List<Cita> getCitasSemanaByVetIdSinMascota(Long vetId, int numSemana);

  public Cita updateCita(Long id, CitaDto citaDto, int numSemana);

  public void deleteCita(Long id);
}
