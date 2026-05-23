package com.example.biskit.repo.citas;

import com.example.biskit.entities.Citas.Cita;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CitasRepo extends JpaRepository<Cita, Long> {
  @Query(
    "SELECT c FROM Cita c WHERE c.vet.id = :vetId AND CAST(c.fechaHora AS date) BETWEEN :fechaInicio AND :fechaFin ORDER BY c.fechaHora ASC"
  )
  public List<Cita> findByVetIdAndFechaHoraBetweenDates(
    @Param("vetId") Long vetId,
    @Param("fechaInicio") LocalDate fechaInicio,
    @Param("fechaFin") LocalDate fechaFin
  );

  // Nueva consulta para obtener citas con mascota null
  @Query(
    "SELECT c FROM Cita c WHERE c.vet.id = :vetId AND CAST(c.fechaHora AS date) BETWEEN :fechaInicio AND :fechaFin AND c.pet IS NULL ORDER BY c.fechaHora ASC"
  )
  public List<Cita> findByVetIdAndFechaHoraBetweenDatesSinMascota(
    @Param("vetId") Long vetId,
    @Param("fechaInicio") LocalDate fechaInicio,
    @Param("fechaFin") LocalDate fechaFin
  );

  @Query(
    "SELECT c FROM Cita c WHERE c.vet.id = :vetId AND CAST(c.fechaHora AS date) = :fecha ORDER BY c.fechaHora ASC"
  )
  public List<Cita> findByVetIdAndFecha(
    @Param("vetId") Long vetId,
    @Param("fecha") LocalDate fecha
  );

  @Modifying
  @Query("DELETE FROM Cita c WHERE c.pet.id = :petId")
  void deleteByPetId(@Param("petId") Long petId);

  @Modifying
  @Query("DELETE FROM Cita c WHERE c.vet.id = :vetId")
  void deleteByVetId(@Param("vetId") Long vetId);
}
