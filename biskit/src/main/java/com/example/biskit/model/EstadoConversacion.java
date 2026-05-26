package com.example.biskit.model;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

public class EstadoConversacion {

  private PasoConversacion pasoActual;
  private Long tipoCitaIdSeleccionado;
  private Long veterinarioIdSeleccionado;
  private String horarioSeleccionado;
  private Long mascotaIdSeleccionada;
  private String telefonoCliente;
  private String cedulaCliente;
  private Map<String, Long> opcionesTipoCita;
  private Map<String, Long> opcionesVeterinario;
  private Map<String, String> opcionesHorario;
  private Map<String, Long> opcionesMascota;
  private Map<String, String> textosTipoCita;
  private Map<String, String> textosVeterinario;
  private Map<String, String> textosMascota;
  private String tipoCitaTextoSeleccionado;
  private String veterinarioTextoSeleccionado;
  private String mascotaTextoSeleccionado;
  private int semanaHorarios;
  private int paginaTipoCita;
  private int paginaVeterinario;
  private int paginaMascota;
  private LocalDateTime ultimaActividad;

  public EstadoConversacion() {
    this.pasoActual = PasoConversacion.BIENVENIDA;
    this.opcionesTipoCita = new LinkedHashMap<>();
    this.opcionesVeterinario = new LinkedHashMap<>();
    this.opcionesHorario = new LinkedHashMap<>();
    this.opcionesMascota = new LinkedHashMap<>();
    this.textosTipoCita = new LinkedHashMap<>();
    this.textosVeterinario = new LinkedHashMap<>();
    this.textosMascota = new LinkedHashMap<>();
    this.semanaHorarios = 0;
    this.paginaTipoCita = 0;
    this.paginaVeterinario = 0;
    this.paginaMascota = 0;
    this.ultimaActividad = LocalDateTime.now();
  }

  public EstadoConversacion(String telefonoCliente) {
    this();
    this.telefonoCliente = telefonoCliente;
  }

  public void actualizarUltimaActividad() {
    this.ultimaActividad = LocalDateTime.now();
  }

  public PasoConversacion getPasoActual() {
    return pasoActual;
  }

  public void setPasoActual(PasoConversacion pasoActual) {
    this.pasoActual = pasoActual;
  }

  public Long getTipoCitaIdSeleccionado() {
    return tipoCitaIdSeleccionado;
  }

  public void setTipoCitaIdSeleccionado(Long tipoCitaIdSeleccionado) {
    this.tipoCitaIdSeleccionado = tipoCitaIdSeleccionado;
  }

  public Long getVeterinarioIdSeleccionado() {
    return veterinarioIdSeleccionado;
  }

  public void setVeterinarioIdSeleccionado(Long veterinarioIdSeleccionado) {
    this.veterinarioIdSeleccionado = veterinarioIdSeleccionado;
  }

  public String getHorarioSeleccionado() {
    return horarioSeleccionado;
  }

  public void setHorarioSeleccionado(String horarioSeleccionado) {
    this.horarioSeleccionado = horarioSeleccionado;
  }

  public Long getMascotaIdSeleccionada() {
    return mascotaIdSeleccionada;
  }

  public void setMascotaIdSeleccionada(Long mascotaIdSeleccionada) {
    this.mascotaIdSeleccionada = mascotaIdSeleccionada;
  }

  public String getTelefonoCliente() {
    return telefonoCliente;
  }

  public void setTelefonoCliente(String telefonoCliente) {
    this.telefonoCliente = telefonoCliente;
  }

  public String getCedulaCliente() {
    return cedulaCliente;
  }

  public void setCedulaCliente(String cedulaCliente) {
    this.cedulaCliente = cedulaCliente;
  }

  public Map<String, Long> getOpcionesTipoCita() {
    return opcionesTipoCita;
  }

  public void setOpcionesTipoCita(Map<String, Long> opcionesTipoCita) {
    this.opcionesTipoCita = opcionesTipoCita;
  }

  public Map<String, Long> getOpcionesVeterinario() {
    return opcionesVeterinario;
  }

  public void setOpcionesVeterinario(Map<String, Long> opcionesVeterinario) {
    this.opcionesVeterinario = opcionesVeterinario;
  }

  public Map<String, String> getOpcionesHorario() {
    return opcionesHorario;
  }

  public void setOpcionesHorario(Map<String, String> opcionesHorario) {
    this.opcionesHorario = opcionesHorario;
  }

  public Map<String, Long> getOpcionesMascota() {
    return opcionesMascota;
  }

  public void setOpcionesMascota(Map<String, Long> opcionesMascota) {
    this.opcionesMascota = opcionesMascota;
  }

  public Map<String, String> getTextosTipoCita() {
    return textosTipoCita;
  }

  public void setTextosTipoCita(Map<String, String> textosTipoCita) {
    this.textosTipoCita = textosTipoCita;
  }

  public Map<String, String> getTextosVeterinario() {
    return textosVeterinario;
  }

  public void setTextosVeterinario(Map<String, String> textosVeterinario) {
    this.textosVeterinario = textosVeterinario;
  }

  public Map<String, String> getTextosMascota() {
    return textosMascota;
  }

  public void setTextosMascota(Map<String, String> textosMascota) {
    this.textosMascota = textosMascota;
  }

  public String getTipoCitaTextoSeleccionado() {
    return tipoCitaTextoSeleccionado;
  }

  public void setTipoCitaTextoSeleccionado(String tipoCitaTextoSeleccionado) {
    this.tipoCitaTextoSeleccionado = tipoCitaTextoSeleccionado;
  }

  public String getVeterinarioTextoSeleccionado() {
    return veterinarioTextoSeleccionado;
  }

  public void setVeterinarioTextoSeleccionado(String veterinarioTextoSeleccionado) {
    this.veterinarioTextoSeleccionado = veterinarioTextoSeleccionado;
  }

  public String getMascotaTextoSeleccionado() {
    return mascotaTextoSeleccionado;
  }

  public void setMascotaTextoSeleccionado(String mascotaTextoSeleccionado) {
    this.mascotaTextoSeleccionado = mascotaTextoSeleccionado;
  }

  public int getSemanaHorarios() {
    return semanaHorarios;
  }

  public void setSemanaHorarios(int semanaHorarios) {
    this.semanaHorarios = semanaHorarios;
  }

  public int getPaginaTipoCita() {
    return paginaTipoCita;
  }

  public void setPaginaTipoCita(int paginaTipoCita) {
    this.paginaTipoCita = paginaTipoCita;
  }

  public int getPaginaVeterinario() {
    return paginaVeterinario;
  }

  public void setPaginaVeterinario(int paginaVeterinario) {
    this.paginaVeterinario = paginaVeterinario;
  }

  public int getPaginaMascota() {
    return paginaMascota;
  }

  public void setPaginaMascota(int paginaMascota) {
    this.paginaMascota = paginaMascota;
  }

  public LocalDateTime getUltimaActividad() {
    return ultimaActividad;
  }

  public void setUltimaActividad(LocalDateTime ultimaActividad) {
    this.ultimaActividad = ultimaActividad;
  }
}
