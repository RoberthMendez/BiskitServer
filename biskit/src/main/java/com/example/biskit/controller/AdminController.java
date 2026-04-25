package com.example.biskit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.biskit.service.Tratamientos.TratamientosService;
import com.example.biskit.service.Vets.VetService;
import com.example.biskit.entities.Admin;
import com.example.biskit.entities.dtos.DrogaTratamientoCountDto;
import com.example.biskit.entities.dtos.StockDroga;
import com.example.biskit.entities.dtos.TratamientosMesDto;
import com.example.biskit.service.Admin.AdminsService;
import com.example.biskit.service.Pets.PetsService;
import com.example.biskit.service.Tratamientos.DrogasService;
import java.util.List;

import com.example.biskit.entities.dtos.TopDrogaDto;
import com.example.biskit.entities.dtos.TopEnfermedadDto;


@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

    @Autowired
    private AdminsService adminsService;

    @Autowired
    private TratamientosService tratamientosService;

    @Autowired
    private VetService vetsService;

    @Autowired
    private PetsService petsService;

    @Autowired
    private DrogasService drogasService;

    @GetMapping("/{id}")
    public Admin getAdminById(@PathVariable Long id) {
        return adminsService.findById(id);
    }

    // http://localhost:8080/admin/ultimos-tratamientos-count
    @GetMapping("/ultimos-tratamientos-count")
    public ResponseEntity<List<TratamientosMesDto>> getUltimosTratamientos() {
        return ResponseEntity.ok(tratamientosService.getNumTratamientos6Meses());
    }

    // http://localhost:8080/admin/droga-tratamientos-mes-count
    @GetMapping("/droga-tratamientos-mes-count")
    public ResponseEntity<List<DrogaTratamientoCountDto>> getTratamientosMedicamentoCount() {
        return ResponseEntity.ok(tratamientosService.getDrogaTratamientosMesCount());
    }

    // http://localhost:8080/admin/vets-count
    @GetMapping("/vets-count")
    public Long getVetsCount() {
        return vetsService.getVetsCount();
    }

    // http://localhost:8080/admin/vets-inactivos-count
    @GetMapping("/vets-inactivos-count")
    public Long getVetsInactivosCount() {
        return vetsService.getVetsInactivosCount();
    }

    // http://localhost:8080/admin/vets-activos-count
    @GetMapping("/vets-activos-count")
    public Long getVetsActivosCount() {
        return vetsService.getVetsActivosCount();
    }

    // http://localhost:8080/admin/mascotas-count
    @GetMapping("/mascotas-count")
    public Long getMascotasCount() {
        return petsService.getPetsCount();
    }

    @GetMapping("/mascotas-activas-count")
    public Long getMascotasActivasCount() {
        return petsService.getMascotasActivasCount();
    }

    // http://localhost:8080/admin/mascotas-inactivas-count
    @GetMapping("/mascotas-inactivas-count")
    public Long getMascotasInactivasCount() {
        return petsService.getPetsInactivosCount();
    }

    // http://localhost:8080/admin/ventas-totales
    @GetMapping("/ventas-totales")
    public Long getVentasTotales() {
        return drogasService.getVentasTotales();
    }

    // http://localhost:8080/admin/ganancias-totales
    @GetMapping("/ganancias-totales")
    public Long getGananciasTotales() {
        return drogasService.getGananciasTotales();
    }

    // http://localhost:8080/admin/top5-drogas
    @GetMapping("/top5-drogas")
    public ResponseEntity<List<TopDrogaDto>> getTop5Drogas() {
        return ResponseEntity.ok(drogasService.getTop5Drogas());
    }

    // http://localhost:8080/admin/top5-enfermedades
    @GetMapping("/top5-enfermedades")
    public ResponseEntity<List<TopEnfermedadDto>> getTop5Enfermedades() {
        return ResponseEntity.ok(petsService.getTop5Enfermedades());
    }

    // http://localhost:8080/admin/drogas-bajas-stock
    @GetMapping("/drogas-bajas-stock")
    public ResponseEntity<List<StockDroga>> getDrogasBajasStock() {
        return ResponseEntity.ok(drogasService.getDrogasBajasStock());
    }

}
