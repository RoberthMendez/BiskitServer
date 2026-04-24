package com.example.biskit.service.Tratamientos;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;

import com.example.biskit.entities.Droga;
import com.example.biskit.repo.DrogasRepo;

import com.example.biskit.entities.dtos.TopDrogaDto;

@Service
public class DrogasImpl implements DrogasService {

    @Autowired
    private DrogasRepo drogasRepo;

    @Override
    public List<Droga> getDrogas() {
        return drogasRepo.findAll();
    }

    @Override
    public Droga getDrogaById(Long id) {
        return drogasRepo.findById(id).orElseThrow(() -> new RuntimeException("No se encontró droga con id: " + id));
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
    public List<TopDrogaDto> getTop5Drogas() {
        List<Droga> topDrogas = drogasRepo.findTop5ByOrderByUnidadesVendidasDescPrecioVentaDesc();
        List<TopDrogaDto> topDrogaDtos = new ArrayList<>();
        for (int i = 1; i <= topDrogas.size(); i++) {
            topDrogaDtos.add(new TopDrogaDto((long) i, topDrogas.get(i - 1).getNombre(),
                    (long) topDrogas.get(i - 1).getUnidadesVendidas()));
        }
        return topDrogaDtos;
    }
}
