package com.example.biskit.service;

import com.example.biskit.model.EstadoConversacion;
import jakarta.annotation.PreDestroy;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Service;

@Service
public class ServicioEstadoConversacion {

  private static final Duration DURACION_SESION = Duration.ofMinutes(30);

  private final ConcurrentHashMap<String, EstadoConversacion> conversaciones =
    new ConcurrentHashMap<>();
  private final ScheduledExecutorService programadorExpiracion =
    Executors.newSingleThreadScheduledExecutor();

  public ServicioEstadoConversacion() {
    programadorExpiracion.scheduleAtFixedRate(
      this::eliminarConversacionesExpiradas,
      5,
      5,
      TimeUnit.MINUTES
    );
  }

  public EstadoConversacion obtener(String telefono) {
    return conversaciones.get(telefono);
  }

  public EstadoConversacion crearNueva(String telefono) {
    EstadoConversacion estado = new EstadoConversacion(telefono);
    conversaciones.put(telefono, estado);
    return estado;
  }

  public void eliminar(String telefono) {
    conversaciones.remove(telefono);
  }

  public boolean estaExpirada(EstadoConversacion estado) {
    if (estado == null || estado.getUltimaActividad() == null) {
      return true;
    }
    return estado.getUltimaActividad().plus(DURACION_SESION).isBefore(LocalDateTime.now());
  }

  private void eliminarConversacionesExpiradas() {
    conversaciones.entrySet().removeIf(entrada -> estaExpirada(entrada.getValue()));
  }

  @PreDestroy
  public void detenerProgramador() {
    programadorExpiracion.shutdownNow();
  }
}
