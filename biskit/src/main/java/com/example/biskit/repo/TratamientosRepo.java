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

	@Query("SELECT COUNT(t) FROM Tratamiento t WHERE t.fecha >= :startDate")
	Long getUltimosTratamientosCount(@Param("startDate") LocalDate startDate);

	@Query("SELECT COUNT(t) FROM Tratamiento t JOIN t.drogas d WHERE d.id = :medicamentoId AND t.fecha >= :startDate")
	Long getNumTratamientosDrogaDesde(@Param("medicamentoId") long medicamentoId,
			@Param("startDate") LocalDate startDate);

	@Query("SELECT DISTINCT d FROM Tratamiento t JOIN t.drogas d WHERE t.fecha >= :startDate")
	List<Droga> getDrogasDesde(@Param("startDate") LocalDate startDate);

	@Query("SELECT COUNT(t) FROM Tratamiento t WHERE t.vet.id = :vetId")
	Long getTratamientosVetCount(@Param("vetId") long vetId);
}