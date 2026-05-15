package com.example.biskit.controller;

import com.example.biskit.entities.DTOs.CitaDTO;
import com.example.biskit.entities.citas.Cita;
import com.example.biskit.entities.citas.TipoCita;
import com.example.biskit.service.Citas.CitasService;
import com.example.biskit.service.Citas.TiposCitaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/citas")
public class CitasController {

  @Autowired
  private CitasService citasService;

  @Autowired
  private TiposCitaService tiposCitaService;

  // ----- Crear Cita (CREATE) -----
  // http://localhost:8080/citas/add
  @PostMapping("/add")
  public ResponseEntity<Cita> crearCita(@RequestBody CitaDTO citaDto) {
    return new ResponseEntity<>(citasService.addCita(citaDto), HttpStatus.CREATED);
  }

  // ----- Obtener Tipos de Citas (READ) -----
  // http://localhost:8080/citas/tipos
  @GetMapping("/tipos")
  public ResponseEntity<List<TipoCita>> getTiposCitas() {
    return new ResponseEntity<>(tiposCitaService.getTiposCitas(), HttpStatus.OK);
  }

  // ----- Editar Cita (UPDATE) -----
  // http://localhost:8080/citas/update/{id}
  @PutMapping("/update/{id}")
  public ResponseEntity<Cita> editarCita(@PathVariable Long id, @RequestBody CitaDTO citaDto) {
    return new ResponseEntity<>(citasService.updateCita(id, citaDto), HttpStatus.OK);
  }

  // ----- Cancelar Cita (DELETE) -----
  // http://localhost:8080/citas/delete/{id}
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> cancelarCita(@PathVariable Long id) {
    citasService.deleteCita(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
