package com.example.biskit.repository;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.biskit.entities.Tratamiento;
import com.example.biskit.entities.vets.Vet;
import com.example.biskit.repo.TratamientosRepo;
import com.example.biskit.repo.vets.VetsRepo;

@SuppressWarnings("deprecation")
@DataJpaTest
@RunWith(SpringRunner.class)
public class TratamientosRepoTest {

  @Autowired
  private TratamientosRepo tratamientosRepo;

  @Autowired
  private VetsRepo vetsRepo;
  
  private Vet vet;

  @BeforeEach
  public void setUp() {
    
    this.vet = vetsRepo.save(Vet.builder()
                      .nombre("Dr. Juan Pérez")
                      .estado(true)
                      .correo("juan.perez@biskit.com")
                      .cedula("12345678")
                      .build());

    tratamientosRepo.save(Tratamiento.builder()
                                     .fecha(LocalDate.of(2024, 5, 1))
                                     .vet(vet)
                                     .build());
    
    tratamientosRepo.save(Tratamiento.builder()
                                     .fecha(LocalDate.of(2024, 5, 2))
                                     .vet(vet)
                                     .build());
    
    tratamientosRepo.save(Tratamiento.builder()
                                     .fecha(LocalDate.of(2024, 5, 3))
                                     .vet(vet)
                                     .build());

    tratamientosRepo.save(Tratamiento.builder()
                                     .fecha(LocalDate.of(2024, 6, 3))
                                     .vet(vet)
                                     .build());

    tratamientosRepo.save(Tratamiento.builder()
                                     .fecha(LocalDate.of(2025, 6, 3))
                                     .vet(vet)
                                     .build());

    // CANTIDAD TRATAMIENTOS REALIZADOS EL 5TO MES DEL 2024 = 3
    // CANTIDAD DE TRATAMIENTOS HECHOS POR EL VETERINARIO 1 = 5


  }

  // Realizar 5 pruebas para diferentes consultas creadas por ustedes.​
  @Test
  public void TratamientosRepo_getNumTratamientosMes_Long(){

    // Arrange

    // Act
    Long numTratamientos = tratamientosRepo.getNumTratamientosMes(2024, 5);

    // Assert
    Assertions.assertThat(numTratamientos).isNotNull();

    Assertions.assertThat(numTratamientos).isEqualTo(3L);

  }

  // Realizar 5 pruebas para diferentes consultas creadas por ustedes.​
  @Test
  public void TratamientosRepo_getTratamientosVetCount_Long(){

    // Arrange

    // Act
    Long numTratamientos = tratamientosRepo.getTratamientosVetCount(this.vet.getId());

    // Assert
    Assertions.assertThat(numTratamientos).isNotNull();

    Assertions.assertThat(numTratamientos).isEqualTo(5L);

  }
  
}
