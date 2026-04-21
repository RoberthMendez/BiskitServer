package com.example.biskit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.biskit.entities.Tratamiento;
import com.example.biskit.entities.dtos.TratamientoDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import com.example.biskit.service.Tratamientos.TratamientosService;

@RestController
@RequestMapping("/tratamientos")
@CrossOrigin(origins = "http://localhost:4200")
public class TratamientosController {
  
  @Autowired
  private TratamientosService tratamientosService;

  // ----- Crear Tratamiento (CREATE) -----
  @PostMapping("/add")
  public void crearTratamiento(@RequestBody TratamientoDto tratamientoDto) {
      tratamientosService.addTratamiento(tratamientoDto);
  }

  // ----- Mostrar Tratamientos de una Mascota (READ) ------
  @GetMapping("/pet/{id}")
  public List<Tratamiento> getTratamientosPorMascota(@PathVariable Long id) {
      return tratamientosService.getTratamientosByPetId(id);
  }

  // ----- Mostrar Tratamiento por ID (READ) -----
  @GetMapping("/{id}")
  public Tratamiento getTratamientoPorId(@PathVariable Long id) {
      return tratamientosService.getTratamientoById(id);
  }

  // ----- Actualizar Tratamiento (UPDATE) -----
  @PutMapping("/update/{id}")
  public void actualizarTratamiento(@PathVariable Long id, @RequestBody TratamientoDto tratamientoDto) {
      tratamientosService.updateTratamiento(id, tratamientoDto);
  }

  // ----- Eliminar Tratamiento (DELETE) -----
  @DeleteMapping("/delete/{id}")
  public void eliminarTratamiento(@PathVariable Long id) {
      tratamientosService.deleteTratamiento(id);
  }
  

}
