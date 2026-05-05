package com.example.biskit.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import com.example.biskit.controller.Vets.VetsPetsController;
import com.example.biskit.entities.Client;
import com.example.biskit.entities.pets.Pet;
import com.example.biskit.service.Pets.PetsService;
import com.example.biskit.service.Clients.ClientsService;
import org.mockito.Mockito;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.sql.Date;
import java.util.List;

@WebMvcTest(controllers = VetsPetsController.class)
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class VetsPetsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("removal")
    @MockBean
    private PetsService petsService;

    @SuppressWarnings("removal")
    @MockBean
    private ClientsService clientsService;

    @Autowired
    private ObjectMapper objectMapper;

    /* Prueba para agregar una mascota */
    @Test
    public void VetsPetsController_addPet_Pet() throws Exception {

        Client owner = Client.builder()
                .id(1L)
                .build();

        Pet pet = Pet.builder()
                .nombre("Firulais")
                .estado(true)
                .fechaNacimiento(Date.valueOf("2020-01-01"))
                .owner(owner)
                .build();

        when(petsService.asignarRelacionesDePetPorIds(Mockito.any(Pet.class))).thenReturn(pet);

        
        doNothing().when(clientsService).addPetToClient(Mockito.anyLong(), Mockito.any(Pet.class));
        when(petsService.addPet(Mockito.any(Pet.class))).thenReturn(pet);

        ResultActions response = mockMvc.perform(post("/vet/pets/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(pet)));

        response
                .andExpect(status().isCreated());
    }

    /* Prueba que resulta incorrecta para agregar una mascota cuando se ingresa un nombre nulo */
    @Test
    public void VetsPetsController_addPet_InvalidPet_ThrowsException() throws Exception {
        Pet pet = Pet.builder()
                .nombre(null) //nombre no puede ser nulo
                .estado(true)
                .fechaNacimiento(Date.valueOf("2020-01-01"))
                .build();

        ResultActions response = mockMvc.perform(post("/vet/pets/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(pet)));

        response
                .andExpect(status().isInternalServerError());
    }

    /* Prueba para mostrar todas las mascotas */
    @Test
    public void VetsPetsController_getPets_Pets() throws Exception {
        Pet pet1 = Pet.builder()
                .nombre("Firulais")
                .estado(true)
                .fechaNacimiento(Date.valueOf("2020-01-01"))
                .build();

        Pet pet2 = Pet.builder()
                .nombre("Firulais2")
                .estado(true)
                .fechaNacimiento(Date.valueOf("2021-01-01"))
                .build();

        when(petsService.getPets()).thenReturn(List.of(pet1, pet2));

        ResultActions response = mockMvc.perform(get("/vet/pets")
                .contentType("application/json"));

        response
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].nombre").value("Firulais"))
                .andExpect(jsonPath("$[1].nombre").value("Firulais2"))
                .andExpect(jsonPath("$.size()").value(2));
    }

    /* Prueba para mostrar una mascota específica */
    @Test
    public void VetsPetsController_getPetById_Pet() throws Exception {
        Pet pet = Pet.builder()
                .nombre("Firulais")
                .estado(true)
                .fechaNacimiento(Date.valueOf("2020-01-01"))
                .build();

        when(petsService.getPetById(1L)).thenReturn(pet);

        ResultActions response = mockMvc.perform(get("/vet/pets/1")
                .contentType("application/json"));

        response
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.nombre").value("Firulais"));
    }

    /* Prueba para mostrar una mascota específica pero no existe el ID */
    @Test
    public void VetsPetsController_getPetById_NotFound() throws Exception {
        when(petsService.getPetById(1L)).thenReturn(null);

        ResultActions response = mockMvc.perform(get("/vet/pets/1")
                .contentType("application/json"));

        response
                .andExpect(status().isNotFound());
    }

    /* Prueba para cambiar el estado de una mascota */
    @Test
    public void VetsPetsController_changePetEstado_Pet() throws Exception {
        Pet pet = Pet.builder()
                .nombre("Firulais")
                .estado(false)
                .fechaNacimiento(Date.valueOf("2020-01-01"))
                .build();

        when(petsService.cambiarEstadoMascota(1L, false)).thenReturn(pet);

        ResultActions response = mockMvc.perform(patch("/vet/pets/update-estado/1")
                .contentType("application/json")
                .content("{\"estado\": false}"));

        response
                .andExpect(status().isOk());
    }

    /* Prueba para cambiar el estado de una mascota cuando el ID no existe */
    @Test
    public void VetsPetsController_changePetEstado_NotFound() throws Exception {
        when(petsService.cambiarEstadoMascota(1L, false)).thenReturn(null);

        ResultActions response = mockMvc.perform(patch("/vet/pets/update-estado/1")
                .contentType("application/json")
                .content("{\"estado\": false}"));

        response
                .andExpect(status().isBadRequest());
    }

    /* Prueba para actualizar una mascota */
    @Test
    public void VetsPetsController_updatePet_Pet() throws Exception {
        Pet pet = Pet.builder()
                .nombre("Firulais")
                .estado(true)
                .fechaNacimiento(Date.valueOf("2020-01-01"))
                .build();

        when(petsService.asignarRelacionesDePetPorIds(Mockito.any(Pet.class))).thenReturn(pet);
        when(petsService.updatePet(Mockito.any(Pet.class))).thenReturn(pet);

        ResultActions response = mockMvc.perform(put("/vet/pets/update/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(pet)));

        response
                .andExpect(status().isOk());
    }
}
