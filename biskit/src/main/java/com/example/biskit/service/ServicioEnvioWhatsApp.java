package com.example.biskit.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ServicioEnvioWhatsApp {

  @Value("${whatsapp.access-token}")
  private String accessToken;

  @Value("${whatsapp.api-url}")
  private String apiUrl;

  private final RestTemplate restTemplate = new RestTemplate();

  public void enviarMensaje(String para, String mensaje) {
    Map<String, Object> texto = new LinkedHashMap<>();
    texto.put("preview_url", false);
    texto.put("body", mensaje);

    Map<String, Object> cuerpoPeticion = crearBasePeticion(para, "text");
    cuerpoPeticion.put("text", texto);

    enviarPeticion(cuerpoPeticion);
  }

  public void enviarBotones(String para, String mensaje, Map<String, String> botones) {
    if (botones == null || botones.isEmpty()) {
      enviarMensaje(para, mensaje);
      return;
    }

    List<Map<String, Object>> botonesRespuesta = botones
      .entrySet()
      .stream()
      .limit(3)
      .map(entrada -> {
        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("id", limitarTexto(entrada.getKey(), 200));
        respuesta.put("title", limitarTexto(entrada.getValue(), 20));

        Map<String, Object> boton = new LinkedHashMap<>();
        boton.put("type", "reply");
        boton.put("reply", respuesta);
        return boton;
      })
      .collect(Collectors.toList());

    Map<String, Object> cuerpo = new LinkedHashMap<>();
    cuerpo.put("text", limitarTexto(mensaje, 1024));

    Map<String, Object> accion = new LinkedHashMap<>();
    accion.put("buttons", botonesRespuesta);

    Map<String, Object> interactivo = new LinkedHashMap<>();
    interactivo.put("type", "button");
    interactivo.put("body", cuerpo);
    interactivo.put("action", accion);

    Map<String, Object> cuerpoPeticion = crearBasePeticion(para, "interactive");
    cuerpoPeticion.put("interactive", interactivo);

    enviarPeticion(cuerpoPeticion);
  }

  public void enviarLista(
    String para,
    String mensaje,
    String textoBoton,
    String tituloSeccion,
    Map<String, String> opciones
  ) {
    if (opciones == null || opciones.isEmpty()) {
      enviarMensaje(para, mensaje);
      return;
    }

    List<Map<String, Object>> filas = opciones
      .entrySet()
      .stream()
      .limit(10)
      .map(entrada -> {
        Map<String, Object> fila = new LinkedHashMap<>();
        fila.put("id", limitarTexto(entrada.getKey(), 200));
        fila.put("title", limitarTexto(entrada.getValue(), 24));
        return fila;
      })
      .collect(Collectors.toList());

    Map<String, Object> seccion = new LinkedHashMap<>();
    seccion.put("title", limitarTexto(tituloSeccion, 24));
    seccion.put("rows", filas);

    Map<String, Object> cuerpo = new LinkedHashMap<>();
    cuerpo.put("text", limitarTexto(mensaje, 1024));

    Map<String, Object> accion = new LinkedHashMap<>();
    accion.put("button", limitarTexto(textoBoton, 20));
    accion.put("sections", List.of(seccion));

    Map<String, Object> interactivo = new LinkedHashMap<>();
    interactivo.put("type", "list");
    interactivo.put("body", cuerpo);
    interactivo.put("action", accion);

    Map<String, Object> cuerpoPeticion = crearBasePeticion(para, "interactive");
    cuerpoPeticion.put("interactive", interactivo);

    enviarPeticion(cuerpoPeticion);
  }

  private Map<String, Object> crearBasePeticion(String para, String tipo) {
    Map<String, Object> cuerpoPeticion = new LinkedHashMap<>();
    cuerpoPeticion.put("messaging_product", "whatsapp");
    cuerpoPeticion.put("to", para);
    cuerpoPeticion.put("type", tipo);
    return cuerpoPeticion;
  }

  private void enviarPeticion(Map<String, Object> cuerpoPeticion) {
    HttpHeaders encabezados = new HttpHeaders();
    encabezados.setContentType(MediaType.APPLICATION_JSON);
    encabezados.setBearerAuth(accessToken);

    HttpEntity<Map<String, Object>> entidad = new HttpEntity<>(cuerpoPeticion, encabezados);
    ResponseEntity<String> respuesta = restTemplate.postForEntity(apiUrl, entidad, String.class);

    if (!respuesta.getStatusCode().is2xxSuccessful()) {
      throw new IllegalStateException("No se pudo enviar el mensaje de WhatsApp");
    }
  }

  private String limitarTexto(String texto, int longitudMaxima) {
    if (texto == null) {
      return "";
    }

    String valor = texto.trim();
    if (valor.length() <= longitudMaxima) {
      return valor;
    }

    return valor.substring(0, longitudMaxima);
  }
}
