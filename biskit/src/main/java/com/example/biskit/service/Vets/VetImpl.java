package com.example.biskit.service.Vets;

import com.example.biskit.entities.Tratamiento;
import com.example.biskit.entities.citas.Cita;
import com.example.biskit.entities.citas.HorarioDia;
import com.example.biskit.entities.dtos.CitaDto;
import com.example.biskit.entities.dtos.VetsFiltrosDto;
import com.example.biskit.entities.pets.Pet;
import com.example.biskit.entities.vets.Especialidad;
import com.example.biskit.entities.vets.Vet;
import com.example.biskit.errors.VetNotFoundException;
import com.example.biskit.repo.TratamientosRepo;
import com.example.biskit.repo.pets.PetsRepo;
import com.example.biskit.repo.vets.VetsRepo;
import com.example.biskit.service.Citas.CitasService;
import com.example.biskit.service.Credenciales.CredencialesService;
import com.example.biskit.specifications.VetsSpecification;
import jakarta.transaction.Transactional;
import java.text.Normalizer;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class VetImpl implements VetService {

  private static final List<String> ORDEN_DIAS_SEMANA = List.of(
    "lunes",
    "martes",
    "miercoles",
    "jueves",
    "viernes",
    "sabado",
    "domingo"
  );

  @Autowired
  private VetsRepo vetsRepo;

  @Autowired
  private PetsRepo petsRepo;

  @Autowired
  private TratamientosRepo tratamientosRepo;

  @Autowired
  private EspecialidadesService especialidadesService;

  @Autowired
  private CredencialesService credencialesService;

  @Autowired
  private CitasService citasService;

  @Override
  public List<Vet> getVets() {
    return vetsRepo.findAll();
  }

  @Override
  public Vet getVetById(Long id) {
    return vetsRepo.findById(id).orElseThrow(() -> new VetNotFoundException(id));
  }

  @Override
  public void addVet(Vet vet) {
    if (vet.getId() == null) {
      vet.setEstado(true);
    }

    if (vet.getCredenciales().getId() == null) {
      vet.getCredenciales().setUsuario(vet.getCorreo());
      vet.getCredenciales().setPassword(vet.getCedula());
      credencialesService.addCredenciales(vet.getCredenciales());
    }

    Especialidad especialidad = especialidadesService.getEspecialidadById(
      vet.getEspecialidad().getId()
    );
    vet.setEspecialidad(especialidad);
    vetsRepo.save(vet);
  }

  @Override
  public void saveVet(Vet vet) {
    vetsRepo.save(vet);
  }

  @Override
  public boolean autenticarVet(String usuario, String contrasena) {
    return vetsRepo
      .findAll()
      .stream()
      .filter(vet -> vet.getCredenciales() != null)
      .anyMatch(
        vet ->
          usuario.equals(vet.getCredenciales().getUsuario()) &&
          contrasena.equals(vet.getCredenciales().getPassword())
      );
  }

  @Override
  public Vet findByUsuario(String usuario) {
    return vetsRepo
      .findAll()
      .stream()
      .filter(vet -> vet.getCredenciales() != null)
      .filter(vet -> usuario.equals(vet.getCredenciales().getUsuario()))
      .findFirst()
      .orElse(null);
  }

  @Override
  public Long getVetsCount() {
    return vetsRepo.count();
  }

  @Override
  public Long getVetsInactivosCount() {
    return vetsRepo.countByEstadoFalse();
  }

  @Override
  public Long getVetsActivosCount() {
    return vetsRepo.countByEstadoTrue();
  }

  @Override
  public Long getVetTratamientosCount(Long vetId) {
    return tratamientosRepo.getTratamientosVetCount(vetId);
  }

  @Override
  public List<Pet> getPetsTratadosPorVet(Long vetId) {
    //Si no existe el veterinario, se lanza una excepción
    if (!vetsRepo.existsById(vetId)) {
      throw new VetNotFoundException(vetId);
    }
    return petsRepo.findDistinctByTratamientosVetId(vetId);
  }

  @Override
  public void deleteVet(Long id) {
    Vet vet = vetsRepo.findById(id).orElseThrow(() -> new VetNotFoundException(id));

    List<Pet> petsTratados = petsRepo.findDistinctByTratamientosVetId(id);
    for (Pet pet : petsTratados) {
      pet.getTratamientos().removeIf(tratamiento -> tratamiento.getVet().getId().equals(id));
      petsRepo.save(pet);
    }

    List<Tratamiento> tratamientos = tratamientosRepo.findByVetId(id);
    tratamientosRepo.deleteAll(tratamientos);

    vetsRepo.delete(vet);
  }

  @Override
  public void cambiarEstadoVet(Long id, boolean estado) {
    Vet vet = vetsRepo.findById(id).orElseThrow(() -> new VetNotFoundException(id));
    vet.setEstado(estado);
    vetsRepo.save(vet);
  }

  @Override
  public List<Vet> getVetsFiltrados(VetsFiltrosDto filtros) {
    return vetsRepo.findAll(VetsSpecification.conFiltros(filtros));
  }

  // ------ AGENDA Y CITAS -------
  @Override
  public List<HorarioDia> getHorarioSemanalByVetId(Long vetId) {
    Vet vet = vetsRepo.findById(vetId).orElseThrow(() -> new VetNotFoundException(vetId));

    if (vet.getHorariosDia() == null || vet.getHorariosDia().isEmpty()) {
      return List.of();
    }

    return vet
      .getHorariosDia()
      .stream()
      .sorted(
        Comparator.comparingInt((HorarioDia horario) ->
          getOrdenDiaSemana(horario.getDiaSemana())
        ).thenComparing(horario ->
          horario.getTurno() != null && horario.getTurno().getHoraInicio() != null
            ? horario.getTurno().getHoraInicio()
            : LocalTime.MAX
        )
      )
      .toList();
  }

  private int getOrdenDiaSemana(String diaSemana) {
    if (diaSemana == null) {
      return Integer.MAX_VALUE;
    }

    String diaNormalizado = Normalizer.normalize(diaSemana, Normalizer.Form.NFD)
      .replaceAll("\\p{M}", "")
      .toLowerCase(Locale.ROOT);

    int indice = ORDEN_DIAS_SEMANA.indexOf(diaNormalizado);
    return indice >= 0 ? indice : Integer.MAX_VALUE;
  }

  // ------ AGENDA Y CITAS -------
  public List<CitaDto> getCitasSemanaByVetId(Long vetId, int numSemana) {
    if (!vetsRepo.existsById(vetId)) {
      throw new VetNotFoundException(vetId);
    }

    List<CitaDto> citasDto = new ArrayList<>();
    List<Cita> citas = citasService.getCitasSemanaByVetId(vetId, numSemana);

    for (Cita cita : citas) {
      String diaSemana = formatearDiaSemana(cita.getFechaHora().getDayOfWeek());
      LocalTime hora = cita.getFechaHora().toLocalTime();

      CitaDto citaDto = CitaDto.builder()
        .id(cita.getId())
        .diaSemana(diaSemana)
        .hora(hora)
        .tipoCitaId(cita.getTipoCita().getId())
        .petId(cita.getPet().getId())
        .vetId(cita.getVet().getId())
        .build();

      citasDto.add(citaDto);
    }

    return citasDto;
  }

  private String formatearDiaSemana(DayOfWeek diaSemana) {
    if (diaSemana == null) {
      return null;
    }

    Locale localeEspanol = new Locale("es", "ES");
    String diaFormateado = diaSemana.getDisplayName(TextStyle.FULL, localeEspanol);

    if (diaFormateado.isEmpty()) {
      return diaFormateado;
    }

    return (
      diaFormateado.substring(0, 1).toUpperCase(localeEspanol) +
      diaFormateado.substring(1).toLowerCase(localeEspanol)
    );
  }
}
