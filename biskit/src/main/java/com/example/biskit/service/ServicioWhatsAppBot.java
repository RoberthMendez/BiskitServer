package com.example.biskit.service;

import com.example.biskit.client.ClienteCita;
import com.example.biskit.client.ClienteHorario;
import com.example.biskit.client.ClienteMascota;
import com.example.biskit.client.ClienteTipoCita;
import com.example.biskit.client.ClienteVeterinario;
import com.example.biskit.dto.HorarioDTO;
import com.example.biskit.dto.MascotaDTO;
import com.example.biskit.dto.TipoCitaDTO;
import com.example.biskit.dto.VeterinarioDTO;
import com.example.biskit.model.EstadoConversacion;
import com.example.biskit.model.PasoConversacion;
import com.example.biskit.model.SolicitudCita;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PreDestroy;
import java.text.Normalizer;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ServicioWhatsAppBot {

  private static final Logger logger = LoggerFactory.getLogger(ServicioWhatsAppBot.class);
  private static final int ELEMENTOS_POR_PAGINA_LISTA = 7;

  private final ServicioEstadoConversacion servicioEstadoConversacion;
  private final ServicioEnvioWhatsApp servicioEnvioWhatsApp;
  private final ClienteTipoCita clienteTipoCita;
  private final ClienteVeterinario clienteVeterinario;
  private final ClienteHorario clienteHorario;
  private final ClienteMascota clienteMascota;
  private final ClienteCita clienteCita;
  private final ObjectMapper objectMapper;
  private final ExecutorService ejecutorWebhook = Executors.newFixedThreadPool(4);

  public ServicioWhatsAppBot(
    ServicioEstadoConversacion servicioEstadoConversacion,
    ServicioEnvioWhatsApp servicioEnvioWhatsApp,
    ClienteTipoCita clienteTipoCita,
    ClienteVeterinario clienteVeterinario,
    ClienteHorario clienteHorario,
    ClienteMascota clienteMascota,
    ClienteCita clienteCita,
    ObjectMapper objectMapper
  ) {
    this.servicioEstadoConversacion = servicioEstadoConversacion;
    this.servicioEnvioWhatsApp = servicioEnvioWhatsApp;
    this.clienteTipoCita = clienteTipoCita;
    this.clienteVeterinario = clienteVeterinario;
    this.clienteHorario = clienteHorario;
    this.clienteMascota = clienteMascota;
    this.clienteCita = clienteCita;
    this.objectMapper = objectMapper;
  }

  public void procesarPayloadAsync(String payload) {
    ejecutorWebhook.submit(() -> procesarPayload(payload));
  }

  private void procesarPayload(String payload) {
    try {
      JsonNode raiz = objectMapper.readTree(payload);
      for (JsonNode entrada : raiz.path("entry")) {
        for (JsonNode cambio : entrada.path("changes")) {
          JsonNode valor = cambio.path("value");
          for (JsonNode mensaje : valor.path("messages")) {
            String telefono = mensaje.path("from").asText(null);
            if (telefono == null || telefono.isBlank()) {
              continue;
            }

            String tipoMensaje = mensaje.path("type").asText("");
            String texto = "";
            if ("text".equals(tipoMensaje)) {
              texto = mensaje.path("text").path("body").asText("");
            } else if ("interactive".equals(tipoMensaje)) {
              texto = extraerRespuestaInteractiva(mensaje);
            }

            procesarMensajeEntrante(telefono, texto);
          }
        }
      }
    } catch (Exception ex) {
      logger.error("No se pudo procesar el payload de WhatsApp", ex);
    }
  }

  private String extraerRespuestaInteractiva(JsonNode mensaje) {
    JsonNode interactivo = mensaje.path("interactive");
    String tipoInteractivo = interactivo.path("type").asText("");

    if ("button_reply".equals(tipoInteractivo)) {
      return interactivo.path("button_reply").path("id").asText("");
    }

    if ("list_reply".equals(tipoInteractivo)) {
      return interactivo.path("list_reply").path("id").asText("");
    }

    return "";
  }

  private void procesarMensajeEntrante(String telefono, String textoRecibido) {
    EstadoConversacion estado = servicioEstadoConversacion.obtener(telefono);
    if (estado == null || servicioEstadoConversacion.estaExpirada(estado)) {
      estado = servicioEstadoConversacion.crearNueva(telefono);
      logger.info("Iniciando conversacion de WhatsApp para {}", enmascararTelefono(telefono));
      enviarBienvenida(estado);
      return;
    }

    synchronized (estado) {
      estado.actualizarUltimaActividad();
      String comando = normalizarComando(textoRecibido);
      logger.info(
        "Procesando conversacion de WhatsApp para {} en paso {}",
        enmascararTelefono(telefono),
        estado.getPasoActual()
      );

      if (esAtras(comando) && permiteAtras(estado.getPasoActual())) {
        manejarAtras(estado);
        return;
      }

      switch (estado.getPasoActual()) {
        case BIENVENIDA -> manejarBienvenida(estado, comando);
        case CONFIRMAR_REINICIO, REINTENTO_CREAR_CITA -> manejarConfirmarReinicio(estado, comando);
        case TIPO_CITA -> manejarTipoCita(estado, comando);
        case VETERINARIO -> manejarVeterinario(estado, comando);
        case HORARIO -> manejarHorario(estado, comando);
        case VERIFICACION_MASCOTA -> manejarVerificacionMascota(estado, comando);
        case CEDULA_CLIENTE -> manejarCedulaCliente(estado, textoRecibido);
        case SELECCION_MASCOTA -> manejarSeleccionMascota(estado, comando);
        default -> enviarBienvenida(estado);
      }
    }
  }

  private void manejarBienvenida(EstadoConversacion estado, String comando) {
    if (esSi(comando)) {
      mostrarTiposCita(estado);
      return;
    }

    if (esNo(comando)) {
      estado.setPasoActual(PasoConversacion.CONFIRMAR_REINICIO);
      enviarBotonesSeguro(
        estado.getTelefonoCliente(),
        "De acuerdo, no agendaremos una cita por ahora. ¿Quieres intentarlo de nuevo?",
        botonesSiNo()
      );
      return;
    }

    enviarBotonesSeguro(
      estado.getTelefonoCliente(),
      "Para ayudarte a agendar una cita, selecciona una opción.",
      botonesSiNo()
    );
  }

  private void manejarConfirmarReinicio(EstadoConversacion estado, String comando) {
    if (esSi(comando)) {
      limpiarFlujoCompleto(estado);
      enviarBienvenida(estado);
      return;
    }

    if (esNo(comando)) {
      enviarMensajeSeguro(
        estado.getTelefonoCliente(),
        "Gracias por escribir a Biskit Veterinaria. Cuando necesites una cita, estaremos listos para ayudarte."
      );
      servicioEstadoConversacion.eliminar(estado.getTelefonoCliente());
      return;
    }

    enviarBotonesSeguro(
      estado.getTelefonoCliente(),
      "No entendi tu respuesta. Selecciona si para intentarlo de nuevo o no para terminar.",
      botonesSiNo()
    );
  }

  private void manejarTipoCita(EstadoConversacion estado, String comando) {
    if (esVerSiguientes(comando)) {
      estado.setPaginaTipoCita(estado.getPaginaTipoCita() + 1);
      mostrarTiposCita(estado);
      return;
    }

    if (esVerAnteriores(comando)) {
      estado.setPaginaTipoCita(Math.max(estado.getPaginaTipoCita() - 1, 0));
      mostrarTiposCita(estado);
      return;
    }

    Long tipoCitaId = estado.getOpcionesTipoCita().get(comando);
    if (tipoCitaId == null) {
      enviarOpcionInvalida(estado);
      mostrarTiposCita(estado);
      return;
    }

    estado.setTipoCitaIdSeleccionado(tipoCitaId);
    estado.setTipoCitaTextoSeleccionado(estado.getTextosTipoCita().get(comando));
    limpiarDesdeVeterinario(estado);
    estado.setPaginaVeterinario(0);
    mostrarVeterinarios(estado);
  }

  private void manejarVeterinario(EstadoConversacion estado, String comando) {
    if (esVerSiguientes(comando)) {
      estado.setPaginaVeterinario(estado.getPaginaVeterinario() + 1);
      mostrarVeterinarios(estado);
      return;
    }

    if (esVerAnteriores(comando)) {
      estado.setPaginaVeterinario(Math.max(estado.getPaginaVeterinario() - 1, 0));
      mostrarVeterinarios(estado);
      return;
    }

    Long veterinarioId = estado.getOpcionesVeterinario().get(comando);
    if (veterinarioId == null) {
      enviarOpcionInvalida(estado);
      mostrarVeterinarios(estado);
      return;
    }

    estado.setVeterinarioIdSeleccionado(veterinarioId);
    estado.setVeterinarioTextoSeleccionado(estado.getTextosVeterinario().get(comando));
    limpiarDesdeHorario(estado);
    estado.setSemanaHorarios(0);
    mostrarHorarios(estado);
  }

  private void manejarHorario(EstadoConversacion estado, String comando) {
    if (esSiguienteSemana(comando)) {
      estado.setSemanaHorarios(estado.getSemanaHorarios() + 1);
      mostrarHorarios(estado);
      return;
    }

    String horario = estado.getOpcionesHorario().get(comando);
    if (horario == null) {
      enviarMensajeSeguro(
        estado.getTelefonoCliente(),
        "No reconoci esa opcion. Escribe el numero del horario, siguiente semana para ver mas opciones o atras para volver."
      );
      mostrarHorarios(estado);
      return;
    }

    estado.setHorarioSeleccionado(horario);
    limpiarMascota(estado);
    estado.setPasoActual(PasoConversacion.VERIFICACION_MASCOTA);
    enviarBotonesSeguro(
      estado.getTelefonoCliente(),
      "¿Tu mascota ya se encuentra registrada en nuestro sistema? 🐶😺",
      botonesSiNoAtras()
    );
  }

  private void manejarVerificacionMascota(EstadoConversacion estado, String comando) {
    if (esNo(comando)) {
      estado.setMascotaIdSeleccionada(null);
      estado.setMascotaTextoSeleccionado(null);
      crearCita(estado);
      return;
    }

    if (esSi(comando)) {
      estado.setPasoActual(PasoConversacion.CEDULA_CLIENTE);
      estado.setPaginaMascota(0);
      enviarBotonesSeguro(
        estado.getTelefonoCliente(),
        "Perfecto! Entonces escribe tu *número de cédula* para buscar tus mascotas registradas 🪪",
        botonesAtras()
      );
      return;
    }

    enviarBotonesSeguro(
      estado.getTelefonoCliente(),
      "No entendí tu respuesta. ¿Tu mascota ya se encuentra registrada en nuestro sistema? 🐶😺",
      botonesSiNoAtras()
    );
  }

  private void manejarCedulaCliente(EstadoConversacion estado, String textoRecibido) {
    String cedula = textoRecibido == null ? "" : textoRecibido.trim();
    if (cedula.isBlank()) {
      enviarBotonesSeguro(
        estado.getTelefonoCliente(),
        "Por favor escribe tu número de cedula.",
        botonesAtras()
      );
      return;
    }

    estado.setCedulaCliente(cedula);

    try {
      List<MascotaDTO> mascotas = clienteMascota.obtenerMascotasPorCedula(cedula);
      if (mascotas.isEmpty()) {
        enviarMensajeSeguro(
          estado.getTelefonoCliente(),
          "No encontré mascotas registradas con tu cédula. Continuaré creando la cita sin mascota asociada."
        );
        estado.setMascotaIdSeleccionada(null);
        estado.setMascotaTextoSeleccionado(null);
        crearCita(estado);
        return;
      }

      mostrarMascotas(estado, mascotas);
    } catch (Exception ex) {
      logger.error("No se pudieron consultar mascotas por cédula", ex);
      preguntarReintento(
        estado,
        "No pude consultar las mascotas registradas en este momento. ¿Quieres intentarlo de nuevo?"
      );
    }
  }

  private void manejarSeleccionMascota(EstadoConversacion estado, String comando) {
    if (esVerSiguientes(comando)) {
      estado.setPaginaMascota(estado.getPaginaMascota() + 1);
      manejarCedulaCliente(estado, estado.getCedulaCliente());
      return;
    }

    if (esVerAnteriores(comando)) {
      estado.setPaginaMascota(Math.max(estado.getPaginaMascota() - 1, 0));
      manejarCedulaCliente(estado, estado.getCedulaCliente());
      return;
    }

    Long mascotaId = estado.getOpcionesMascota().get(comando);
    if (mascotaId == null) {
      enviarOpcionInvalida(estado);
      estado.setPasoActual(PasoConversacion.CEDULA_CLIENTE);
      manejarCedulaCliente(estado, estado.getCedulaCliente());
      return;
    }

    estado.setMascotaIdSeleccionada(mascotaId);
    estado.setMascotaTextoSeleccionado(estado.getTextosMascota().get(comando));
    crearCita(estado);
  }

  private void mostrarTiposCita(EstadoConversacion estado) {
    try {
      List<TipoCitaDTO> tiposCita = clienteTipoCita.obtenerTiposCita();
      Map<String, Long> opciones = new LinkedHashMap<>();
      Map<String, String> textos = new LinkedHashMap<>();
      Map<String, String> filas = new LinkedHashMap<>();

      List<TipoCitaDTO> tiposValidos = tiposCita
        .stream()
        .filter(tipoCita -> tipoCita.getId() != null)
        .toList();

      int pagina = paginaNormalizada(estado.getPaginaTipoCita(), tiposValidos.size());
      estado.setPaginaTipoCita(pagina);
      int inicio = pagina * ELEMENTOS_POR_PAGINA_LISTA;
      int fin = Math.min(inicio + ELEMENTOS_POR_PAGINA_LISTA, tiposValidos.size());

      int indice = 1;
      for (TipoCitaDTO tipoCita : tiposValidos.subList(inicio, fin)) {
        if (tipoCita.getId() == null) {
          continue;
        }
        String clave = String.valueOf(indice++);
        String texto = textoDisponible(tipoCita.getNombre(), "Tipo de cita " + clave);
        opciones.put(clave, tipoCita.getId());
        textos.put(clave, texto);
        filas.put(clave, texto);
      }

      if (opciones.isEmpty()) {
        preguntarReintento(
          estado,
          "No hay tipos de cita disponibles en este momento. ¿Quieres intentarlo de nuevo?"
        );
        return;
      }

      boolean tieneSiguiente = fin < tiposValidos.size();
      estado.setOpcionesTipoCita(opciones);
      estado.setTextosTipoCita(textos);
      estado.setPasoActual(PasoConversacion.TIPO_CITA);
      enviarListaSeguro(
        estado.getTelefonoCliente(),
        "Selecciona el *tipo de cita* que deseas programar 👇🏼",
        "Ver Tipos de Cita",
        "Tipos de cita",
        filas
      );
      enviarBotonesNavegacionLista(
        estado.getTelefonoCliente(),
        "Otras opciones:",
        pagina,
        tieneSiguiente,
        true,
        "Ver mas tipos cita"
      );
    } catch (Exception ex) {
      logger.error("No se pudieron cargar los tipos de cita", ex);
      preguntarReintento(
        estado,
        "No pude cargar los tipos de cita en este momento. ¿Quieres intentarlo de nuevo?"
      );
    }
  }

  private void mostrarVeterinarios(EstadoConversacion estado) {
    try {
      List<VeterinarioDTO> veterinarios = clienteVeterinario.obtenerVeterinarios();
      Map<String, Long> opciones = new LinkedHashMap<>();
      Map<String, String> textos = new LinkedHashMap<>();
      Map<String, String> filas = new LinkedHashMap<>();

      List<VeterinarioDTO> veterinariosValidos = veterinarios
        .stream()
        .filter(veterinario -> veterinario.getId() != null)
        .toList();

      int pagina = paginaNormalizada(estado.getPaginaVeterinario(), veterinariosValidos.size());
      estado.setPaginaVeterinario(pagina);
      int inicio = pagina * ELEMENTOS_POR_PAGINA_LISTA;
      int fin = Math.min(inicio + ELEMENTOS_POR_PAGINA_LISTA, veterinariosValidos.size());

      int indice = 1;
      for (VeterinarioDTO veterinario : veterinariosValidos.subList(inicio, fin)) {
        if (veterinario.getId() == null) {
          continue;
        }
        String clave = String.valueOf(indice++);
        String texto = textoDisponible(veterinario.getNombre(), "Veterinario " + clave);
        opciones.put(clave, veterinario.getId());
        textos.put(clave, texto);
        filas.put(clave, texto);
      }

      if (opciones.isEmpty()) {
        preguntarReintento(
          estado,
          "No hay veterinarios disponibles en este momento. ¿Quieres intentarlo de nuevo?"
        );
        return;
      }

      boolean tieneSiguiente = fin < veterinariosValidos.size();
      estado.setOpcionesVeterinario(opciones);
      estado.setTextosVeterinario(textos);
      estado.setPasoActual(PasoConversacion.VETERINARIO);
      enviarListaSeguro(
        estado.getTelefonoCliente(),
        "Selecciona el *veterinario* con el que deseas agendar 👇🏼",
        "Ver Veterinarios",
        "Veterinarios",
        filas
      );
      enviarBotonesNavegacionLista(
        estado.getTelefonoCliente(),
        "Otras opciones:",
        pagina,
        tieneSiguiente,
        true,
        "Ver mas veterinarios"
      );
    } catch (Exception ex) {
      logger.error("No se pudieron cargar los veterinarios", ex);
      preguntarReintento(
        estado,
        "No pude cargar los veterinarios en este momento. ¿Quieres intentarlo de nuevo?"
      );
    }
  }

  private void mostrarHorarios(EstadoConversacion estado) {
    try {
      List<HorarioDTO> horarios = clienteHorario.obtenerHorarios(
        estado.getVeterinarioIdSeleccionado(),
        estado.getTipoCitaIdSeleccionado(),
        estado.getSemanaHorarios()
      );

      Map<String, String> opciones = new LinkedHashMap<>();
      StringBuilder mensaje = new StringBuilder(
        "Estos son los *horarios disponibles* del veterinario seleccionado:\n\n"
      );

      int indice = 1;
      String diaActual = null;
      for (HorarioDTO horario : horarios) {
        String fechaHora = textoDisponible(horario.getFechaHora(), null);
        if (fechaHora == null) {
          continue;
        }
        String clave = String.valueOf(indice++);
        opciones.put(clave, fechaHora);

        String diaSemana = textoDisponible(horario.getDiaSemana(), "Horarios").toUpperCase(
          Locale.ROOT
        );
        if (!diaSemana.equals(diaActual)) {
          if (diaActual != null) {
            mensaje.append("\n");
          }
          mensaje.append("*").append(diaSemana).append("*\n");
          diaActual = diaSemana;
        }

        mensaje
          .append("*")
          .append(clave)
          .append("*. ")
          .append(describirHorario(horario))
          .append("\n");
      }

      if (opciones.isEmpty()) {
        estado.setOpcionesHorario(opciones);
        estado.setPasoActual(PasoConversacion.HORARIO);
        enviarBotonesSeguro(
          estado.getTelefonoCliente(),
          "No hay horarios disponibles en esta ventana de 7 días.",
          botonesHorarios()
        );
        return;
      }

      mensaje.append("\nResponde este mensaje con el *número* del horario que deseas agendar 📅");
      estado.setOpcionesHorario(opciones);
      estado.setPasoActual(PasoConversacion.HORARIO);
      enviarMensajeSeguro(estado.getTelefonoCliente(), mensaje.toString());
      enviarBotonesSeguro(
        estado.getTelefonoCliente(),
        "También puedes usar estas opciones.",
        botonesHorarios()
      );
    } catch (Exception ex) {
      logger.error("No se pudieron cargar los horarios", ex);
      preguntarReintento(
        estado,
        "No pude cargar los horarios disponibles en este momento. ¿Quieres intentarlo de nuevo?"
      );
    }
  }

  private void mostrarMascotas(EstadoConversacion estado, List<MascotaDTO> mascotas) {
    Map<String, Long> opciones = new LinkedHashMap<>();
    Map<String, String> textos = new LinkedHashMap<>();
    Map<String, String> filas = new LinkedHashMap<>();

    List<MascotaDTO> mascotasValidas = mascotas
      .stream()
      .filter(mascota -> mascota.getId() != null)
      .toList();

    int pagina = paginaNormalizada(estado.getPaginaMascota(), mascotasValidas.size());
    estado.setPaginaMascota(pagina);
    int inicio = pagina * ELEMENTOS_POR_PAGINA_LISTA;
    int fin = Math.min(inicio + ELEMENTOS_POR_PAGINA_LISTA, mascotasValidas.size());

    int indice = 1;
    for (MascotaDTO mascota : mascotasValidas.subList(inicio, fin)) {
      if (mascota.getId() == null) {
        continue;
      }
      String clave = String.valueOf(indice++);
      String texto = describirMascota(mascota, clave);
      opciones.put(clave, mascota.getId());
      textos.put(clave, texto);
      filas.put(clave, texto);
    }

    if (opciones.isEmpty()) {
      enviarMensajeSeguro(
        estado.getTelefonoCliente(),
        "No encontre mascotas validas con esa cedula. Continuare creando la cita sin mascota asociada."
      );
      estado.setMascotaIdSeleccionada(null);
      estado.setMascotaTextoSeleccionado(null);
      crearCita(estado);
      return;
    }

    agregarControlesLista(filas, pagina, fin < mascotasValidas.size(), true);
    estado.setOpcionesMascota(opciones);
    estado.setTextosMascota(textos);
    estado.setPasoActual(PasoConversacion.SELECCION_MASCOTA);
    enviarListaSeguro(
      estado.getTelefonoCliente(),
      "Ahora selecciona para cual de *tus mascotas* deseas agendar la cita 👇🏼",
      "Ver mascotas",
      "Mascotas",
      filas
    );
  }

  private void crearCita(EstadoConversacion estado) {
    SolicitudCita solicitudCita = new SolicitudCita(
      estado.getTipoCitaIdSeleccionado(),
      estado.getVeterinarioIdSeleccionado(),
      estado.getHorarioSeleccionado(),
      estado.getMascotaIdSeleccionada(),
      estado.getTelefonoCliente()
    );

    try {
      clienteCita.crearCita(solicitudCita);
      logger.info(
        "Cita creada desde WhatsApp para {}",
        enmascararTelefono(estado.getTelefonoCliente())
      );
      enviarMensajeSeguro(estado.getTelefonoCliente(), construirResumenConfirmacion(estado));
      servicioEstadoConversacion.eliminar(estado.getTelefonoCliente());
    } catch (Exception ex) {
      logger.error("No se pudo crear la cita", ex);
      preguntarReintento(
        estado,
        "No pude crear la cita en este momento. ¿Quieres intentarlo de nuevo?"
      );
    }
  }

  private void manejarAtras(EstadoConversacion estado) {
    PasoConversacion pasoActual = estado.getPasoActual();
    if (pasoActual == PasoConversacion.TIPO_CITA) {
      limpiarFlujoCompleto(estado);
      enviarBienvenida(estado);
      return;
    }

    if (pasoActual == PasoConversacion.VETERINARIO) {
      limpiarDesdeTipoCita(estado);
      mostrarTiposCita(estado);
      return;
    }

    if (pasoActual == PasoConversacion.HORARIO) {
      limpiarDesdeVeterinario(estado);
      mostrarVeterinarios(estado);
      return;
    }

    limpiarMascota(estado);
    mostrarHorarios(estado);
  }

  private void enviarBienvenida(EstadoConversacion estado) {
    estado.setPasoActual(PasoConversacion.BIENVENIDA);
    enviarBotonesSeguro(
      estado.getTelefonoCliente(),
      "Hola! Te has contactado con *Biskit Veterinaria* 🐾\n¿Deseas agendar una cita para tu mascota? 🐶😸",
      botonesSiNo()
    );
  }

  private void preguntarReintento(EstadoConversacion estado, String mensaje) {
    estado.setPasoActual(PasoConversacion.REINTENTO_CREAR_CITA);
    enviarBotonesSeguro(
      estado.getTelefonoCliente(),
      mensaje + " Selecciona *Si* para empezar de nuevo, selecciona *No* para terminar.",
      botonesSiNo()
    );
  }

  private void enviarOpcionInvalida(EstadoConversacion estado) {
    enviarMensajeSeguro(
      estado.getTelefonoCliente(),
      "No reconocí esa opción. Por favor escribe solo el número de la lista, o atrás para volver."
    );
  }

  private String construirResumenConfirmacion(EstadoConversacion estado) {
    String tipoCita = textoDisponible(
      estado.getTipoCitaTextoSeleccionado(),
      "ID " + estado.getTipoCitaIdSeleccionado()
    );
    String veterinario = textoDisponible(
      estado.getVeterinarioTextoSeleccionado(),
      "ID " + estado.getVeterinarioIdSeleccionado()
    );
    String mascota =
      estado.getMascotaIdSeleccionada() == null
        ? "Sin mascota asociada"
        : textoDisponible(
            estado.getMascotaTextoSeleccionado(),
            "ID " + estado.getMascotaIdSeleccionada()
          );

    return (
      "Listo! Tu cita quedó agendada. 📆\n\n" +
      "*RESUMEN*:\n" +
      "* *Tipo de cita:* " +
      tipoCita +
      "\n* *Veterinario:* " +
      veterinario +
      "\n* *Horario:* " +
      estado.getHorarioSeleccionado() +
      "\n* *Mascota:* " +
      mascota +
      "\n* *Teléfono*: " +
      estado.getTelefonoCliente() +
      "\n\nGracias por confiar en *Biskit Veterinaria* 😊"
    );
  }

  private boolean permiteAtras(PasoConversacion pasoActual) {
    return (
      pasoActual == PasoConversacion.TIPO_CITA ||
      pasoActual == PasoConversacion.VETERINARIO ||
      pasoActual == PasoConversacion.HORARIO ||
      pasoActual == PasoConversacion.VERIFICACION_MASCOTA ||
      pasoActual == PasoConversacion.CEDULA_CLIENTE ||
      pasoActual == PasoConversacion.SELECCION_MASCOTA
    );
  }

  private boolean esSi(String comando) {
    return "si".equals(comando) || "s".equals(comando);
  }

  private boolean esNo(String comando) {
    return "no".equals(comando) || "n".equals(comando);
  }

  private boolean esAtras(String comando) {
    return "atras".equals(comando) || "volver".equals(comando);
  }

  private boolean esSiguienteSemana(String comando) {
    return (
      "siguiente_semana".equals(comando) ||
      "siguiente semana".equals(comando) ||
      "semana siguiente".equals(comando) ||
      "siguiente".equals(comando) ||
      "otra semana".equals(comando) ||
      "ver mas".equals(comando) ||
      "mas horarios".equals(comando)
    );
  }

  private boolean esVerSiguientes(String comando) {
    return (
      "ver_siguientes".equals(comando) ||
      "ver siguientes".equals(comando) ||
      "siguientes".equals(comando)
    );
  }

  private boolean esVerAnteriores(String comando) {
    return (
      "ver_anteriores".equals(comando) ||
      "ver anteriores".equals(comando) ||
      "anteriores".equals(comando)
    );
  }

  private String normalizarComando(String texto) {
    String valor = texto == null ? "" : texto.trim();
    String sinAcentos = Normalizer.normalize(valor, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
    return sinAcentos.toLowerCase(Locale.ROOT).replaceAll("\\s+", " ");
  }

  private String textoDisponible(String texto, String textoRespaldo) {
    if (texto == null || texto.isBlank()) {
      return textoRespaldo;
    }
    return texto.trim();
  }

  private String describirMascota(MascotaDTO mascota, String clave) {
    String nombre = textoDisponible(mascota.getNombre(), "Mascota " + clave);
    String especie = textoDisponible(mascota.getEspecie(), null);
    return especie == null ? nombre : nombre + " - " + especie;
  }

  private String describirHorario(HorarioDTO horario) {
    String horaInicio = textoDisponible(horario.getHoraInicio(), null);
    String horaFin = textoDisponible(horario.getHoraFin(), null);

    if (horaInicio == null || horaFin == null) {
      return textoDisponible(horario.getFechaHora(), "Horario disponible");
    }

    return "De " + horaInicio + " a " + horaFin;
  }

  private int paginaNormalizada(int paginaSolicitada, int totalOpciones) {
    if (totalOpciones <= 0) {
      return 0;
    }

    int ultimaPagina = (totalOpciones - 1) / ELEMENTOS_POR_PAGINA_LISTA;
    return Math.max(0, Math.min(paginaSolicitada, ultimaPagina));
  }

  private void agregarControlesLista(
    Map<String, String> filas,
    int pagina,
    boolean tieneSiguiente,
    boolean incluirAtras
  ) {
    if (pagina > 0) {
      filas.put("ver_anteriores", "Ver anteriores");
    }

    if (tieneSiguiente) {
      filas.put("ver_siguientes", "Ver siguientes");
    }

    if (incluirAtras) {
      filas.put("atras", "Atras");
    }
  }

  private void enviarBotonesNavegacionLista(
    String telefono,
    String mensaje,
    int pagina,
    boolean tieneSiguiente,
    boolean incluirAtras,
    String textoSiguiente
  ) {
    Map<String, String> botones = new LinkedHashMap<>();

    if (pagina > 0) {
      botones.put("ver_anteriores", "Ver anteriores");
    }

    if (tieneSiguiente) {
      botones.put("ver_siguientes", textoSiguiente);
    }

    if (incluirAtras) {
      botones.put("atras", "Atras");
    }

    if (!botones.isEmpty()) {
      enviarBotonesSeguro(telefono, mensaje, botones);
    }
  }

  private Map<String, String> botonesSiNo() {
    Map<String, String> botones = new LinkedHashMap<>();
    botones.put("si", "Si");
    botones.put("no", "No");
    return botones;
  }

  private Map<String, String> botonesSiNoAtras() {
    Map<String, String> botones = new LinkedHashMap<>();
    botones.put("si", "Si");
    botones.put("no", "No");
    botones.put("atras", "Atras");
    return botones;
  }

  private Map<String, String> botonesAtras() {
    Map<String, String> botones = new LinkedHashMap<>();
    botones.put("atras", "Atras");
    return botones;
  }

  private Map<String, String> botonesHorarios() {
    Map<String, String> botones = new LinkedHashMap<>();
    botones.put("siguiente_semana", "Siguiente semana");
    botones.put("atras", "Atras");
    return botones;
  }

  private void enviarBotonesSeguro(String para, String mensaje, Map<String, String> botones) {
    try {
      servicioEnvioWhatsApp.enviarBotones(para, mensaje, botones);
    } catch (Exception ex) {
      logger.error("No se pudo enviar mensaje interactivo de WhatsApp", ex);
      enviarMensajeSeguro(para, mensaje);
    }
  }

  private void enviarListaSeguro(
    String para,
    String mensaje,
    String textoBoton,
    String tituloSeccion,
    Map<String, String> opciones
  ) {
    try {
      servicioEnvioWhatsApp.enviarLista(para, mensaje, textoBoton, tituloSeccion, opciones);
    } catch (Exception ex) {
      logger.error("No se pudo enviar lista interactiva de WhatsApp", ex);
      enviarMensajeSeguro(para, construirMensajeTextoLista(mensaje, opciones));
    }
  }

  private String construirMensajeTextoLista(String mensaje, Map<String, String> opciones) {
    StringBuilder constructor = new StringBuilder(mensaje).append("\n");
    for (Map.Entry<String, String> opcion : opciones.entrySet()) {
      constructor.append(opcion.getKey()).append(". ").append(opcion.getValue()).append("\n");
    }
    return constructor.toString();
  }

  private String enmascararTelefono(String telefono) {
    if (telefono == null || telefono.length() <= 4) {
      return "****";
    }
    return "****" + telefono.substring(telefono.length() - 4);
  }

  private void limpiarFlujoCompleto(EstadoConversacion estado) {
    limpiarDesdeTipoCita(estado);
    estado.setPaginaTipoCita(0);
    estado.setOpcionesTipoCita(new LinkedHashMap<>());
    estado.setTextosTipoCita(new LinkedHashMap<>());
  }

  private void limpiarDesdeTipoCita(EstadoConversacion estado) {
    estado.setTipoCitaIdSeleccionado(null);
    estado.setTipoCitaTextoSeleccionado(null);
    limpiarDesdeVeterinario(estado);
  }

  private void limpiarDesdeVeterinario(EstadoConversacion estado) {
    estado.setVeterinarioIdSeleccionado(null);
    estado.setVeterinarioTextoSeleccionado(null);
    estado.setPaginaVeterinario(0);
    estado.setOpcionesVeterinario(new LinkedHashMap<>());
    estado.setTextosVeterinario(new LinkedHashMap<>());
    limpiarDesdeHorario(estado);
  }

  private void limpiarDesdeHorario(EstadoConversacion estado) {
    estado.setHorarioSeleccionado(null);
    estado.setSemanaHorarios(0);
    estado.setOpcionesHorario(new LinkedHashMap<>());
    limpiarMascota(estado);
  }

  private void limpiarMascota(EstadoConversacion estado) {
    estado.setMascotaIdSeleccionada(null);
    estado.setMascotaTextoSeleccionado(null);
    estado.setCedulaCliente(null);
    estado.setPaginaMascota(0);
    estado.setOpcionesMascota(new LinkedHashMap<>());
    estado.setTextosMascota(new LinkedHashMap<>());
  }

  private void enviarMensajeSeguro(String para, String mensaje) {
    try {
      servicioEnvioWhatsApp.enviarMensaje(para, mensaje);
    } catch (Exception ex) {
      logger.error("No se pudo enviar mensaje de WhatsApp", ex);
    }
  }

  @PreDestroy
  public void detenerEjecutor() {
    ejecutorWebhook.shutdownNow();
  }
}
