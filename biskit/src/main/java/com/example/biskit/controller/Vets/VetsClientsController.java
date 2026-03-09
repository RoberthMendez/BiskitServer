package com.example.biskit.controller.Vets;

import java.util.concurrent.atomic.LongAccumulator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.biskit.entities.Client;
import com.example.biskit.service.Clients.ClientsService;

@Controller
@RequestMapping("/vet")
public class VetsClientsController {

  @Autowired
  private ClientsService clientsService;

  // ================ CLIENTES ================

  private static final String clientsPath = "vet/clients/";

  // ----- Mostrar Clientes (READ) -----
  @GetMapping("/clients")
  public String mostrarClientes(Model model) {
    model.addAttribute("clients", clientsService.getClients());
    return clientsPath + "clients";
  }

  // ----- Mostrar Cliente (READ) -----
  @GetMapping("/clients/{id}")
  public String mostrarCliente(@PathVariable("id") Long id, Model model) {
    model.addAttribute("client", clientsService.getClientById(id));
    model.addAttribute("pets", clientsService.getPetsByClientId(id));
    return clientsPath + "info-client";
  }

  // ----- Añadir Cliente (CREATE) -----
  @GetMapping("/clients/add")
  public String mostrarFormularioNuevoCliente(Model model) {
    Client client = new Client();
    model.addAttribute("client", client);
    return clientsPath + "add-client";
  }

  @PostMapping("/clients/add")
  public String agregarCliente(@ModelAttribute("client") Client client) {
    clientsService.addClient(client);
    return "redirect:/vet/clients";
  }

  // ----- Editar Cliente (UPDATE) -----
  @GetMapping("/clients/update/{id}")
  public String mostrarFormularioUpdateCliente(@PathVariable("id") Long id, Model model) {
    Client client = clientsService.getClientById(id);
    model.addAttribute("client", client);
    return clientsPath + "add-client";
  }

  @PostMapping("/clients/update")
  public String actualizarCliente(@ModelAttribute("client") Client client) {

    Client clientExistente = clientsService.getClientById(client.getId());
    if (clientExistente != null) {
      client.setPets(clientExistente.getPets());
    }

    clientsService.updateClient(client);
    return "redirect:/vet/clients";

  }

  // ----- Eliminar Cliente (DELETE) -----
  @GetMapping("/clients/delete/{id}")
  public String eliminarCliente(@PathVariable("id") Long id) {
    clientsService.deleteClient(id);
    return "redirect:/vet/clients";
  }

}
