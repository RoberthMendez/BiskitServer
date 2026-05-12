package com.example.biskit.repo.citas;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.biskit.entities.citas.HorarioDia;

@Repository
public interface HorariosDiaRepo extends JpaRepository<HorarioDia, Long> {

  @Query("SELECT hd FROM HorarioDia hd WHERE hd.vet.id = :vetId AND hd.diaSemana = :diaSemana")
  public Optional<HorarioDia> findByVetIdAndDiaSemana(@Param("vetId") Long vetId, @Param("diaSemana") String diaSemana);

}
