package com.example.biskit.controller;

import com.example.biskit.dto.HorarioDTO;
import com.example.biskit.dto.MascotaDTO;
import com.example.biskit.dto.TipoCitaDTO;
import com.example.biskit.dto.VeterinarioDTO;
import com.example.biskit.entities.Citas.Cita;
import com.example.biskit.entities.Citas.HorarioDia;
import com.example.biskit.entities.Citas.TipoCita;
import com.example.biskit.entities.Citas.Turno;
import com.example.biskit.entities.Client;
import com.example.biskit.entities.DTOs.CitaDto;
import com.example.biskit.entities.Pets.Pet;
import com.example.biskit.entities.Vets.Vet;
import com.example.biskit.model.SolicitudCita;
import com.example.biskit.repo.ClientsRepo;
import com.example.biskit.repo.citas.CitasRepo;
import com.example.biskit.repo.citas.HorariosDiaRepo;
import com.example.biskit.repo.citas.TiposCitaRepo;
import com.example.biskit.repo.pets.PetsRepo;
import com.example.biskit.repo.vets.VetsRepo;
import com.example.biskit.service.Citas.CitasService;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiWhatsAppController {

  private static final DateTimeFormatter FORMATO_HORARIO = DateTimeFormatter.ofPattern(
    "yyyy-MM-dd HH:mm"
  );
  private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern(
    "h:mm a",
    Locale.US
  );

  private final TiposCitaRepo tiposCitaRepo;
  private final VetsRepo vetsRepo;
  private final HorariosDiaRepo horariosDiaRepo;
  private final CitasRepo citasRepo;
  private final PetsRepo petsRepo;
  private final ClientsRepo clientsRepo;
  private final CitasService citasService;

  public ApiWhatsAppController(
    TiposCitaRepo tiposCitaRepo,
    VetsRepo vetsRepo,
    HorariosDiaRepo horariosDiaRepo,
    CitasRepo citasRepo,
    PetsRepo petsRepo,
    ClientsRepo clientsRepo,
    CitasService citasService
  ) {
    this.tiposCitaRepo = tiposCitaRepo;
    this.vetsRepo = vetsRepo;
    this.horariosDiaRepo = horariosDiaRepo;
    this.citasRepo = citasRepo;
    this.petsRepo = petsRepo;
    this.clientsRepo = clientsRepo;
    this.citasService = citasService;
  }

  @GetMapping("/tipos-cita")
  public ResponseEntity<List<TipoCitaDTO>> obtenerTiposCita() {
    List<TipoCitaDTO> tiposCita = tiposCitaRepo
      .findAll()
      .stream()
      .map(tipoCita -> new TipoCitaDTO(tipoCita.getId(), tipoCita.getNombre()))
      .toList();

    return ResponseEntity.ok(tiposCita);
  }

  @GetMapping("/veterinarios")
  public ResponseEntity<List<VeterinarioDTO>> obtenerVeterinarios() {
    List<VeterinarioDTO> veterinarios = vetsRepo
      .findAll()
      .stream()
      .filter(Vet::isEstado)
      .map(veterinario -> new VeterinarioDTO(veterinario.getId(), veterinario.getNombre()))
      .toList();

    return ResponseEntity.ok(veterinarios);
  }

  @GetMapping("/horarios")
  public ResponseEntity<List<HorarioDTO>> obtenerHorarios(
    @RequestParam Long veterinarioId,
    @RequestParam Long tipoCitaId,
    @RequestParam(defaultValue = "0") int semana
  ) {
    TipoCita tipoCita = tiposCitaRepo.findById(tipoCitaId).orElseThrow();
    vetsRepo.findById(veterinarioId).orElseThrow();

    List<HorarioDia> horariosSemana = horariosDiaRepo.findByVetId(veterinarioId);
    List<HorarioDTO> horariosDisponibles = new ArrayList<>();
    long idHorario = 1L;

    int semanaNormalizada = Math.max(semana, 0);
    LocalDate fechaInicio = LocalDate.now().plusDays(1 + (semanaNormalizada * 7L));
    for (int diasAdelante = 0; diasAdelante < 7; diasAdelante++) {
      LocalDate fecha = fechaInicio.plusDays(diasAdelante);
      String diaSemana = obtenerDiaSemana(fecha.getDayOfWeek());

      for (HorarioDia horarioDia : horariosSemana) {
        if (!diaSemana.equalsIgnoreCase(horarioDia.getDiaSemana())) {
          continue;
        }

        Turno turno = horarioDia.getTurno();
        if (turno == null || turno.getHoraInicio() == null || turno.getHoraFin() == null) {
          continue;
        }

        idHorario = agregarHorariosDelDia(
          horariosDisponibles,
          idHorario,
          veterinarioId,
          fecha,
          turno,
          tipoCita.getDuracionMinutos()
        );
      }
    }

    return ResponseEntity.ok(horariosDisponibles);
  }

  @GetMapping("/mascotas")
  public ResponseEntity<List<MascotaDTO>> obtenerMascotasPorCedula(@RequestParam String cedula) {
    String cedulaNormalizada = normalizarCedula(cedula);

    List<Long> idsClientes = clientsRepo
      .findAll()
      .stream()
      .filter(cliente -> cedulaNormalizada.equals(normalizarCedula(cliente.getCedula())))
      .map(Client::getId)
      .toList();

    if (idsClientes.isEmpty()) {
      return ResponseEntity.ok(List.of());
    }

    List<MascotaDTO> mascotas = petsRepo
      .findByOwnerIdIn(idsClientes)
      .stream()
      .map(this::convertirMascota)
      .toList();

    return ResponseEntity.ok(mascotas);
  }

  @PostMapping("/citas")
  public ResponseEntity<Void> crearCita(@RequestBody SolicitudCita solicitudCita) {
    TipoCita tipoCita = tiposCitaRepo.findById(solicitudCita.getTipoCitaId()).orElseThrow();
    LocalDateTime fechaHora = parsearHorario(solicitudCita.getHorario());

    CitaDto citaDto = CitaDto.builder()
      .tipoCitaNombre(tipoCita.getNombre())
      .vetId(solicitudCita.getVeterinarioId())
      .petId(solicitudCita.getMascotaId())
      .diaSemana(obtenerDiaSemana(fechaHora.getDayOfWeek()))
      .hora(fechaHora.toLocalTime())
      .build();

    int numSemana = obtenerNumeroSemana(fechaHora.toLocalDate());
    citasService.addCita(citaDto, numSemana);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  private long agregarHorariosDelDia(
    List<HorarioDTO> horariosDisponibles,
    long idHorario,
    Long veterinarioId,
    LocalDate fecha,
    Turno turno,
    Integer duracionMinutos
  ) {
    if (duracionMinutos == null || duracionMinutos <= 0) {
      return idHorario;
    }

    LocalDateTime inicioDia = fecha.atStartOfDay();
    LocalDateTime finDia = fecha.atTime(LocalTime.MAX);
    List<Cita> citasDelDia = citasRepo.findByVetIdAndFechaHoraBetweenOrderByFechaHoraAsc(
      veterinarioId,
      inicioDia,
      finDia
    );

    LocalTime hora = turno.getHoraInicio();
    while (!hora.plusMinutes(duracionMinutos).isAfter(turno.getHoraFin())) {
      LocalDateTime fechaHora = LocalDateTime.of(fecha, hora);
      LocalTime horaFin = hora.plusMinutes(duracionMinutos);
      if (
        fechaHora.isAfter(LocalDateTime.now()) &&
        estaDisponible(fechaHora, duracionMinutos, citasDelDia)
      ) {
        horariosDisponibles.add(
          new HorarioDTO(
            idHorario++,
            fechaHora.format(FORMATO_HORARIO),
            obtenerDiaSemana(fecha.getDayOfWeek()),
            hora.format(FORMATO_HORA),
            horaFin.format(FORMATO_HORA)
          )
        );
      }
      hora = horaFin;
    }

    return idHorario;
  }

  private boolean estaDisponible(
    LocalDateTime fechaHora,
    Integer duracionMinutos,
    List<Cita> citasDelDia
  ) {
    LocalTime horaInicio = fechaHora.toLocalTime();
    LocalTime horaFin = horaInicio.plusMinutes(duracionMinutos);

    for (Cita cita : citasDelDia) {
      Integer duracionExistente = cita.getTipoCita().getDuracionMinutos();
      LocalTime inicioExistente = cita.getFechaHora().toLocalTime();
      LocalTime finExistente = inicioExistente.plusMinutes(duracionExistente);

      if (horaInicio.isBefore(finExistente) && horaFin.isAfter(inicioExistente)) {
        return false;
      }
    }

    return true;
  }

  private MascotaDTO convertirMascota(Pet mascota) {
    String especie = null;
    if (mascota.getRaza() != null && mascota.getRaza().getEspecie() != null) {
      especie = mascota.getRaza().getEspecie().getNombre();
    }
    return new MascotaDTO(mascota.getId(), mascota.getNombre(), especie);
  }

  private LocalDateTime parsearHorario(String horario) {
    try {
      return LocalDateTime.parse(horario, FORMATO_HORARIO);
    } catch (DateTimeParseException ex) {
      return LocalDateTime.parse(horario);
    }
  }

  private int obtenerNumeroSemana(LocalDate fecha) {
    LocalDate inicioSemanaActual = LocalDate.now().with(
      TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)
    );
    LocalDate inicioSemanaCita = fecha.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    return Math.toIntExact(ChronoUnit.WEEKS.between(inicioSemanaActual, inicioSemanaCita));
  }

  private String obtenerDiaSemana(DayOfWeek diaSemana) {
    return switch (diaSemana) {
      case MONDAY -> "Lunes";
      case TUESDAY -> "Martes";
      case WEDNESDAY -> "Miercoles";
      case THURSDAY -> "Jueves";
      case FRIDAY -> "Viernes";
      case SATURDAY -> "Sabado";
      case SUNDAY -> "Domingo";
    };
  }

  private String normalizarCedula(String cedula) {
    if (cedula == null) {
      return "";
    }

    return cedula.replaceAll("\\D", "").replaceFirst("^0+(?!$)", "");
  }
}
