package com.example.biskit.controller.Vets;

import com.example.biskit.entities.Vets.Especialidad;
import com.example.biskit.service.Vets.EspecialidadesService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/especialidades")
@CrossOrigin(origins = "http://localhost:4200")
public class EspecialidadesController {

  @Autowired
  private EspecialidadesService especialidadesService;

  // ----- Crear Especialidad (CREATE) -----
  // http://localhost:8080/especialidades/add
  @PostMapping("/add")
  public ResponseEntity<Especialidad> crearEspecialidad(@RequestBody Especialidad especialidad) {
    return new ResponseEntity<>(
      especialidadesService.addEspecialidad(especialidad),
      HttpStatus.CREATED
    );
  }

  // ----- Mostrar Especialidades (READ) -----
  // http://localhost:8080/especialidades
  @GetMapping("")
  public ResponseEntity<List<Especialidad>> mostrarEspecialidades() {
    return new ResponseEntity<>(especialidadesService.getEspecialidades(), HttpStatus.OK);
  }
}
