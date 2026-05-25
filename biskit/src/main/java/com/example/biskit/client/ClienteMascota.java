package com.example.biskit.client;

import com.example.biskit.dto.MascotaDTO;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ClienteMascota {

  @Value("${app.backend-url}")
  private String backendUrl;

  private final RestTemplate restTemplate = new RestTemplate();

  public List<MascotaDTO> obtenerMascotasPorCedula(String cedula) {
    String url = UriComponentsBuilder
      .fromUriString(limpiarBackendUrl())
      .path("/api/mascotas")
      .queryParam("cedula", cedula)
      .encode()
      .toUriString();

    MascotaDTO[] respuesta = restTemplate.getForObject(url, MascotaDTO[].class);
    return respuesta == null ? Collections.emptyList() : Arrays.asList(respuesta);
  }

  private String limpiarBackendUrl() {
    String url = backendUrl == null ? "" : backendUrl.trim();
    while (url.endsWith("/")) {
      url = url.substring(0, url.length() - 1);
    }
    return url;
  }
}
