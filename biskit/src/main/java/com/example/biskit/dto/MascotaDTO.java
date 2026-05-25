package com.example.biskit.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MascotaDTO {

  private Long id;
  private String nombre;
  private String especie;

  public MascotaDTO() {}

  public MascotaDTO(Long id, String nombre, String especie) {
    this.id = id;
    this.nombre = nombre;
    this.especie = especie;
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

  public String getEspecie() {
    return especie;
  }

  public void setEspecie(String especie) {
    this.especie = especie;
  }
}
