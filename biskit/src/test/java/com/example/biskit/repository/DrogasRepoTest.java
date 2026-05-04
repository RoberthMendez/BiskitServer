package com.example.biskit.repository;

import org.junit.jupiter.api.Test;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.biskit.entities.Droga;
import com.example.biskit.repo.DrogasRepo;

@SuppressWarnings("deprecation")
@DataJpaTest
@RunWith(SpringRunner.class)
public class DrogasRepoTest {

  @Autowired
  private DrogasRepo drogasRepo;

  @BeforeEach
  public void setUp() {

    drogasRepo.save(Droga.builder()
                         .nombre("Droga A")
                         .unidadesVendidas(10)
                         .precioVenta(5L)
                         .unidadesDisponibles(5)
                         .build());

    drogasRepo.save(Droga.builder()
                          .nombre("Droga B")
                          .unidadesVendidas(20)
                          .precioVenta(1L)
                          .unidadesDisponibles(3)
                          .build());


    // Stock Bajo
    drogasRepo.save(Droga.builder()
                          .nombre("Droga C")
                          .unidadesVendidas(5)
                          .precioVenta(6L)
                          .unidadesDisponibles(1)
                          .build());

    drogasRepo.save(Droga.builder()
                          .nombre("Droga D")
                          .unidadesVendidas(0)
                          .precioVenta(100L)
                          .unidadesDisponibles(10)
                          .build());

    // Stock Bajo
    drogasRepo.save(Droga.builder()
                          .nombre("Droga E")
                          .unidadesVendidas(15)
                          .precioVenta(4L)
                          .unidadesDisponibles(0)
                          .build()); 

    // UNIDADES VENDIDAS = 10 + 20 + 5 + 0 + 15 = 50
    // GANANCIAS = (10*5) + (20*1) + (5*6) + (0*100) + (15*4) = 50 + 20 + 30 + 0 + 60 = 160
    // DROGAS BAJO STOCK = Droga C (1), Droga E (0)

  }

  //Realizar 5 pruebas para diferentes consultas creadas por ustedes.​
  @Test
  public void DrogasRepo_getVentasTotales_Long(){

    // Arrange

    // Act
    Long ventasTotales = drogasRepo.getVentasTotales();

    // Assert
    Assertions.assertThat(ventasTotales).isNotNull();
    
    Assertions.assertThat(ventasTotales).isEqualTo(50L);

  }

  //Realizar 5 pruebas para diferentes consultas creadas por ustedes.​
  @Test
  public void DrogasRepo_getGananciasTotales_Long(){

    // Arrange

    // Act
    Long gananciasTotales = drogasRepo.getGananciasTotales();

    // Assert
    Assertions.assertThat(gananciasTotales).isNotNull();

    Assertions.assertThat(gananciasTotales).isEqualTo(160L);

  }

  //Realizar 5 pruebas para diferentes consultas creadas por ustedes.​
  @Test
  public void DrogasRepo_findByStockLessThanEqual_ListDroga(){

    // Arrange

    // Act
    List<Droga> drogasBajoStock = drogasRepo.findByStockLessThanEqual();

    // Assert
    Assertions.assertThat(drogasBajoStock).isNotNull();

    Assertions.assertThat(drogasBajoStock).hasSize(2);

    Assertions.assertThat(drogasBajoStock)
              .extracting(Droga::getNombre)
              .containsExactlyInAnyOrder("Droga C", "Droga E");

  }
  
}
