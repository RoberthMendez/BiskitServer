package com.example.biskit.service.Citas;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.biskit.entities.citas.Cita;
import com.example.biskit.repo.citas.CitasRepo;

@Service
public class CitasImpl implements CitasService {

  @Autowired
  private CitasRepo citasRepo;

  public List<Cita> getCitasSemanaByVetId(Long vetId) {
    
    LocalDate hoy = LocalDate.now();
    LocalDate inicioSemana = hoy.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    LocalDate finSemana = inicioSemana.plusDays(6);

    return citasRepo.findByVetIdAndFechaHoraBetweenDates(vetId, inicioSemana, finSemana);

  }
  
}
