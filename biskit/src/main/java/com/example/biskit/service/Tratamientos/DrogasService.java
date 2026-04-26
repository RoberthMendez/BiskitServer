package com.example.biskit.service.Tratamientos;

import com.example.biskit.entities.Droga;

import java.util.List;

import com.example.biskit.entities.dtos.TopDto;
import com.example.biskit.entities.dtos.StockDroga;

public interface DrogasService {

    public List<Droga> getDrogas();

    public Droga getDrogaById(Long id);

    public void saveDroga(Droga droga);

    public Long getVentasTotales();

    public Long getGananciasTotales();

    public List<TopDto> getTop5Drogas();

    public List<StockDroga> getDrogasBajasStock();

}
