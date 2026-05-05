package com.example.biskit.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import com.example.biskit.entities.Droga;
import com.example.biskit.entities.Tratamiento;
import com.example.biskit.service.Pets.PetsService;
import com.example.biskit.service.Vets.VetService;
import com.example.biskit.service.Tratamientos.DrogasService;
import com.example.biskit.service.Tratamientos.TratamientosImpl;
import com.example.biskit.entities.pets.Pet;
import com.example.biskit.entities.vets.Vet;
import com.example.biskit.errors.MascotaInactivaException;
import com.example.biskit.errors.StockInsuficienteException;
import com.example.biskit.repo.TratamientosRepo;
import com.example.biskit.entities.dtos.DrogaTratamientoCountDto;
import com.example.biskit.entities.dtos.TratamientoDto;
import com.example.biskit.entities.dtos.TratamientosMesDto;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class TratamientoServiceTestMock {

    @Mock
    private TratamientosRepo tratamientosRepo;

    @Mock
    private VetService vetService;

    @Mock
    private PetsService petsService;

    @Mock
    private DrogasService drogasService;

    @InjectMocks
    private TratamientosImpl tratamientoService;

    private Pet petActiva() {
        return Pet.builder().id(1L).nombre("Firulais").estado(true).build();
    }

    private Pet petInactiva() {
        return Pet.builder().id(1L).nombre("Firulais").estado(false).build();
    }

    private Vet vet1() {
        return Vet.builder().id(1L).nombre("Dr. Smith").tratamientos(new ArrayList<>()).build();
    }

    private Vet vet2() {
        return Vet.builder().id(2L).nombre("Dra. Johnson").tratamientos(new ArrayList<>()).build();
    }

    // Droga con stock 
    private Droga drogaConStock() {
        return Droga.builder()
                .id(2L).nombre("Droga B")
                .unidadesDisponibles(2).unidadesVendidas(3)
                .tratamientos(new ArrayList<>())
                .build();
    }

    // Droga sin stock
    private Droga drogaSinStock() {
        return Droga.builder()
                .id(1L).nombre("Droga A")
                .unidadesDisponibles(0).unidadesVendidas(3)
                .tratamientos(new ArrayList<>())
                .build();
    }

    // ─────────────────────────────────────────────────────────────
    // 1. getTratamientoById 
    // ─────────────────────────────────────────────────────────────
    @Test
    public void tratamientoService_getTratamientoById_Tratamiento() {
        // Arrange
        Long id = 1L;
        Tratamiento tratamientoMock = Tratamiento.builder()
                .id(id)
                .fecha(LocalDate.now())
                .build();

        when(tratamientosRepo.findById(id))
                .thenReturn(Optional.of(tratamientoMock));

        // Act
        Tratamiento resultado = tratamientoService.getTratamientoById(id);

        // Assert
        Assertions.assertThat(resultado).isNotNull();
        Assertions.assertThat(resultado.getId()).isEqualTo(id);
    }

    @Test
    public void tratamientoService_getTratamientoById_lanzaExcepcionSiNoExiste() {
        // Arrange
        Long id = 99L;

        when(tratamientosRepo.findById(id))
                .thenReturn(Optional.empty());

        // Act + Assert
        Assertions.assertThatThrownBy(() ->
                tratamientoService.getTratamientoById(id)
        )
        .isInstanceOf(RuntimeException.class)
        .hasMessageContaining("No se encontró tratamiento con id: " + id);
    }

    // ─────────────────────────────────────────────────────────────
    // 2. addTratamiento(TratamientoDto)
    // ─────────────────────────────────────────────────────────────
    @Test
    public void tratamientoService_addTratamientoDto_void() {
        // Arrange
        Droga drogaB = drogaConStock();
        int disponiblesAntes = drogaB.getUnidadesDisponibles(); // 2
        int vendidasAntes    = drogaB.getUnidadesVendidas();    // 3

        TratamientoDto dto = new TratamientoDto(
                null,
                LocalDate.of(2026, 5, 4),
                1L,       // petId
                1L,       // vetId
                List.of(2L) // Droga B
        );

        when(petsService.getPetById(1L)).thenReturn(petActiva());
        when(vetService.getVetById(1L)).thenReturn(vet1());
        when(drogasService.getDrogaById(2L)).thenReturn(drogaB);

        // Act
        tratamientoService.addTratamiento(dto);

        // Assert
        Assertions.assertThat(drogaB.getUnidadesDisponibles()).isEqualTo(disponiblesAntes - 1);
        Assertions.assertThat(drogaB.getUnidadesVendidas()).isEqualTo(vendidasAntes + 1);
       
    }

    // ─────────────────────────────────────────────────────────────
    // 3. addTratamiento 
    // ─────────────────────────────────────────────────────────────
    @Test
    public void tratamientoService_addTratamiento_void() {
        // Arrange
        Droga drogaB = drogaConStock();
        int disponiblesAntes = drogaB.getUnidadesDisponibles();
        int vendidasAntes    = drogaB.getUnidadesVendidas();  

        Tratamiento nuevoTratamiento = Tratamiento.builder()
                .fecha(LocalDate.of(2020, 6, 15))
                .pet(petActiva())
                .vet(vet1())
                .drogas(new ArrayList<>(List.of(drogaB)))
                .build();

        // Act
        tratamientoService.addTratamiento(nuevoTratamiento);

        // Assert
        Assertions.assertThat(drogaB.getUnidadesDisponibles()).isEqualTo(disponiblesAntes - 1);
        Assertions.assertThat(drogaB.getUnidadesVendidas()).isEqualTo(vendidasAntes + 1);
    }

    // ─────────────────────────────────────────────────────────────
    // 4. updateTratamiento → cambia pet y vet, sin drogas nuevas
    // ─────────────────────────────────────────────────────────────
    @Test
    public void tratamientoService_updateTratamiento_void() {
        // Arrange
        Long id = 1L;

        // Tratamiento original: pet1, vet1, sin drogas
        Tratamiento original = Tratamiento.builder()
                .id(id)
                .fecha(LocalDate.now())
                .pet(petActiva())
                .vet(vet1())
                .drogas(new ArrayList<>())
                .build();

        // DTO de actualización: cambia a pet2 y vet2, sin drogas
        TratamientoDto updateDto = new TratamientoDto(
                id,
                original.getFecha(),
                2L,       // nuevo petId
                2L,       // nuevo vetId
                List.of()
        );

        when(tratamientosRepo.findById(id)).thenReturn(Optional.of(original));
        when(petsService.getPetById(2L)).thenReturn(
                Pet.builder().id(2L).nombre("Michi").estado(true).build()
        );
        when(vetService.getVetById(2L)).thenReturn(vet2());

        // Act
        tratamientoService.updateTratamiento(id, updateDto);

        // Assert: el objeto mutado debe reflejar los nuevos valores
        Assertions.assertThat(original.getPet().getId()).isEqualTo(2L);
        Assertions.assertThat(original.getVet().getId()).isEqualTo(2L);
    }

    // ─────────────────────────────────────────────────────────────
    // 5. deleteTratamiento
    // ─────────────────────────────────────────────────────────────
    @Test
    public void tratamientoService_deleteTratamiento_void() {
        // Arrange
        Long id = 1L;

        Pet    pet = petActiva();
        Vet    vet = vet1();
        Droga  droga = drogaConStock();

        // El tratamiento a eliminar está vinculado al vet, pet y droga
        Tratamiento tratamiento = Tratamiento.builder()
                .id(id)
                .fecha(LocalDate.now())
                .pet(pet)
                .vet(vet)
                .drogas(new ArrayList<>(List.of(droga)))
                .build();

        // Las listas bidireccionales deben contener al tratamiento
        pet.setTratamientos(new ArrayList<>(List.of(tratamiento)));
        vet.setTratamientos(new ArrayList<>(List.of(tratamiento)));
        droga.setTratamientos(new ArrayList<>(List.of(tratamiento)));

        when(tratamientosRepo.findById(id)).thenReturn(Optional.of(tratamiento));

        // Después de borrar, findById debe lanzar excepción
        when(tratamientosRepo.findById(id))
                .thenReturn(Optional.of(tratamiento))   // primera llamada: para el delete
                .thenReturn(Optional.empty());           // segunda llamada: ya no existe

        // Act
        tratamientoService.deleteTratamiento(id);

        // Assert
        Assertions.assertThatThrownBy(() ->
                tratamientoService.getTratamientoById(id)
        )
        .isInstanceOf(RuntimeException.class)
        .hasMessageContaining("No se encontró tratamiento");
    }

    // ─────────────────────────────────────────────────────────────
    // 6. getTratamientosByPetId
    // ─────────────────────────────────────────────────────────────
    @Test
    public void tratamientoService_getTratamientosByPetId_ListTratamiento() {
        // Arrange
        Long petId = 1L;
        Pet  pet   = petActiva();

        List<Tratamiento> listaMock = List.of(
                Tratamiento.builder().id(1L).fecha(LocalDate.now()).pet(pet).build(),
                Tratamiento.builder().id(2L).fecha(LocalDate.now().minusMonths(3)).pet(pet).build(),
                Tratamiento.builder().id(3L).fecha(LocalDate.now().minusMonths(5)).pet(pet).build()
        );

        when(tratamientosRepo.findByPetId(petId)).thenReturn(listaMock);

        // Act
        List<Tratamiento> resultado = tratamientoService.getTratamientosByPetId(petId);

        // Assert
        Assertions.assertThat(resultado).isNotNull();
        Assertions.assertThat(resultado).hasSize(3);
    }

    // ─────────────────────────────────────────────────────────────
    // 7. getTratamientosByVetId
    // ─────────────────────────────────────────────────────────────
    @Test
    public void tratamientoService_getTratamientosByVetId_ListTratamiento() {
        // Arrange
        Long vetId = 1L;
        Vet  vet   = vet1();

        List<Tratamiento> listaMock = List.of(
                Tratamiento.builder().id(1L).fecha(LocalDate.now()).vet(vet).build(),
                Tratamiento.builder().id(3L).fecha(LocalDate.now().minusMonths(5)).vet(vet).build(),
                Tratamiento.builder().id(5L).fecha(LocalDate.now().minusYears(1)).vet(vet).build()
        );

        when(tratamientosRepo.findByVetId(vetId)).thenReturn(listaMock);

        // Act
        List<Tratamiento> resultado = tratamientoService.getTratamientosByVetId(vetId);

        // Assert
        Assertions.assertThat(resultado).isNotNull();
        Assertions.assertThat(resultado).hasSize(3);
    }

    // ─────────────────────────────────────────────────────────────
    // 8. getNumTratamientos6Meses → 6 entradas con conteos correctos
    // ─────────────────────────────────────────────────────────────
    @Test
    public void tratamientoService_getNumTratamientos6Meses_ListTratamientosMesDto() {
        // Arrange
        LocalDate hoy = LocalDate.now();

        // El servicio llama getNumTratamientosMes(año, mes) para cada uno
        // de los últimos 6 meses (i=5 → i=0). Simulamos los conteos del naive test:
        // -5 meses → 1, -4 meses → 0, -3 meses → 1, -2 meses → 0, -1 mes → 0, actual → 1
        when(tratamientosRepo.getNumTratamientosMes(
                hoy.minusMonths(5).getYear(), hoy.minusMonths(5).getMonthValue())).thenReturn(1L);
        when(tratamientosRepo.getNumTratamientosMes(
                hoy.minusMonths(4).getYear(), hoy.minusMonths(4).getMonthValue())).thenReturn(0L);
        when(tratamientosRepo.getNumTratamientosMes(
                hoy.minusMonths(3).getYear(), hoy.minusMonths(3).getMonthValue())).thenReturn(1L);
        when(tratamientosRepo.getNumTratamientosMes(
                hoy.minusMonths(2).getYear(), hoy.minusMonths(2).getMonthValue())).thenReturn(0L);
        when(tratamientosRepo.getNumTratamientosMes(
                hoy.minusMonths(1).getYear(), hoy.minusMonths(1).getMonthValue())).thenReturn(0L);
        when(tratamientosRepo.getNumTratamientosMes(
                hoy.getYear(), hoy.getMonthValue())).thenReturn(1L);

        // Act
        List<TratamientosMesDto> resultado = tratamientoService.getNumTratamientos6Meses();

        // Nombres de mes en español capitalizados (igual que el servicio)
        String[] nombresMes = new String[6];
        for (int i = 5; i >= 0; i--) {
            String nombre = hoy.minusMonths(i).getMonth()
                    .getDisplayName(TextStyle.FULL, Locale.forLanguageTag("es-ES"));
            nombresMes[5 - i] = nombre.substring(0, 1).toUpperCase() + nombre.substring(1);
        }

        // Assert
        Assertions.assertThat(resultado).isNotNull();
        Assertions.assertThat(resultado).hasSize(6);

        Assertions.assertThat(resultado.get(0).getMes()).isEqualTo(nombresMes[0]);
        Assertions.assertThat(resultado.get(0).getNumTratamientos()).isEqualTo(1L);

        Assertions.assertThat(resultado.get(1).getMes()).isEqualTo(nombresMes[1]);
        Assertions.assertThat(resultado.get(1).getNumTratamientos()).isEqualTo(0L);

        Assertions.assertThat(resultado.get(2).getMes()).isEqualTo(nombresMes[2]);
        Assertions.assertThat(resultado.get(2).getNumTratamientos()).isEqualTo(1L);

        Assertions.assertThat(resultado.get(3).getMes()).isEqualTo(nombresMes[3]);
        Assertions.assertThat(resultado.get(3).getNumTratamientos()).isEqualTo(0L);

        Assertions.assertThat(resultado.get(4).getMes()).isEqualTo(nombresMes[4]);
        Assertions.assertThat(resultado.get(4).getNumTratamientos()).isEqualTo(0L);

        Assertions.assertThat(resultado.get(5).getMes()).isEqualTo(nombresMes[5]);
        Assertions.assertThat(resultado.get(5).getNumTratamientos()).isEqualTo(1L);
    }

    // ─────────────────────────────────────────────────────────────
    // 9. getDrogaTratamientosMesCount → últimos 30 días, 1 droga
    // ─────────────────────────────────────────────────────────────
    @Test
    public void tratamientoService_getDrogaTratamientosMesCount() {
        // Arrange
        // El servicio usa LocalDate.now().minusDays(30) internamente,
        // así que usamos any() para no acoplarnos al instante exacto.
        Droga drogaA = drogaSinStock(); // "Droga A"

        when(tratamientosRepo.getDrogasDesde(any(LocalDate.class)))
                .thenReturn(List.of(drogaA));
        when(tratamientosRepo.getNumTratamientosDrogaDesde(
                eq(drogaA.getId()), any(LocalDate.class)))
                .thenReturn(1L);

        // Act
        List<DrogaTratamientoCountDto> resultado =
                tratamientoService.getDrogaTratamientosMesCount();

        // Assert
        Assertions.assertThat(resultado).isNotNull();
        Assertions.assertThat(resultado).hasSize(1);
        Assertions.assertThat(resultado.get(0).getDrogaNombre()).isEqualTo("Droga A");
        Assertions.assertThat(resultado.get(0).getCount()).isEqualTo(1L);
    }

    // ─────────────────────────────────────────────────────────────
    // 10. addTratamientoDto → lanza StockInsuficienteException
    // ─────────────────────────────────────────────────────────────
    @Test
    public void tratamientoService_addTratamientoDto_noSePuedeAsignarDrogaSinStock() {
        // Arrange
        TratamientoDto dto = new TratamientoDto(
                null,
                LocalDate.of(2026, 5, 4),
                1L,
                1L,
                List.of(1L) // Droga A sin stock
        );

        when(petsService.getPetById(1L)).thenReturn(petActiva());
        when(vetService.getVetById(1L)).thenReturn(vet1());
        when(drogasService.getDrogaById(1L)).thenReturn(drogaSinStock()); // 0 unidades

        // Act + Assert
        Assertions.assertThatThrownBy(() ->
                tratamientoService.addTratamiento(dto)
        )
        .isInstanceOf(StockInsuficienteException.class)
        .hasMessageContaining("No hay suficientes unidades");
    }

    // ─────────────────────────────────────────────────────────────
    // 11. addTratamientoDto → lanza MascotaInactivaException
    // ─────────────────────────────────────────────────────────────
    @Test
    public void tratamientoService_addTratamientoDto_mascotaInactivaNoPuedeRecibirTratamiento() {
        // Arrange
        TratamientoDto dto = new TratamientoDto(
                null,
                LocalDate.of(2026, 5, 4),
                1L, // mascota inactiva
                1L,
                List.of(2L) // Droga B con stock (no llega a evaluarse)
        );

        when(petsService.getPetById(1L)).thenReturn(petInactiva()); // estado = false
        when(vetService.getVetById(1L)).thenReturn(vet1());

        // Act + Assert
        Assertions.assertThatThrownBy(() ->
                tratamientoService.addTratamiento(dto)
        )
        .isInstanceOf(MascotaInactivaException.class)
        .hasMessageContaining("La mascota está inactiva");
    }

    @Test
    public void tratamientoService_updateTratamiento_lanzaExcepcionSiNuevasDrogaSinStock() {
        // Arrange
        Long id = 1L;

        // El tratamiento original tiene Droga B con stock
        Tratamiento existente = Tratamiento.builder()
                .id(id)
                .fecha(LocalDate.now())
                .pet(petActiva())
                .vet(vet1())
                .drogas(new ArrayList<>(List.of(drogaConStock())))
                .build();

        // DTO que intenta cambiar a Droga A 
        TratamientoDto updateDto = new TratamientoDto(
                id,
                LocalDate.now(),
                1L,
                1L,
                List.of(2L) // Droga A sin stock
        );

        when(petsService.getPetById(1L)).thenReturn(petActiva());
        when(vetService.getVetById(1L)).thenReturn(vet1());
        when(tratamientosRepo.findById(id)).thenReturn(Optional.of(existente));
        when(drogasService.getDrogaById(2L)).thenReturn(drogaSinStock());

        // Act + Assert
        Assertions.assertThatThrownBy(() ->
                tratamientoService.updateTratamiento(id, updateDto)
        )
        .isInstanceOf(StockInsuficienteException.class)
        .hasMessageContaining("No hay suficientes unidades de Droga A");
    }

    
}
