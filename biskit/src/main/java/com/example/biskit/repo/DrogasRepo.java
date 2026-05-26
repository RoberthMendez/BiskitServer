package com.example.biskit.repo;

import com.example.biskit.entities.Droga;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DrogasRepo extends JpaRepository<Droga, Long> {
  @Query("SELECT COALESCE(SUM(d.unidadesVendidas), 0) FROM Droga d")
  public Long getVentasTotales();

  @Query("SELECT COALESCE(SUM(d.unidadesVendidas * d.precioVenta), 0) FROM Droga d")
  public Long getGananciasTotales();

  public List<Droga> findTop5ByOrderByUnidadesVendidasDescPrecioVentaDesc();

  public List<Droga> findByUnidadesDisponiblesLessThanEqual(Integer unidadesDisponibles);

  public boolean existsByNombreIgnoreCase(String nombre);

  public Optional<Droga> findByNombreIgnoreCase(String nombre);
}
