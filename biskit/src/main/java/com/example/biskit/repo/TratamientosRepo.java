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

	//  Ventas Totales Mes: total de drogas usadas en el último mes finalizado
	@Query("""
        SELECT COUNT(d)
        FROM Tratamiento t
        JOIN t.drogas d
        WHERE YEAR(t.fecha) = YEAR(CURRENT_DATE - 1 MONTH)
          AND MONTH(t.fecha) = MONTH(CURRENT_DATE - 1 MONTH)
    """)
    Long countDrogasUltimoMes();

	// Ganancias Totales Mes: suma de precioVenta de cada droga en el último mes finalizado
    @Query("""
        SELECT COALESCE(SUM(d.precioVenta), 0)
        FROM Tratamiento t
        JOIN t.drogas d
        WHERE YEAR(t.fecha) = YEAR(CURRENT_DATE - 1 MONTH)
          AND MONTH(t.fecha) = MONTH(CURRENT_DATE - 1 MONTH)
    """)
    Long sumGananciasUltimoMes();

	//  Total de Tratamientos realizados en el ultimo mes
	@Query("""
        SELECT COUNT(t)
        FROM Tratamiento t
        WHERE YEAR(t.fecha) = YEAR(CURRENT_DATE - 1 MONTH)
          AND MONTH(t.fecha) = MONTH(CURRENT_DATE - 1 MONTH)
    """)
    Long countTratamientosUltimoMes();

	// Top 5 drogas más vendidas del último mes finalizado
	@Query("""
		SELECT d
		FROM Tratamiento t
		JOIN t.drogas d
		WHERE YEAR(t.fecha) = :anio
		AND MONTH(t.fecha) = :mes
		GROUP BY d
		ORDER BY COUNT(d) DESC
		LIMIT 5
	""")
	List<Droga> getTop5DrogasMasVendidasUltimoMes(@Param("anio") int anio, @Param("mes") int mes);

	@Query("""
		SELECT COUNT(t)
		FROM Tratamiento t
		JOIN t.drogas d
		WHERE d.id = :drogaId
		AND YEAR(t.fecha) = :anio
		AND MONTH(t.fecha) = :mes
	""")
	Long countDrogaEnUltimoMes(
		@Param("drogaId") Long drogaId,
		@Param("anio") int anio,
		@Param("mes") int mes
	);

}