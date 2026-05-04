package com.example.biskit.repo;

import java.util.List;
import java.time.LocalDate;

import org.springframework.stereotype.Repository;

import com.example.biskit.entities.Droga;
import com.example.biskit.entities.Tratamiento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface TratamientosRepo extends JpaRepository<Tratamiento, Long> {

	List<Tratamiento> findByPetId(Long petId);

	List<Tratamiento> findByVetId(Long vetId);

  // Obtener el número total de tratamientos realizados en un mes específico
	@Query("SELECT COUNT(t) FROM Tratamiento t WHERE YEAR(t.fecha) = :year AND MONTH(t.fecha) = :month")
	Long getNumTratamientosMes(@Param("year") int year, @Param("month") int month);

  // Obtener el número de tratamientos que incluyen un medicamento específico desde una fecha dada
	@Query("SELECT COUNT(t) FROM Tratamiento t JOIN t.drogas d WHERE d.id = :medicamentoId AND t.fecha >= :startDate")
	Long getNumTratamientosDrogaDesde(@Param("medicamentoId") long medicamentoId,
			@Param("startDate") LocalDate startDate);

  // Obtener la lista de medicamentos utilizados en tratamientos desde una fecha dada
	@Query("SELECT DISTINCT d FROM Tratamiento t JOIN t.drogas d WHERE t.fecha >= :startDate")
	List<Droga> getDrogasDesde(@Param("startDate") LocalDate startDate);

  // Obtener el número de tratamientos realizados por un veterinario específico
	@Query("SELECT COUNT(t) FROM Tratamiento t WHERE t.vet.id = :vetId")
	Long getTratamientosVetCount(@Param("vetId") long vetId);

}