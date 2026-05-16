package com.example.biskit.controller.Vets;

import com.example.biskit.entities.DTOs.CitaDTO;
import com.example.biskit.entities.DTOs.HorarioDia.HorarioDiaDTO;
import com.example.biskit.entities.DTOs.HorarioDia.HorarioDiaMapper;
import com.example.biskit.entities.DTOs.Tratamientos.ItemTratamiento.ItemTratamientoDTO;
import com.example.biskit.entities.DTOs.Tratamientos.ItemTratamiento.ItemTratamientoMapper;
import com.example.biskit.entities.Vets.Vet;
import com.example.biskit.service.Vets.VetService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vets")
@CrossOrigin(origins = "http://localhost:4200")
public class VetsController {

  @Autowired
  private VetService vetService;

  // ----- Crear Veterinario (CREATE) -----
  // http://localhost:8080/vets/add
  @PostMapping("/add")
  public ResponseEntity<Vet> crearVeterinario(@RequestBody Vet vet) {
    return new ResponseEntity<>(vetService.addVet(vet), HttpStatus.CREATED);
  }

  // ----- Mostrar Veterinarios (READ) -----
  // http://localhost:8080/vets
  @GetMapping("")
  public ResponseEntity<List<Vet>> mostrarVets() {
    return new ResponseEntity<>(vetService.getVets(), HttpStatus.OK);
  }

  // ----- Mostrar Veterinario por ID (READ) -----
  // http://localhost:8080/vets/{id}
  @GetMapping("/{id}")
  public ResponseEntity<Vet> getVetById(@PathVariable Long id) {
    return new ResponseEntity<>(vetService.getVetById(id), HttpStatus.OK);
  }

  // ----- Mostrar Tratamientos de un Veterinario (READ) -----
  // http://localhost:8080/vets/{id}/tratamientos
  @GetMapping("/{id}/tratamientos")
  public ResponseEntity<List<ItemTratamientoDTO>> getTratamientosVet(@PathVariable Long id) {
    return new ResponseEntity<>(
      ItemTratamientoMapper.INSTANCE.toDTOList(vetService.getTratamientosVet(id)),
      HttpStatus.OK
    );
  }

  // ----- Actualizar Veterinario (UPDATE) -----
  // http://localhost:8080/vets/update/{id}
  @PutMapping("update/{id}")
  public ResponseEntity<Vet> actualizarVet(@PathVariable Long id, @RequestBody Vet vet) {
    vet.setId(id);
    return new ResponseEntity<>(vetService.saveVet(vet), HttpStatus.OK);
  }

  // ----- Eliminar Veterinario (DELETE) -----
  // http://localhost:8080/vets/delete/{id}
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> eliminarVet(@PathVariable Long id) {
    vetService.deleteVet(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  // ----- Contar Tratamientos de un Veterinario -----
  // http://localhost:8080/vets/{id}/tratamientos/count
  @GetMapping("/{id}/tratamientos/count")
  public ResponseEntity<Long> getVetTratamientosCount(@PathVariable Long id) {
    return new ResponseEntity<>(vetService.getVetTratamientosCount(id), HttpStatus.OK);
  }

  // ----- Comprobar id de Veterinario (GET) -----
  // http://localhost:8080/vets/{id}/exists
  @GetMapping("/{id}/exists")
  public ResponseEntity<Void> checkVetId(@PathVariable Long id) {
    vetService.getVetById(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  // ----- AGENDA Y CITAS -----

  // http://localhost:8080/vets/{id}/horario-semanal
  @GetMapping("/{id}/horario-semanal")
  public ResponseEntity<List<HorarioDiaDTO>> getHorarioSemanalByVetId(@PathVariable Long id) {
    return new ResponseEntity<>(
      HorarioDiaMapper.INSTANCE.toDTOList(vetService.getHorarioSemanalByVetId(id)),
      HttpStatus.OK
    );
  }

  // http://localhost:8080/vets/{id}/citas-semanales?numSemana=x
  @GetMapping("/{id}/citas-semanales")
  public ResponseEntity<List<CitaDTO>> getCitasSemanaByVetId(
    @PathVariable Long id,
    @RequestParam(required = true) int numSemana
  ) {
    return new ResponseEntity<>(vetService.getCitasSemanaByVetId(id, numSemana), HttpStatus.OK);
  }

  // http://localhost:8080/vets/details
  @GetMapping("/details")
  public ResponseEntity<Vet> buscarVet() {
    Vet vet = vetService.findByUsuario(
      SecurityContextHolder.getContext().getAuthentication().getName()
    );

    if (vet == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(vet, HttpStatus.OK);
  }
}
