package com.example.biskit.service.Citas;

import com.example.biskit.entities.citas.TipoCita;
import com.example.biskit.repo.citas.TiposCitaRepo;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TiposCitaImpl implements TiposCitaService {

  @Autowired
  private TiposCitaRepo tiposCitaRepo;

  public List<TipoCita> getTiposCitas() {
    return tiposCitaRepo.findAll();
  }
}
