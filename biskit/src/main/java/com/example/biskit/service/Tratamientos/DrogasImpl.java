package com.example.biskit.service.Tratamientos;

import com.example.biskit.entities.Droga;
import com.example.biskit.entities.dtos.StockDroga;
import com.example.biskit.entities.dtos.TopDto;
import com.example.biskit.repo.DrogasRepo;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class DrogasImpl implements DrogasService {

  @Autowired
  private DrogasRepo drogasRepo;

  @Override
  public List<Droga> getDrogas() {
    return drogasRepo.findAll();
  }

  @Override
  public Droga getDrogaById(Long id) {
    return drogasRepo
      .findById(id)
      .orElseThrow(() -> new RuntimeException("No se encontró droga con id: " + id));
  }

  @Override
  public void saveDroga(Droga droga) {
    drogasRepo.save(droga);
  }

  @Override
  public Long getVentasTotales() {
    return drogasRepo.getVentasTotales();
  }

  @Override
  public Long getGananciasTotales() {
    return drogasRepo.getGananciasTotales();
  }

  @Override
  public List<TopDto> getTop5Drogas() {
    List<Droga> topDrogas = drogasRepo.findTop5ByOrderByUnidadesVendidasDescPrecioVentaDesc();
    List<TopDto> topDrogaDtos = new ArrayList<>();
    for (int i = 1; i <= topDrogas.size(); i++) {
      topDrogaDtos.add(
        new TopDto(
          (long) i,
          topDrogas.get(i - 1).getNombre(),
          (long) topDrogas.get(i - 1).getUnidadesVendidas()
        )
      );
    }
    return topDrogaDtos;
  }

  @Override
  public List<StockDroga> getDrogasBajasStock() {
    List<Droga> drogasBajasStock = drogasRepo.findByStockLessThanEqual();
    List<StockDroga> stockDrogaDtos = new ArrayList<>();
    for (Droga droga : drogasBajasStock) {
      stockDrogaDtos.add(new StockDroga(droga.getNombre(), (long) droga.getUnidadesDisponibles()));
    }
    return stockDrogaDtos;
  }
}
