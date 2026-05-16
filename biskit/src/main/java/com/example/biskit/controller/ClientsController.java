package com.example.biskit.controller;

import com.example.biskit.entities.Client;
import com.example.biskit.entities.DTOs.Pets.PetDTO;
import com.example.biskit.entities.DTOs.Pets.PetMapper;
import com.example.biskit.service.Clients.ClientsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
  // http://localhost:8080/clients/add
  @PostMapping("/add")
  public ResponseEntity<Client> crearCliente(@RequestBody Client client) {
    return new ResponseEntity<>(clientsService.addClient(client), HttpStatus.CREATED);
  }

  // ----- Mostrar Clientes (READ) -----
  // http://localhost:8080/clients
  @GetMapping("")
  public ResponseEntity<List<Client>> mostrarClientes() {
    return new ResponseEntity<>(clientsService.getClients(), HttpStatus.OK);
  }

  // ----- Mostrar Cliente (READ) -----
  // http://localhost:8080/clients/{id}
  @GetMapping("/{id}")
  public ResponseEntity<Client> mostrarCliente(@PathVariable("id") Long id) {
    return new ResponseEntity<>(clientsService.getClientById(id), HttpStatus.OK);
  }

  // ----- Mostrar Mascotas de un Cliente (READ) -----
  // http://localhost:8080/clients/{id}/pets
  @GetMapping("/{id}/pets")
  public ResponseEntity<List<PetDTO>> mostrarMascotasDeCliente(@PathVariable("id") Long id) {
    return new ResponseEntity<>(
      PetMapper.INSTANCE.toDTOList(clientsService.getPetsByClientId(id)),
      HttpStatus.OK
    );
  }

  // ----- Actualizar Cliente (UPDATE) -----
  // http://localhost:8080/clients/update/{id}
  @PutMapping("/update/{id}")
  public ResponseEntity<Client> actualizarCliente(
    @PathVariable("id") Long id,
    @RequestBody Client client
  ) {
    client.setId(id);
    return new ResponseEntity<>(clientsService.updateClient(client), HttpStatus.OK);
  }

  // ----- Eliminar Cliente (DELETE) -----
  // http://localhost:8080/clients/delete/{id}
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> eliminarCliente(@PathVariable("id") Long id) {
    clientsService.deleteClient(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  // http://localhost:8080/clients/count
  @GetMapping("/count")
  public ResponseEntity<Long> getTotalClients() {
    return new ResponseEntity<>(clientsService.getClientsCount(), HttpStatus.OK);
  }

  // ----- Verificar si un Cliente existe -----
  // http://localhost:8080/clients/{id}/exists
  @GetMapping("/{id}/exists")
  public ResponseEntity<Void> checkClientId(@PathVariable Long id) {
    clientsService.getClientById(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/details")
  public Client buscarClient() {
    Client client = clientsService.findByUsuario(
      SecurityContextHolder.getContext().getAuthentication().getName()
    );
    return client;
  }
}
