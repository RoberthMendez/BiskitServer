package com.example.biskit.repo;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.biskit.entities.Droga;
import java.util.List;

@Repository
public interface DrogasRepo extends JpaRepository<Droga, Long> {

    @Query("SELECT COALESCE(SUM(d.unidadesVendidas), 0) FROM Droga d")
    public Long getVentasTotales();

    @Query("SELECT COALESCE(SUM(d.unidadesVendidas * d.precioVenta), 0) FROM Droga d")
    public Long getGananciasTotales();

    public List<Droga> findTop5ByOrderByUnidadesVendidasDescPrecioVentaDesc();

}