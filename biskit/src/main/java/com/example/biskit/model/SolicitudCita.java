package com.example.biskit.model;

public class SolicitudCita {

  private Long tipoCitaId;
  private Long veterinarioId;
  private String horario;
  private Long mascotaId;
  private String telefonoCliente;

  public SolicitudCita() {}

  public SolicitudCita(
    Long tipoCitaId,
    Long veterinarioId,
    String horario,
    Long mascotaId,
    String telefonoCliente
  ) {
    this.tipoCitaId = tipoCitaId;
    this.veterinarioId = veterinarioId;
    this.horario = horario;
    this.mascotaId = mascotaId;
    this.telefonoCliente = telefonoCliente;
  }

  public Long getTipoCitaId() {
    return tipoCitaId;
  }

  public void setTipoCitaId(Long tipoCitaId) {
    this.tipoCitaId = tipoCitaId;
  }

  public Long getVeterinarioId() {
    return veterinarioId;
  }

  public void setVeterinarioId(Long veterinarioId) {
    this.veterinarioId = veterinarioId;
  }

  public String getHorario() {
    return horario;
  }

  public void setHorario(String horario) {
    this.horario = horario;
  }

  public Long getMascotaId() {
    return mascotaId;
  }

  public void setMascotaId(Long mascotaId) {
    this.mascotaId = mascotaId;
  }

  public String getTelefonoCliente() {
    return telefonoCliente;
  }

  public void setTelefonoCliente(String telefonoCliente) {
    this.telefonoCliente = telefonoCliente;
  }
}
