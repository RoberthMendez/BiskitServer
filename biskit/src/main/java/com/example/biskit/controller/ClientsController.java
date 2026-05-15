package com.example.biskit.controller;

import com.example.biskit.entities.Client;
import com.example.biskit.entities.pets.Pet;
import com.example.biskit.service.Clients.ClientsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clients")
@CrossOrigin(origins = "http://localhost:4200")
public class ClientsController {

  @Autowired
  private ClientsService clientsService;

  // ----- Crear Cliente (CREATE) -----
  @PostMapping("/add")
  public ResponseEntity<Client> crearCliente(@RequestBody Client client) {
    return ResponseEntity.status(HttpStatus.CREATED).body(clientsService.addClient(client));
  }

  // ----- Mostrar Clientes (READ) -----
  @GetMapping("")
  public List<Client> mostrarClientes() {
    return clientsService.getClients();
  }

  // ----- Mostrar Cliente (READ) -----
  @GetMapping("/{id}")
  public Client mostrarCliente(@PathVariable("id") Long id) {
    return clientsService.getClientById(id);
  }

  // ----- Mostrar Mascotas de un Cliente (READ) -----
  @GetMapping("/{id}/pets")
  public List<Pet> mostrarMascotasDeCliente(@PathVariable("id") Long id) {
    return clientsService.getPetsByClientId(id);
  }

  // ----- Actualizar Cliente (UPDATE) -----
  @PutMapping("/update/{id}")
  public void actualizarCliente(@PathVariable("id") Long id, @RequestBody Client client) {
    client.setId(id);
    clientsService.updateClient(client);
  }

  // ----- Eliminar Cliente (DELETE) -----
  @DeleteMapping("/delete/{id}")
  public void eliminarCliente(@PathVariable("id") Long id) {
    clientsService.deleteClient(id);
  }

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
