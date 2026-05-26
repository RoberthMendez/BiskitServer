package com.example.biskit.client;

import com.example.biskit.model.SolicitudCita;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ClienteCita {

  @Value("${app.backend-url}")
  private String backendUrl;

  private final RestTemplate restTemplate = new RestTemplate();

  public void crearCita(SolicitudCita solicitudCita) {
    ResponseEntity<String> respuesta = restTemplate.postForEntity(
      construirUrl("/api/citas"),
      solicitudCita,
      String.class
    );

    if (!respuesta.getStatusCode().is2xxSuccessful()) {
      throw new IllegalStateException("No se pudo crear la cita");
    }
  }

  private String construirUrl(String ruta) {
    return UriComponentsBuilder.fromUriString(limpiarBackendUrl()).path(ruta).toUriString();
  }

  private String limpiarBackendUrl() {
    String url = backendUrl == null ? "" : backendUrl.trim();
    while (url.endsWith("/")) {
      url = url.substring(0, url.length() - 1);
    }
    return url;
  }
}
