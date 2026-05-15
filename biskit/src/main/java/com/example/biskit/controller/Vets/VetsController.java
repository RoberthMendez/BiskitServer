package com.example.biskit.controller.Vets;

import com.example.biskit.entities.citas.HorarioDia;
import com.example.biskit.entities.dtos.CitaDto;
import com.example.biskit.entities.vets.Vet;
import com.example.biskit.service.Vets.VetService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
  @PostMapping("/add")
  public ResponseEntity<Vet> crearVeterinario(@RequestBody Vet vet) {
    return new ResponseEntity<Vet>(vetService.addVet(vet), HttpStatus.CREATED);
  }

  // ----- Mostrar Veterinarios (READ) -----
  @GetMapping("")
  public List<Vet> mostrarVets() {
    return vetService.getVets();
  }

  // ----- Mostrar Veterinario por ID (READ) -----
  @GetMapping("/{id}")
  public Vet getVetById(@PathVariable Long id) {
    return vetService.getVetById(id);
  }

  // ----- Actualizar Veterinario (UPDATE) -----
  @PutMapping("update/{id}")
  public void actualizarVet(@PathVariable Long id, @RequestBody Vet vet) {
    vet.setId(id);
    vetService.saveVet(vet);
  }

  // ----- Eliminar Veterinario (DELETE) -----
  @DeleteMapping("/delete/{id}")
  public void eliminarVet(@PathVariable Long id) {
    vetService.deleteVet(id);
  }

  // ----- Contar Tratamientos de un Veterinario -----
  @GetMapping("/{id}/tratamientos/count")
  public Long getVetTratamientosCount(@PathVariable Long id) {
    return vetService.getVetTratamientosCount(id);
  }

  // ----- Cambiar Estado de Veterinario (PATCH) -----
  @PatchMapping("/update-estado/{id}")
  public void cambiarEstadoVet(@PathVariable Long id, @RequestBody Map<String, Boolean> body) {
    vetService.cambiarEstadoVet(id, body.get("estado"));
  }

  // ----- Comprobar id de Veterinario (GET) -----
  @GetMapping("/{id}/exists")
  public ResponseEntity<Void> checkVetId(@PathVariable Long id) {
    vetService.getVetById(id);
    return ResponseEntity.ok().build();
  }

  // ----- AGENDA Y CITAS -----
  @GetMapping("/{id}/horario-semanal")
  public ResponseEntity<List<HorarioDia>> getHorarioSemanalByVetId(@PathVariable Long id) {
    List<HorarioDia> horarios = vetService.getHorarioSemanalByVetId(id);
    return ResponseEntity.ok(horarios);
  }

  @GetMapping("/{id}/citas-semanales")
  public ResponseEntity<List<CitaDto>> getCitasSemanaByVetId(
    @PathVariable Long id,
    @RequestParam(required = true) int numSemana
  ) {
    List<CitaDto> citas = vetService.getCitasSemanaByVetId(id, numSemana);
    return ResponseEntity.ok(citas);
  }
}
