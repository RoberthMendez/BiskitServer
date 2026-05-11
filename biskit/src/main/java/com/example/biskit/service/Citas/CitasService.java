package com.example.biskit.service.Citas;

import java.util.List;

import com.example.biskit.entities.citas.Cita;

public interface CitasService {

  public List<Cita> getCitasSemanaByVetId(Long vetId);
  
}
