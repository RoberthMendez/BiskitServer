package com.example.biskit.controller.Vets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.biskit.entities.Client;
import com.example.biskit.service.Clients.ClientsService;

import com.example.biskit.service.Credenciales.CredencialesService;

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

  @PostMapping("/clients/add")
  public String agregarCliente(@ModelAttribute("client") Client client) {

    if (client.getNombre().isEmpty()) {
      return "redirect:/vet/clients/add?error=nombre_vacio";
    }
    if (client.getCedula().isEmpty()) {
      return "redirect:/vet/clients/add?error=cedula_vacia";
    }
    if (client.getCorreo().isEmpty()) {
      return "redirect:/vet/clients/add?error=usuario_vacio";
    }
    if (client.getCelular().isEmpty()) {
      return "redirect:/vet/clients/add?error=celular_vacio";
    }

    if (clientsService.existeCedula(client.getCedula())) {
      return "redirect:/vet/clients/add?error=cedula_existente";
    }

    if (clientsService.existeCorreo(client.getCorreo())) {
      return "redirect:/vet/clients/add?error=correo_existente";

    }

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
