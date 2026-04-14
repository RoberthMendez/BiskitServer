package com.example.biskit.controller.Vets;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.biskit.entities.Client;
import com.example.biskit.entities.pets.Pet;
import com.example.biskit.service.Clients.ClientsService;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/vet")
@CrossOrigin(origins = "http://localhost:4200")
public class VetsClientsController {

  @Autowired
  private ClientsService clientsService;

  // ----- Crear Cliente (CREATE) -----
  @PostMapping("/clients/add")
  public void crearCliente(@RequestBody Client client) {
    clientsService.addClient(client);
  }

  // ----- Mostrar Clientes (READ) -----
  @GetMapping("/clients")
  public List<Client> mostrarClientes() {
    return clientsService.getClients();
  }

  // ----- Mostrar Cliente (READ) -----
  @GetMapping("/clients/{id}")
  public Client mostrarCliente(@PathVariable("id") Long id) {
    return clientsService.getClientById(id);
  }

  // ----- Mostrar Mascotas de un Cliente (READ) -----
  @GetMapping("/clients/{id}/pets")
  public List<Pet> mostrarMascotasDeCliente(@PathVariable("id") Long id) {
    return clientsService.getPetsByClientId(id);
  }

  // ----- Actualizar Cliente (UPDATE) -----
  @PutMapping("/clients/update/{id}")
  public void actualizarCliente(@PathVariable("id") Long id, @RequestBody Client client) {
    client.setId(id);
    clientsService.updateClient(client);
  }

  // ----- Eliminar Cliente (DELETE) -----
  @DeleteMapping("/clients/delete/{id}")
  public void eliminarCliente(@PathVariable("id") Long id) {
    clientsService.deleteClient(id);
  }

}
