package com.example.biskit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.biskit.service.Tratamientos.TratamientosService;
import com.example.biskit.service.Vets.VetService;


@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

    @Autowired
    private TratamientosService tratamientosService;

    @Autowired
    private VetService vetsService;

    //http://localhost:8080/admin/ultimos-tratamientos-count
    @GetMapping("/ultimos-tratamientos-count")
    public Long getUltimosTratamientos() {
        return tratamientosService.getUltimosTratamientosCount();
    }
    
    //http://localhost:8080/admin/droga/{id}/tratamientos-count
    @GetMapping("/droga/{id}/tratamientos-count")
    public Long getTratamientosMedicamentoCount(@PathVariable("id") Long id) {
        return tratamientosService.getTratamientosMedicamentoCount(id);
    }

    //http://localhost:8080/admin/vets-inactivos
    @GetMapping("/vets-inactivos")
    public Long getVetsInactivosCount(){
        return vetsService.getVetsInactivosCount();
    }
}
