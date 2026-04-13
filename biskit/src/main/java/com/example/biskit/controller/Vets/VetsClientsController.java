package com.example.biskit.controller.Vets;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  // ================ CLIENTES ================

  private static final String clientsPath = "vet/clients/";

  @GetMapping("")
  public String getMethodName() {
    return "vet/vet-panel";
  }

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

  // ----- Añadir Cliente (CREATE) -----
  @GetMapping("/clients/add")
  public String mostrarFormularioNuevoCliente(@RequestParam(required = false) String error, Model model) {

    model.addAttribute("client", new Client());

    if (error != null) {
      if (error.equals("nombre_vacio")) {
        model.addAttribute("error", "El campo de nombre no puede estar vacío");
      } else if (error.equals("cedula_vacia")) {
        model.addAttribute("error", "El campo de cédula no puede estar vacío");
      } else if (error.equals("usuario_vacio")) {
        model.addAttribute("error", "El campo de correo no puede estar vacío");
      } else if (error.equals("celular_vacio")) {
        model.addAttribute("error", "El campo de teléfono no puede estar vacío");
      } else if (error.equals("cedula_existente")) {
        model.addAttribute("error", "La cédula ya está registrada con un cliente existente");
      } else if (error.equals("correo_existente")) {
        model.addAttribute("error", "El correo ya está registrado con un cliente existente");
      }
    }

    return clientsPath + "add-client";
  }

  // ----- Editar Cliente (UPDATE) -----
  @GetMapping("/clients/update/{id}")
  public String mostrarFormularioUpdateCliente(@PathVariable("id") Long id, Model model) {
    Client client = clientsService.getClientById(id);
    model.addAttribute("client", client);
    return clientsPath + "add-client";
  }

  // @PostMapping("/clients/update")
  // public String actualizarCliente(@ModelAttribute("client") Client client) {

  // Client clientExistente = clientsService.getClientById(client.getId());
  // clientsService.updateClient(clientExistente);
  // return "redirect:/vet/clients";

  // }

  // ----- Eliminar Cliente (DELETE) -----
  @GetMapping("/clients/delete/{id}")
  public String eliminarCliente(@PathVariable("id") Long id) {
    clientsService.deleteClient(id);
    return "redirect:/vet/clients";
  }

}
