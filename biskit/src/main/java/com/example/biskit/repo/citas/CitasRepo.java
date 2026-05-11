package com.example.biskit.repo.citas;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.biskit.entities.citas.Cita;

@Repository
public interface CitasRepo extends JpaRepository<Cita, Long> {

  @Query("SELECT c FROM Cita c WHERE c.vet.id = :vetId AND CAST(c.fechaHora AS date) BETWEEN :fechaInicio AND :fechaFin")
  public List<Cita> findByVetIdAndFechaHoraBetweenDates(@Param("vetId") Long vetId, @Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);

}