package com.example.biskit.controller;

import com.example.biskit.entities.citas.TipoCita;
import com.example.biskit.entities.dtos.CitaDto;
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
  @PostMapping("/add")
  public ResponseEntity<Void> crearCita(@RequestBody CitaDto citaDto) {
    citasService.addCita(citaDto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  // ----- Obtener Tipos de Citas (READ) -----
  @GetMapping("/tipos")
  public ResponseEntity<List<TipoCita>> getTiposCitas() {
    List<TipoCita> tipos = tiposCitaService.getTiposCitas();
    return ResponseEntity.ok(tipos);
  }

  // ----- Editar Cita (UPDATE) -----
  @PutMapping("/update/{id}")
  public ResponseEntity<Void> editarCita(@PathVariable Long id, @RequestBody CitaDto citaDto) {
    citasService.updateCita(id, citaDto);
    return ResponseEntity.ok().build();
  }

  // ----- Cancelar Cita (DELETE) -----
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> cancelarCita(@PathVariable Long id) {
    citasService.deleteCita(id);
    return ResponseEntity.ok().build();
  }
}
