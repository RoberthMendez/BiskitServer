package com.example.biskit.service.Vets;

import com.example.biskit.entities.Citas.HorarioDia;
import com.example.biskit.entities.DTOs.CitaDTO;
import com.example.biskit.entities.DTOs.VetsFiltrosDTO;
import com.example.biskit.entities.Pets.Pet;
import com.example.biskit.entities.Tratamiento;
import com.example.biskit.entities.Vets.Vet;
import java.util.List;

public interface VetService {
  public List<Vet> getVets();

  public Vet getVetById(Long id);

  public Vet addVet(Vet vet);

  public Vet saveVet(Vet vet);

  public Vet updateVet(Vet vet);

  public boolean autenticarVet(String usuario, String contrasena);

  public Vet findByUsuario(String usuario);

  public Long getVetsCount();

  public Long getVetsInactivosCount();

  public Long getVetsActivosCount();

  public Long getVetTratamientosCount(Long vetId);

  public List<Pet> getPetsTratadosPorVet(Long vetId);

  public void deleteVet(Long id);

  public void cambiarEstadoVet(Long id, boolean estado);

  public List<Vet> getVetsFiltrados(VetsFiltrosDTO filtros);

  public List<Tratamiento> getTratamientosVet(Long vetId);

  // ------ AGENDA Y CITAS -------
  public List<HorarioDia> getHorarioSemanalByVetId(Long vetId);

  public List<CitaDTO> getCitasSemanaByVetId(Long vetId, int numSemana);

  public List<CitaDTO> getCitasSemanaByVetIdSinMascota(Long vetId, int numSemana);
}
