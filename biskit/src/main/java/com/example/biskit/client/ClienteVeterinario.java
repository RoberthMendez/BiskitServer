package com.example.biskit.client;

import com.example.biskit.dto.VeterinarioDTO;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ClienteVeterinario {

  @Value("${app.backend-url}")
  private String backendUrl;

  private final RestTemplate restTemplate = new RestTemplate();

  public List<VeterinarioDTO> obtenerVeterinarios() {
    VeterinarioDTO[] respuesta = restTemplate.getForObject(
      construirUrl("/api/veterinarios"),
      VeterinarioDTO[].class
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
