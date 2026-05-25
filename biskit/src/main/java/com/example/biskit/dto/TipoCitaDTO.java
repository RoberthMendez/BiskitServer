package com.example.biskit.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TipoCitaDTO {

  private Long id;
  private String nombre;

  public TipoCitaDTO() {}

  public TipoCitaDTO(Long id, String nombre) {
    this.id = id;
    this.nombre = nombre;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
}
