package com.example.biskit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.example.biskit.service.Tratamientos.TratamientosService;
import com.example.biskit.entities.Credenciales;
import com.example.biskit.entities.Droga;
import com.example.biskit.entities.Tratamiento;
import com.example.biskit.service.Pets.PetsService;
import com.example.biskit.service.Vets.VetService;
import com.example.biskit.service.Vets.EspecialidadesService;
import com.example.biskit.service.Tratamientos.DrogasService;
import com.example.biskit.entities.pets.Pet;
import com.example.biskit.entities.vets.Especialidad;
import com.example.biskit.entities.vets.Vet;
import com.example.biskit.errors.MascotaInactivaException;
import com.example.biskit.errors.StockInsuficienteException;
import com.example.biskit.entities.dtos.DrogaTratamientoCountDto;
import com.example.biskit.entities.dtos.TratamientoDto;
import com.example.biskit.entities.dtos.TratamientosMesDto;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class TratamientoServiceTestNaive {

    @Autowired
    private TratamientosService tratamientoService;

    @Autowired
    private DrogasService drogasService;

    @Autowired
    private PetsService petService;

    @Autowired
    private VetService vetService;

    @Autowired
    private EspecialidadesService especialidadesService;

    @BeforeEach
    public void setUp() {

        Calendar cal = Calendar.getInstance();
        cal.set(2020, Calendar.MAY, 10);

        Especialidad esp1 = Especialidad.builder().nombre("Cirugía").build();
        especialidadesService.addEspecialidad(esp1);

        //Drogas
        Droga droga1 = Droga.builder().nombre("Droga A").unidadesDisponibles(3).build();
        Droga droga2 = Droga.builder().nombre("Droga B").unidadesDisponibles(5).build();
        drogasService.saveDroga(droga1);
        drogasService.saveDroga(droga2);

        // Pets
        Pet pet1 = Pet.builder().nombre("Firulais").fechaNacimiento(new java.sql.Date(cal.getTimeInMillis())).estado(true).build();
        petService.addPet(pet1);
        Pet pet2 = Pet.builder().nombre("Michi").fechaNacimiento(new java.sql.Date(cal.getTimeInMillis())).estado(true).build();
        petService.addPet(pet2);
        
        // Vets
        Vet vet1 = Vet.builder().nombre("Dr. Smith").correo("smith@example.com").cedula("12345").credenciales(new Credenciales()).especialidad(esp1).build();
        vetService.addVet(vet1);
        Vet vet2 = Vet.builder().nombre("Dra. Johnson").correo("johnson@example.com").cedula("67890").credenciales(new Credenciales()).especialidad(esp1).build();
        vetService.addVet(vet2);

        Tratamiento t1 = Tratamiento.builder()
            .fecha(LocalDate.now()) // dentro últimos 6 meses
            .pet(pet1)
            .vet(vet1)
            .drogas(List.of(droga1))
            .build();

        Tratamiento t2 = Tratamiento.builder()
            .fecha(LocalDate.now().minusMonths(3))   // dentro últimos 6 meses
            .pet(pet1)
            .vet(vet2)
            .drogas(List.of(droga1))
            .build();

        Tratamiento t3 = Tratamiento.builder()
            .fecha(LocalDate.now().minusMonths(5))   // dentro últimos 6 meses
            .pet(pet1)
            .vet(vet1)
            .drogas(List.of(droga1))
            .build();

        Tratamiento t4 = Tratamiento.builder()
            .fecha(LocalDate.now().minusMonths(8))   // antes de 6 meses
            .pet(pet2)
            .vet(vet2)
            .drogas(List.of(droga2))
            .build();

        Tratamiento t5 = Tratamiento.builder()
            .fecha(LocalDate.now().minusYears(1))    // mucho antes
            .pet(pet2)
            .vet(vet1)
            .drogas(List.of(droga2))
            .build();

        Tratamiento t6 = Tratamiento.builder()
            .fecha(LocalDate.now().minusMonths(10))  // antes de 6 meses
            .pet(pet2)
            .vet(vet2)
            .drogas(List.of(droga2))
            .build();

        tratamientoService.addTratamiento(t1);
        tratamientoService.addTratamiento(t2);
        tratamientoService.addTratamiento(t3);
        tratamientoService.addTratamiento(t4);
        tratamientoService.addTratamiento(t5);
        tratamientoService.addTratamiento(t6);
    }

    @Test
    public void tratamientoService_getTratamientoById_Tratamiento() {
        // Arrange
        Long id = 1L;

        // Act
        Tratamiento tratamiento = tratamientoService.getTratamientoById(id);

        // Assert
        Assertions.assertThat(tratamiento).isNotNull();
        Assertions.assertThat(tratamiento.getId()).isEqualTo(id);
    }

    @Test
    public void tratamientoService_addTratamientoDto_void() {
        
        // Arrange
        Calendar cal = Calendar.getInstance();
        cal.set(2026, Calendar.MAY, 4);

        // Usa Droga B (id = 2), que tiene 2 unidades disponibles y 3 vendidas

        Droga antes = drogasService.getDrogaById(2L);

        int disponiblesAntes = antes.getUnidadesDisponibles();
        int vendidasAntes = antes.getUnidadesVendidas();

        TratamientoDto newTratamientoDto = new TratamientoDto(
                null,
                new java.sql.Date(cal.getTimeInMillis()).toLocalDate(),
                1L, // petId
                1L, // vetId
                List.of(2L) // Droga B
        );

        // Act
        tratamientoService.addTratamiento(newTratamientoDto);

        Tratamiento creado = tratamientoService.getTratamientoById(7L);
        Droga despues = drogasService.getDrogaById(2L);

        // Assert
        Assertions.assertThat(creado).isNotNull();

        // Debe disminuir stock en 1
        Assertions.assertThat(despues.getUnidadesDisponibles())
                .isEqualTo(disponiblesAntes - 1);

        // Debe aumentar vendidas en 1
        Assertions.assertThat(despues.getUnidadesVendidas())
                .isEqualTo(vendidasAntes + 1);
    }

    @Test
    public void tratamientoService_addTratamiento_void() {

        // Arrange
        Calendar cal = Calendar.getInstance();
        cal.set(2020, Calendar.JUNE, 15);
        Tratamiento newTratamiento = Tratamiento.builder()
            .fecha(new java.sql.Date(cal.getTimeInMillis()).toLocalDate())
            .pet(petService.getPetById(1L))
            .vet(vetService.getVetById(1L))
            .drogas(List.of())
            .build();

        // Act
        tratamientoService.addTratamiento(newTratamiento);
        Tratamiento creado = tratamientoService.getTratamientoById(newTratamiento.getId());

        // Assert
        Assertions.assertThat(creado).isNotNull();
    }

    @Test
    public void tratamientoService_updateTratamiento_void() {

        // Arrange
        Tratamiento original = tratamientoService.getTratamientoById(1L);
        TratamientoDto updateDto = new TratamientoDto(
            1L,
            original.getFecha(),
            2L,
            2L,
            List.of() 
        );

        // Act
        tratamientoService.updateTratamiento(1L, updateDto);
        Tratamiento actualizado = tratamientoService.getTratamientoById(1L);

        // Assert
        Assertions.assertThat(actualizado).isNotNull();
        Assertions.assertThat(actualizado.getPet().getId()).isEqualTo(2L);
        Assertions.assertThat(actualizado.getVet().getId()).isEqualTo(2L);
    }

    @Test
    public void tratamientoService_deleteTratamiento_void() {

        // Arrange
        Long idToDelete = 1L;

        // Act
        tratamientoService.deleteTratamiento(idToDelete);

        // Assert
        Assertions.assertThatThrownBy(() ->
        tratamientoService.getTratamientoById(idToDelete)
        ).isInstanceOf(RuntimeException.class)
        .hasMessageContaining("No se encontró tratamiento");
        Assertions.assertThatThrownBy(() ->
            tratamientoService.getTratamientoById(idToDelete)
        ).isInstanceOf(RuntimeException.class)
        .hasMessageContaining("No se encontró tratamiento");
    }

    @Test
    public void tratamientoService_getTratamientosByPetId_ListTratamiento() {

        // Arrange
        Long petId = 1L;

        // Act
        List<Tratamiento> tratamientos = tratamientoService.getTratamientosByPetId(petId);

        // Assert
        Assertions.assertThat(tratamientos).isNotNull();
        Assertions.assertThat(tratamientos).hasSize(3); // pet1 tiene 3 tratamientos
    }

    @Test
    public void tratamientoService_getNumTratamientos6Meses_ListTratamientosMesDto() {

        // Act
        List<TratamientosMesDto> tratamientos6Meses = tratamientoService.getNumTratamientos6Meses();

        // Assert
        Assertions.assertThat(tratamientos6Meses).isNotNull();
        Assertions.assertThat(tratamientos6Meses).hasSize(6);

        LocalDate fechaActual = LocalDate.now();

        String mes5 = fechaActual.minusMonths(5).getMonth()
                .getDisplayName(TextStyle.FULL, Locale.forLanguageTag("es-ES"));
        mes5 = mes5.substring(0,1).toUpperCase() + mes5.substring(1);

        String mes4 = fechaActual.minusMonths(4).getMonth()
                .getDisplayName(TextStyle.FULL, Locale.forLanguageTag("es-ES"));
        mes4 = mes4.substring(0,1).toUpperCase() + mes4.substring(1);

        String mes3 = fechaActual.minusMonths(3).getMonth()
                .getDisplayName(TextStyle.FULL, Locale.forLanguageTag("es-ES"));
        mes3 = mes3.substring(0,1).toUpperCase() + mes3.substring(1);

        String mes2 = fechaActual.minusMonths(2).getMonth()
                .getDisplayName(TextStyle.FULL, Locale.forLanguageTag("es-ES"));
        mes2 = mes2.substring(0,1).toUpperCase() + mes2.substring(1);

        String mes1 = fechaActual.minusMonths(1).getMonth()
                .getDisplayName(TextStyle.FULL, Locale.forLanguageTag("es-ES"));
        mes1 = mes1.substring(0,1).toUpperCase() + mes1.substring(1);

        String mes0 = fechaActual.getMonth()
                .getDisplayName(TextStyle.FULL, Locale.forLanguageTag("es-ES"));
        mes0 = mes0.substring(0,1).toUpperCase() + mes0.substring(1);

        // Según los datos cargados:
        // -5 meses = 1 tratamiento (t3)
        // -4 meses = 0
        // -3 meses = 1 tratamiento (t2)
        // -2 meses = 0
        // -1 meses = 0
        // actual = 1 tratamiento (t1)

        Assertions.assertThat(tratamientos6Meses.get(0).getMes()).isEqualTo(mes5);
        Assertions.assertThat(tratamientos6Meses.get(0).getNumTratamientos()).isEqualTo(1L);

        Assertions.assertThat(tratamientos6Meses.get(1).getMes()).isEqualTo(mes4);
        Assertions.assertThat(tratamientos6Meses.get(1).getNumTratamientos()).isEqualTo(0L);

        Assertions.assertThat(tratamientos6Meses.get(2).getMes()).isEqualTo(mes3);
        Assertions.assertThat(tratamientos6Meses.get(2).getNumTratamientos()).isEqualTo(1L);

        Assertions.assertThat(tratamientos6Meses.get(3).getMes()).isEqualTo(mes2);
        Assertions.assertThat(tratamientos6Meses.get(3).getNumTratamientos()).isEqualTo(0L);

        Assertions.assertThat(tratamientos6Meses.get(4).getMes()).isEqualTo(mes1);
        Assertions.assertThat(tratamientos6Meses.get(4).getNumTratamientos()).isEqualTo(0L);

        Assertions.assertThat(tratamientos6Meses.get(5).getMes()).isEqualTo(mes0);
        Assertions.assertThat(tratamientos6Meses.get(5).getNumTratamientos()).isEqualTo(1L);
    }

    @Test
    public void tratamientoService_getDrogaTratamientosMesCount() {
        
        // Act
        List<DrogaTratamientoCountDto> resultado =
                tratamientoService.getDrogaTratamientosMesCount();

        // Assert
        Assertions.assertThat(resultado).isNotNull();

        // Últimos 30 días:
        // t1 = hace 1 mes con Droga A

        Assertions.assertThat(resultado).hasSize(1);

        DrogaTratamientoCountDto dto = resultado.get(0);

        Assertions.assertThat(dto.getDrogaNombre())
                .isEqualTo("Droga A");

        Assertions.assertThat(dto.getCount())
                .isEqualTo(1L);

    }

    @Test
    public void tratamientoService_getTratamientosByVetId_ListTratamiento() {

        // Arrange
        Long vetId = 1L;

        // Act
        List<Tratamiento> tratamientos = tratamientoService.getTratamientosByVetId(vetId);

        // Assert
        Assertions.assertThat(tratamientos).isNotNull();
        Assertions.assertThat(tratamientos).hasSize(3); // vet1 tiene 3 tratamientos (t1, t3, t5)
    }

    @Test
    public void tratamientoService_addTratamientoDto_noSePuedeAsignarDrogaSinStock() {

        // Arrange
        // Droga A ya quedó en 0 stock por los tratamientos del setUp
        Calendar cal = Calendar.getInstance();
        cal.set(2026, Calendar.MAY, 4);

        TratamientoDto dto = new TratamientoDto(
                null,
                new java.sql.Date(cal.getTimeInMillis()).toLocalDate(),
                1L,
                1L, 
                List.of(1L) // Droga A sin stock
        );

        // Act y Assert
        Assertions.assertThatThrownBy(() ->
                tratamientoService.addTratamiento(dto)
        )
        .isInstanceOf(StockInsuficienteException.class)
        .hasMessageContaining("No hay suficientes unidades");
    }

    @Test
    public void tratamientoService_addTratamientoDto_mascotaInactivaNoPuedeRecibirTratamiento() {

        // Arrange
        Pet petInactiva = petService.getPetById(1L);
        petInactiva.setEstado(false);
        petService.addPet(petInactiva);

        Calendar cal = Calendar.getInstance();
        cal.set(2026, Calendar.MAY, 4);

        TratamientoDto dto = new TratamientoDto(
                null,
                new java.sql.Date(cal.getTimeInMillis()).toLocalDate(),
                1L, // mascota ahora inactiva
                1L, // vet válido
                List.of(2L) // Droga B con stock
        );

        // Act + Assert
        Assertions.assertThatThrownBy(() ->
                tratamientoService.addTratamiento(dto)
        )
        .isInstanceOf(MascotaInactivaException.class)
        .hasMessageContaining("La mascota está inactiva");
    }

    
}
