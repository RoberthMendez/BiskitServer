package com.example.biskit.repo.citas;

import com.example.biskit.entities.Citas.Cita;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitasRepo extends JpaRepository<Cita, Long> {
  List<Cita> findByVetIdAndFechaHoraBetweenOrderByFechaHoraAsc(
    Long vetId,
    LocalDateTime fechaInicio,
    LocalDateTime fechaFin
  );

  List<Cita> findByVetIdAndFechaHoraBetweenAndPetIsNullOrderByFechaHoraAsc(
    Long vetId,
    LocalDateTime fechaInicio,
    LocalDateTime fechaFin
  );

  void deleteByPetId(Long petId);

  void deleteByVetId(Long vetId);

  void deleteByPetIdIsNull();
}
