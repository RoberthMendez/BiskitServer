package com.example.biskit.controller.Vets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.biskit.service.Clients.ClientsService;
import com.example.biskit.service.Pets.PetsService;
import com.example.biskit.entities.Client;
import com.example.biskit.entities.Pet;

@Controller
@RequestMapping("/vet")
public class VetsPetsController {

  @Autowired
  private PetsService petsService;

  @Autowired
  private ClientsService clientsService;

  private static final String petsPath = "vet/pets/";

  // ----- Mostrar Mascotas (READ) -----

  @GetMapping("/pets")
  public String mostrarMascotas(Model model) {
    model.addAttribute("pets", petsService.getPets());
    return petsPath + "pets";
  }

  // ----- Mostrar Mascota (READ) -----

  @GetMapping("/pets/{id}")
  public String mostrarMascota(@PathVariable("id") Long id, Model model) {
    Pet pet = petsService.getPetById(id);
    Client owner = pet.getOwner();
    model.addAttribute("pet", pet);
    model.addAttribute("dueño", owner);
    return petsPath + "info-pet";
  }

  // ----- Añadir Mascota (CREATE) -----

  @GetMapping("/pets/add")
  public String mostrarFormularioAddPet(Model model) {
    Pet pet = new Pet();
    model.addAttribute("pet", pet);
    model.addAttribute("clientes", clientsService.getClients());
    return petsPath + "add-pet";
  }

  @PostMapping("/pets/add")
  public String agregarMascota(@ModelAttribute("pet") Pet pet, @RequestParam("idCliente") Long idCliente) {
    clientsService.addPetToClient(idCliente, pet);
    return "redirect:/vet/pets";
  }

  // ----- Editar Mascota (UPDATE) -----

  @GetMapping("/pets/update/{id}")
  public String mostrarFormularioUpdatePet(@PathVariable("id") Long id, Model model) {
    Pet pet = petsService.getPetById(id);
    Client owner = pet.getOwner();
    model.addAttribute("pet", pet);
    model.addAttribute("owner", owner);
    model.addAttribute("clientes", clientsService.getClients());
    return petsPath + "add-pet";
  }

  // ----- Eliminar Mascota (DELETE) -----

  @GetMapping("/pets/delete/{id}")
  public String eliminarMascota(@PathVariable("id") Long id) {
    petsService.deletePet(id);
    clientsService.deletePetFromClient(id);
    return "redirect:/vet/pets";
  }

}
