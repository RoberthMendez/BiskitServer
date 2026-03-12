package com.example.biskit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.biskit.entities.Client;
import com.example.biskit.service.Clients.ClientsService;
import com.example.biskit.service.Pets.PetsService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/client")
public class ClientsController {

  @Autowired
  ClientsService clientsService;

  @Autowired
  PetsService petsService;

  @GetMapping("/login")
  public String getMethodName(@RequestParam(required = false) String error, Model model) {

    if (error != null && error.equals("1")) {
      model.addAttribute("error", "Usuario o contraseña incorrectos");
    }
    return "client/login";
  }

  @PostMapping("/login")
  public String postMethodName(@RequestParam String usuario, @RequestParam String contrasena) {

    if (usuario.isEmpty() || contrasena.isEmpty() || !clientsService.autenticarClient(usuario, contrasena)) {
      return "redirect:/client/login?error=1";
    }

    Client client = clientsService.findByUsuario(usuario);
    return "redirect:/client/" + client.getId() + "/pets";

  }

  @GetMapping("{id}/pets")
  public String getMethodName(@PathVariable("id") Long id, Model model) {
    model.addAttribute("client", clientsService.getClientById(id));
    model.addAttribute("pets", clientsService.getPetsByClientId(id));
    return "client/pets-client";
  }

  @GetMapping("{idClient}/pets/{idPet}")
  public String getMethodName(@PathVariable("idClient") Long idClient, @PathVariable("idPet") Long idPet,
      Model model) {
    model.addAttribute("client", clientsService.getClientById(idClient));
    model.addAttribute("pet", petsService.getPetById(idPet));
    return "client/info-pet";
  }

}