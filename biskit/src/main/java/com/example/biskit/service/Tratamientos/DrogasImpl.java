package com.example.biskit.service.Tratamientos;

import com.example.biskit.entities.DTOs.KPIs.StockDrogaDTO;
import com.example.biskit.entities.DTOs.KPIs.TopDTO;
import com.example.biskit.entities.Droga;
import com.example.biskit.errors.YaExiste.DrogaYaExisteException;
import com.example.biskit.repo.DrogasRepo;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
  public Droga saveDroga(Droga droga) {
    Optional<Droga> drogaExistente = drogasRepo.findByNombreIgnoreCase(droga.getNombre());
    if (
      drogaExistente.isPresent() &&
      (droga.getId() == null || !drogaExistente.get().getId().equals(droga.getId()))
    ) {
      throw new DrogaYaExisteException("La droga " + droga.getNombre() + " ya existe.");
    }
    return drogasRepo.save(droga);
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
  public List<TopDTO> getTop5Drogas() {
    List<Droga> topDrogas = drogasRepo.findTop5ByOrderByUnidadesVendidasDescPrecioVentaDesc();
    List<TopDTO> topDrogaDtos = new ArrayList<>();
    for (int i = 1; i <= topDrogas.size(); i++) {
      topDrogaDtos.add(
        new TopDTO(
          (long) i,
          topDrogas.get(i - 1).getNombre(),
          (long) topDrogas.get(i - 1).getUnidadesVendidas()
        )
      );
    }
    return topDrogaDtos;
  }

  @Override
  public List<StockDrogaDTO> getDrogasBajasStock() {
    List<Droga> drogasBajasStock = drogasRepo.findByUnidadesDisponiblesLessThanEqual(2);
    List<StockDrogaDTO> stockDrogaDtos = new ArrayList<>();
    for (Droga droga : drogasBajasStock) {
      stockDrogaDtos.add(
        new StockDrogaDTO(droga.getNombre(), (long) droga.getUnidadesDisponibles())
      );
    }
    return stockDrogaDtos;
  }
}
