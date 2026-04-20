package com.example.biskit.controller.Vets;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.biskit.service.Vets.EspecialidadesService;
import com.example.biskit.entities.vets.Especialidad;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/vet/especialidades")
@CrossOrigin(origins = "http://localhost:4200")
public class EspecialidadesController {

  @Autowired
  private EspecialidadesService especialidadesService;

  // ----- Crear Especialidad (CREATE) -----
  @PostMapping("/add")
  public void crearEspecialidad(@RequestBody Especialidad especialidad) {
    especialidadesService.addEspecialidad(especialidad);
  }

  // ----- Mostrar Especialidades (READ) -----
  @GetMapping("")
  public List<Especialidad> mostrarEspecialidades() {
    return especialidadesService.getEspecialidades();
  }
  
}
