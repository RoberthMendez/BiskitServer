package com.example.biskit.service.Vets;

import com.example.biskit.entities.Citas.Cita;
import com.example.biskit.entities.Citas.HorarioDia;
import com.example.biskit.entities.Citas.Turno;
import com.example.biskit.entities.Credenciales;
import com.example.biskit.entities.DTOs.CitaDto;
import com.example.biskit.entities.DTOs.VetsFiltrosDto;
import com.example.biskit.entities.Pets.Pet;
import com.example.biskit.entities.Tratamiento;
import com.example.biskit.entities.Vets.Especialidad;
import com.example.biskit.entities.Vets.Vet;
import com.example.biskit.errors.NoExiste.VetNoExisteException;
import com.example.biskit.errors.YaExiste.VeterinarioYaExisteException;
import com.example.biskit.repo.TratamientosRepo;
import com.example.biskit.repo.citas.CitasRepo;
import com.example.biskit.repo.citas.HorariosDiaRepo;
import com.example.biskit.repo.pets.PetsRepo;
import com.example.biskit.repo.vets.VetsRepo;
import com.example.biskit.security.CustomUserDetailService;
import com.example.biskit.service.Citas.CitasService;
import com.example.biskit.service.Credenciales.CredencialesService;
import com.example.biskit.specifications.VetsSpecification;
import jakarta.transaction.Transactional;
import java.text.Normalizer;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
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

  private static final List<String> DIAS_SEMANA = List.of(
    "Lunes",
    "Martes",
    "Miercoles",
    "Jueves",
    "Viernes",
    "Sabado",
    "Domingo"
  );

  @Autowired
  private VetsRepo vetsRepo;

  @Autowired
  private PetsRepo petsRepo;

  @Autowired
  private CitasRepo citasRepo;

  @Autowired
  private HorariosDiaRepo horariosDiaRepo;

  @Autowired
  private com.example.biskit.repo.citas.TurnosRepo turnosRepo;

  @Autowired
  private TratamientosRepo tratamientosRepo;

  @Autowired
  private EspecialidadesService especialidadesService;

  @Autowired
  private CredencialesService credencialesService;

  @Autowired
  private CustomUserDetailService userDetailsService;

  @Autowired
  private CitasService citasService;

  @Override
  public List<Vet> getVets() {
    return vetsRepo.findAll();
  }

  @Override
  public Vet getVetById(Long id) {
    return vetsRepo.findById(id).orElseThrow(() -> new VetNoExisteException(id));
  }

  @Override
  public Vet addVet(Vet vet) {
    if (vet.getCorreo() != null && credencialesService.existeUsuario(vet.getCorreo())) {
      throw new VeterinarioYaExisteException(vet.getCorreo());
    }

    if (vet.getId() == null) {
      vet.setEstado(true);
    }

    Credenciales credenciales = userDetailsService.vetToCredenciales(vet);
    credencialesService.addCredenciales(credenciales);
    vet.setCredenciales(credenciales);

    Especialidad especialidad = especialidadesService.getEspecialidadById(
      vet.getEspecialidad().getId()
    );
    vet.setEspecialidad(especialidad);

    Vet vetGuardado = vetsRepo.save(vet);
    asignarHorarioAleatorio(vetGuardado);
    return vetGuardado;
  }

  @Override
  public Vet updateVet(Vet vet) {
    Vet vetExistente = vetsRepo
      .findById(vet.getId())
      .orElseThrow(() -> new VetNoExisteException(vet.getId()));

    vetExistente.setNombre(vet.getNombre());
    vetExistente.setCorreo(vet.getCorreo());
    vetExistente.setCedula(vet.getCedula());
    vetExistente.setEstado(vet.isEstado());
    vetExistente.getCredenciales().setUsername(vet.getCorreo());

    if (vet.getEspecialidad() != null && vet.getEspecialidad().getId() != null) {
      Especialidad especialidad = especialidadesService.getEspecialidadById(
        vet.getEspecialidad().getId()
      );
      vetExistente.setEspecialidad(especialidad);
    }

    return vetsRepo.save(vetExistente);
  }

  @Override
  public Vet saveVet(Vet vet) {
    return vetsRepo.save(vet);
  }

  @Override
  public boolean autenticarVet(String usuario, String contrasena) {
    return vetsRepo
      .findAll()
      .stream()
      .filter(vet -> vet.getCredenciales() != null)
      .anyMatch(
        vet ->
          usuario.equals(vet.getCredenciales().getUsername()) &&
          contrasena.equals(vet.getCredenciales().getPassword())
      );
  }

  @Override
  public Vet findByUsuario(String usuario) {
    return vetsRepo
      .findAll()
      .stream()
      .filter(vet -> vet.getCredenciales() != null)
      .filter(vet -> usuario.equals(vet.getCredenciales().getUsername()))
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
    if (!vetsRepo.existsById(vetId)) {
      throw new VetNoExisteException(vetId);
    }

    return tratamientosRepo.getTratamientosVetCount(vetId);
  }

  @Override
  public List<Pet> getPetsTratadosPorVet(Long vetId) {
    //Si no existe el veterinario, se lanza una excepción
    if (!vetsRepo.existsById(vetId)) {
      throw new VetNoExisteException(vetId);
    }
    return petsRepo.findDistinctByTratamientosVetId(vetId);
  }

  @Override
  public void deleteVet(Long id) {
    Vet vet = vetsRepo.findById(id).orElseThrow(() -> new VetNoExisteException(id));

    List<Pet> petsTratados = petsRepo.findDistinctByTratamientosVetId(id);
    for (Pet pet : petsTratados) {
      pet.getTratamientos().removeIf(tratamiento -> tratamiento.getVet().getId().equals(id));
      petsRepo.save(pet);
    }

    List<Tratamiento> tratamientos = tratamientosRepo.findByVetId(id);
    tratamientosRepo.deleteAll(tratamientos);
    citasRepo.deleteByVetId(id);
    horariosDiaRepo.deleteByVetId(id);

    vetsRepo.delete(vet);
  }

  @Override
  public void cambiarEstadoVet(Long id, boolean estado) {
    Vet vet = vetsRepo.findById(id).orElseThrow(() -> new VetNoExisteException(id));
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
    Vet vet = vetsRepo.findById(vetId).orElseThrow(() -> new VetNoExisteException(vetId));

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

  public List<Tratamiento> getTratamientosVet(Long vetId) {
    if (!vetsRepo.existsById(vetId)) {
      throw new VetNoExisteException(vetId);
    }

    return tratamientosRepo.findByVetId(vetId);
  }

  private void asignarHorarioAleatorio(Vet vet) {
    List<Turno> turnos = turnosRepo.findAll();
    if (turnos.isEmpty()) {
      return;
    }

    Random random = new Random();
    List<String> diasDisponibles = new ArrayList<>(DIAS_SEMANA);
    Collections.shuffle(diasDisponibles, random);
    List<String> diasSeleccionados = diasDisponibles.subList(
      0,
      Math.min(5, diasDisponibles.size())
    );

    HashSet<String> diasAsignados = new HashSet<>();
    for (String diaSemana : diasSeleccionados) {
      if (!diasAsignados.add(diaSemana)) {
        continue;
      }

      Turno turnoAleatorio = turnos.get(random.nextInt(turnos.size()));
      horariosDiaRepo.save(
        HorarioDia.builder().vet(vet).diaSemana(diaSemana).turno(turnoAleatorio).build()
      );
    }
  }

  // ------ AGENDA Y CITAS -------
  public List<CitaDto> getCitasSemanaByVetId(Long vetId, int numSemana) {
    if (!vetsRepo.existsById(vetId)) {
      throw new VetNoExisteException(vetId);
    }

    List<CitaDto> citasDto = new ArrayList<>();
    List<Cita> citas = citasService.getCitasSemanaByVetId(vetId, numSemana);

    for (Cita cita : citas) {
      String diaSemana = formatearDiaSemana(cita.getFechaHora().getDayOfWeek());
      LocalTime hora = cita.getFechaHora().toLocalTime();
      String nombrePet = cita.getPet() != null ? cita.getPet().getNombre() : "Nueva Mascota";
      String ownerNombre =
        cita.getPet() != null && cita.getPet().getOwner() != null
          ? cita.getPet().getOwner().getNombre()
          : "-";

      CitaDto citaDto = CitaDto.builder()
        .id(cita.getId())
        .diaSemana(diaSemana)
        .hora(hora)
        .tipoCitaNombre(cita.getTipoCita().getNombre())
        .duracionMinutos(cita.getTipoCita().getDuracionMinutos())
        .petNombre(nombrePet)
        .ownerNombre(ownerNombre)
        .build();

      citasDto.add(citaDto);
    }

    return citasDto;
  }

  public List<CitaDto> getCitasSemanaByVetIdSinMascota(Long vetId, int numSemana) {
    if (!vetsRepo.existsById(vetId)) {
      throw new VetNoExisteException(vetId);
    }

    List<CitaDto> citasDto = new ArrayList<>();
    List<Cita> citas = citasService.getCitasSemanaByVetIdSinMascota(vetId, numSemana);

    for (Cita cita : citas) {
      String diaSemana = formatearDiaSemana(cita.getFechaHora().getDayOfWeek());
      LocalTime hora = cita.getFechaHora().toLocalTime();

      CitaDto citaDto = CitaDto.builder()
        .id(cita.getId())
        .diaSemana(diaSemana)
        .hora(hora)
        .tipoCitaNombre(cita.getTipoCita().getNombre())
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
