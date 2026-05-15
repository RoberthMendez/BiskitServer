package com.example.biskit.service.Tratamientos;

import com.example.biskit.entities.DTOs.StockDroga;
import com.example.biskit.entities.DTOs.TopDto;
import com.example.biskit.entities.Droga;
import java.util.List;

public interface DrogasService {
  public List<Droga> getDrogas();

  public Droga getDrogaById(Long id);

  public Droga saveDroga(Droga droga);

  public Long getVentasTotales();

  public Long getGananciasTotales();

  public List<TopDto> getTop5Drogas();

  public List<StockDroga> getDrogasBajasStock();
}
