package com.example.biskit.service.Tratamientos;

import com.example.biskit.entities.DTOs.KPIs.StockDrogaDTO;
import com.example.biskit.entities.DTOs.KPIs.TopDTO;
import com.example.biskit.entities.Droga;
import java.util.List;

public interface DrogasService {
  public List<Droga> getDrogas();

  public Droga getDrogaById(Long id);

  public Droga saveDroga(Droga droga);

  public Long getVentasTotales();

  public Long getGananciasTotales();

  public List<TopDTO> getTop5Drogas();

  public List<StockDrogaDTO> getDrogasBajasStock();
}
