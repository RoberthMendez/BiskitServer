package com.example.biskit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.biskit.service.Tratamientos.TratamientosService;
import com.example.biskit.service.Vets.VetService;
import com.example.biskit.service.Pets.PetsService;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

    @Autowired
    private TratamientosService tratamientosService;

    @Autowired
    private VetService vetsService;

    @Autowired
    private PetsService petsService;

    // http://localhost:8080/admin/ultimos-tratamientos-count
    @GetMapping("/ultimos-tratamientos-count")
    public Long getUltimosTratamientos() {
        return tratamientosService.getUltimosTratamientosCount();
    }

    // http://localhost:8080/admin/droga/{id}/tratamientos-mes-count
    @GetMapping("/droga/{id}/tratamientos-mes-count")
    public Long getTratamientosMedicamentoCount(@PathVariable("id") Long id) {
        return tratamientosService.getTratamientosMedicamentoMesCount(id);
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
        return petsService.getMascotasCount();
    }

    // http://localhost:8080/admin/mascotas-inactivas-count
    @GetMapping("/mascotas-inactivas-count")
    public Long getMascotasInactivasCount() {
        return petsService.getMascotasInactivasCount();
    }
}
