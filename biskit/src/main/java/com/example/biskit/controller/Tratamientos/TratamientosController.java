package com.example.biskit.controller.Tratamientos;

import com.example.biskit.entities.DTOs.Tratamientos.TratamientoDTO;
import com.example.biskit.entities.DTOs.Tratamientos.TratamientoDetalle.TratamientoDetalleDTO;
import com.example.biskit.entities.DTOs.Tratamientos.TratamientoDetalle.TratamientoDetalleMapper;
import com.example.biskit.entities.Tratamiento;
import com.example.biskit.service.Tratamientos.TratamientosService;
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
@RequestMapping("/tratamientos")
@CrossOrigin(origins = "http://localhost:4200")
public class TratamientosController {

  @Autowired
  private TratamientosService tratamientosService;

  // ----- Crear Tratamiento (CREATE) -----
  // http://localhost:8080/tratamientos/add
  @PostMapping("/add")
  public ResponseEntity<Tratamiento> crearTratamiento(@RequestBody TratamientoDTO tratamientoDto) {
    return new ResponseEntity<>(
      tratamientosService.addTratamiento(tratamientoDto),
      HttpStatus.CREATED
    );
  }

  // ----- Mostrar Tratamiento por ID (READ) -----
  // http://localhost:8080/tratamientos/{id}
  @GetMapping("/{id}")
  public ResponseEntity<TratamientoDetalleDTO> getTratamientoPorId(@PathVariable Long id) {
    return new ResponseEntity<>(
      TratamientoDetalleMapper.INSTANCE.toDto(tratamientosService.getTratamientoById(id)),
      HttpStatus.OK
    );
  }

  // ----- Actualizar Tratamiento (UPDATE) -----
  // http://localhost:8080/tratamientos/update/{id}
  @PutMapping("/update/{id}")
  public ResponseEntity<Void> actualizarTratamiento(
    @PathVariable Long id,
    @RequestBody TratamientoDTO tratamientoDto
  ) {
    tratamientosService.updateTratamiento(id, tratamientoDto);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  // ----- Eliminar Tratamiento (DELETE) -----
  // http://localhost:8080/tratamientos/delete/{id}
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> eliminarTratamiento(@PathVariable Long id) {
    tratamientosService.deleteTratamiento(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
