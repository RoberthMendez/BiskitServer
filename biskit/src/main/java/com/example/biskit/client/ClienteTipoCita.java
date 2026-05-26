package com.example.biskit.client;

import com.example.biskit.dto.TipoCitaDTO;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ClienteTipoCita {

  @Value("${app.backend-url}")
  private String backendUrl;

  private final RestTemplate restTemplate = new RestTemplate();

  public List<TipoCitaDTO> obtenerTiposCita() {
    TipoCitaDTO[] respuesta = restTemplate.getForObject(
      construirUrl("/api/tipos-cita"),
      TipoCitaDTO[].class
    );
    return respuesta == null ? Collections.emptyList() : Arrays.asList(respuesta);
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
