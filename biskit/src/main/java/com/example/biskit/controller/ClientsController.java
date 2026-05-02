package com.example.biskit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.biskit.service.Clients.ClientsService;
import com.example.biskit.service.Pets.PetsService;
import com.example.biskit.service.Tratamientos.TratamientosService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
@CrossOrigin(origins = "http://localhost:4200")
public class ClientsController {

  @Autowired
  ClientsService clientsService;

  @Autowired
  TratamientosService tratamientosService;

  @Autowired
  PetsService petsService;

  // ----- Contar Total de Clientes -----
  @GetMapping("/count")
  public Long getTotalClients() {
    return clientsService.getClientsCount();
  }

  // ----- Verificar si un Cliente existe -----
  @GetMapping("/{id}/exists")
  public ResponseEntity<Void> checkClientId(@PathVariable Long id) {
      clientsService.getClientById(id);
      return ResponseEntity.ok().build();
  }

}