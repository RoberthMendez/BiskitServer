package com.example.biskit.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HorarioDTO {

  private Long id;
  private String fechaHora;
  private String diaSemana;
  private String horaInicio;
  private String horaFin;

  public HorarioDTO() {}

  public HorarioDTO(Long id, String fechaHora) {
    this.id = id;
    this.fechaHora = fechaHora;
  }

  public HorarioDTO(
    Long id,
    String fechaHora,
    String diaSemana,
    String horaInicio,
    String horaFin
  ) {
    this.id = id;
    this.fechaHora = fechaHora;
    this.diaSemana = diaSemana;
    this.horaInicio = horaInicio;
    this.horaFin = horaFin;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFechaHora() {
    return fechaHora;
  }

  public void setFechaHora(String fechaHora) {
    this.fechaHora = fechaHora;
  }

  public String getDiaSemana() {
    return diaSemana;
  }

  public void setDiaSemana(String diaSemana) {
    this.diaSemana = diaSemana;
  }

  public String getHoraInicio() {
    return horaInicio;
  }

  public void setHoraInicio(String horaInicio) {
    this.horaInicio = horaInicio;
  }

  public String getHoraFin() {
    return horaFin;
  }

  public void setHoraFin(String horaFin) {
    this.horaFin = horaFin;
  }
}
