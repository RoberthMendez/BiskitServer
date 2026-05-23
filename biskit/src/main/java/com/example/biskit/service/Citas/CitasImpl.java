package com.example.biskit.service.Citas;

import com.example.biskit.entities.Citas.Cita;
import com.example.biskit.entities.Citas.HorarioDia;
import com.example.biskit.entities.Citas.TipoCita;
import com.example.biskit.entities.DTOs.CitaDTO;
import com.example.biskit.entities.Pets.Pet;
import com.example.biskit.entities.Vets.Vet;
import com.example.biskit.errors.VeterinarioNoDisponibleException;
import com.example.biskit.repo.citas.CitasRepo;
import com.example.biskit.repo.citas.HorariosDiaRepo;
import com.example.biskit.repo.citas.TiposCitaRepo;
import com.example.biskit.repo.pets.PetsRepo;
import com.example.biskit.repo.vets.VetsRepo;
import jakarta.transaction.Transactional;
import java.text.Normalizer;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CitasImpl implements CitasService {

  @Autowired
  private CitasRepo citasRepo;

  @Autowired
  private TiposCitaRepo tiposCitaRepo;

  @Autowired
  private PetsRepo petsRepo;

  @Autowired
  private VetsRepo vetsRepo;

  @Autowired
  private HorariosDiaRepo horariosDiaRepo;

  public void addCitaDataLoader(Cita cita) {
    validarDisponibilidadVet(cita, null);
    citasRepo.save(cita);
  }

  public Cita addCita(CitaDTO citaDto, int numSemana) {
    TipoCita tipoCita = tiposCitaRepo.findByNombre(citaDto.getTipoCitaNombre()).orElseThrow();
    Vet vet = vetsRepo.findById(citaDto.getVetId()).orElseThrow();

    LocalDate hoy = LocalDate.now();
    DayOfWeek diaObjetivo = parseDiaSemanaDesdeEspanol(citaDto.getDiaSemana());
    LocalDate inicioSemana = hoy
      .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
      .plusWeeks(numSemana);
    LocalDate fechaCita = inicioSemana.plusDays(diaObjetivo.getValue() - 1);
    LocalDateTime fechaHora = LocalDateTime.of(fechaCita, citaDto.getHora());

    Cita cita = new Cita();
    cita.setTipoCita(tipoCita);

    if (citaDto.getPetId() != null) {
      cita.setPet(petsRepo.findById(citaDto.getPetId()).orElseThrow());
    } else {
      cita.setPet(null);
    }

    cita.setVet(vet);
    cita.setFechaHora(fechaHora);

    validarDisponibilidadVet(cita, null);
    return citasRepo.save(cita);
  }

  private DayOfWeek parseDiaSemanaDesdeEspanol(String dia) {
    if (dia == null) {
      throw new IllegalArgumentException("diaSemana es null");
    }
    String normalized = Normalizer.normalize(dia, Normalizer.Form.NFD)
      .replaceAll("\\p{M}", "")
      .toLowerCase()
      .trim();

    switch (normalized) {
      case "lunes":
        return DayOfWeek.MONDAY;
      case "martes":
        return DayOfWeek.TUESDAY;
      case "miercoles":
      case "miércoles":
        return DayOfWeek.WEDNESDAY;
      case "jueves":
        return DayOfWeek.THURSDAY;
      case "viernes":
        return DayOfWeek.FRIDAY;
      case "sabado":
      case "sábado":
        return DayOfWeek.SATURDAY;
      case "domingo":
        return DayOfWeek.SUNDAY;
      default:
        throw new IllegalArgumentException("Dia de semana no reconocido: " + dia);
    }
  }

  private void validarDisponibilidadVet(Cita cita, Long citaIdIgnorar) {
    LocalDateTime fechaHora = cita.getFechaHora();
    Long vetId = cita.getVet().getId();
    Integer duracionMinutos = cita.getTipoCita().getDuracionMinutos();

    // 1. Obtener el día de la semana en español (nombre completo)
    String diaSemana = getDiaSemanaEnEspanol(fechaHora.getDayOfWeek());

    // 2. Buscar si el veterinario tiene horario para ese día
    Optional<HorarioDia> horarioDiaOpt = horariosDiaRepo.findByVetIdAndDiaSemana(vetId, diaSemana);
    if (!horarioDiaOpt.isPresent()) {
      throw new VeterinarioNoDisponibleException(diaSemana, true);
    }

    HorarioDia horarioDia = horarioDiaOpt.get();
    LocalTime horaInicio = horarioDia.getTurno().getHoraInicio();
    LocalTime horaFin = horarioDia.getTurno().getHoraFin();
    LocalTime horaCita = fechaHora.toLocalTime();
    LocalTime horaFinCita = horaCita.plusMinutes(duracionMinutos);

    // 3. Verificar que la cita esté dentro del horario del turno
    if (horaCita.isBefore(horaInicio) || horaFinCita.isAfter(horaFin)) {
      throw new VeterinarioNoDisponibleException(horaInicio, horaFin);
    }

    // 4. Buscar citas existentes del veterinario en ese día
    LocalDate fecha = fechaHora.toLocalDate();
    LocalDateTime inicioDia = fecha.atStartOfDay();
    LocalDateTime finDia = fecha.atTime(LocalTime.MAX);
    List<Cita> citasDelDia = citasRepo.findByVetIdAndFechaHoraBetweenOrderByFechaHoraAsc(
      vetId,
      inicioDia,
      finDia
    );

    // 5. Verificar conflictos con otras citas
    for (Cita citaExistente : citasDelDia) {
      if (citaIdIgnorar != null && citaIdIgnorar.equals(citaExistente.getId())) {
        continue;
      }

      LocalTime horaExistente = citaExistente.getFechaHora().toLocalTime();
      Integer duracionExistente = citaExistente.getTipoCita().getDuracionMinutos();
      LocalTime horaFinExistente = horaExistente.plusMinutes(duracionExistente);

      // Verificar si hay sobreposición
      // Conflicto si: horaCita < horaFinExistente AND horaFinCita > horaExistente
      if (horaCita.isBefore(horaFinExistente) && horaFinCita.isAfter(horaExistente)) {
        throw new VeterinarioNoDisponibleException(
          "El veterinario se encuentra ocupado en el horario solicitado"
        );
      }
    }
  }

  /**
   * Convierte el DayOfWeek a su nombre en español
   */
  private String getDiaSemanaEnEspanol(DayOfWeek dayOfWeek) {
    switch (dayOfWeek) {
      case MONDAY:
        return "Lunes";
      case TUESDAY:
        return "Martes";
      case WEDNESDAY:
        return "Miercoles";
      case THURSDAY:
        return "Jueves";
      case FRIDAY:
        return "Viernes";
      case SATURDAY:
        return "Sabado";
      case SUNDAY:
        return "Domingo";
      default:
        return "";
    }
  }

  public List<Cita> getCitasSemanaByVetId(Long vetId, int numSemana) {
    LocalDate hoy = LocalDate.now();
    LocalDate inicioSemana = hoy.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    inicioSemana = inicioSemana.plusWeeks(numSemana);
    LocalDate finSemana = inicioSemana.plusDays(6);
    LocalDateTime inicioSemanaDateTime = inicioSemana.atStartOfDay();
    LocalDateTime finSemanaDateTime = finSemana.atTime(LocalTime.MAX);

    return citasRepo.findByVetIdAndFechaHoraBetweenOrderByFechaHoraAsc(
      vetId,
      inicioSemanaDateTime,
      finSemanaDateTime
    );
  }

  public List<Cita> getCitasSemanaByVetIdSinMascota(Long vetId, int numSemana) {
    LocalDate hoy = LocalDate.now();
    LocalDate inicioSemana = hoy.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    inicioSemana = inicioSemana.plusWeeks(numSemana);
    LocalDate finSemana = inicioSemana.plusDays(6);
    LocalDateTime inicioSemanaDateTime = inicioSemana.atStartOfDay();
    LocalDateTime finSemanaDateTime = finSemana.atTime(LocalTime.MAX);

    return citasRepo.findByVetIdAndFechaHoraBetweenAndPetIsNullOrderByFechaHoraAsc(
      vetId,
      inicioSemanaDateTime,
      finSemanaDateTime
    );
  }

  public Cita updateCita(Long id, CitaDTO citaDto, int numSemana) {
    Cita citaExistente = citasRepo
      .findById(id)
      .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
    TipoCita tipoCita = tiposCitaRepo
      .findByNombre(citaDto.getTipoCitaNombre())
      .orElseThrow(() -> new RuntimeException("Tipo de cita no encontrado"));
    Pet pet = petsRepo
      .findById(citaDto.getPetId())
      .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));
    Vet vet = vetsRepo
      .findById(citaDto.getVetId())
      .orElseThrow(() -> new RuntimeException("Veterinario no encontrado"));

    LocalDate hoy = LocalDate.now();
    DayOfWeek diaObjetivo = parseDiaSemanaDesdeEspanol(citaDto.getDiaSemana());
    LocalDate inicioSemana = hoy
      .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
      .plusWeeks(numSemana);
    LocalDate fechaCita = inicioSemana.plusDays(diaObjetivo.getValue() - 1);
    LocalDateTime fechaHora = LocalDateTime.of(fechaCita, citaDto.getHora());

    citaExistente.setTipoCita(tipoCita);
    citaExistente.setPet(pet);
    citaExistente.setVet(vet);
    citaExistente.setFechaHora(fechaHora);

    validarDisponibilidadVet(citaExistente, citaExistente.getId());
    return citasRepo.save(citaExistente);
  }

  public void deleteCita(Long id) {
    Cita cita = citasRepo
      .findById(id)
      .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
    citasRepo.delete(cita);
  }

  @Scheduled(cron = "0 */5 * * * *")
  public void eliminarCitasPendientes() {
    citasRepo.deleteByPetIdIsNull();
    System.out.println("Citas sin mascota eliminadas: " + LocalDateTime.now());
  }
}
