package com.example.biskit.controller.Vets;

import java.time.LocalDate;
import java.util.List;

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
import com.example.biskit.service.Vets.VetService;
import com.example.biskit.service.Tratamientos.DrogasService;
import com.example.biskit.entities.Tratamiento;
import com.example.biskit.service.Tratamientos.TratamientosService;
import com.example.biskit.entities.Client;
import com.example.biskit.entities.pets.Pet;

@Controller
@RequestMapping("/vet")
public class VetsPetsController {

  @Autowired
  private PetsService petsService;

  @Autowired
  private ClientsService clientsService;

  @Autowired
  private VetService vetService;

  @Autowired
  private DrogasService drogasService;

  @Autowired
  private TratamientosService tratamientosService;

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

  // ----- Agregar Tratamiento a Mascota -----

  @GetMapping("/pets/tratamiento/add")
  public String mostrarFormularioAddTratamiento(@RequestParam(required = false) Long id, Model model) {

    Tratamiento tratamiento = new Tratamiento();

    if (id != null) {
      tratamiento.setPet(petsService.getPetById(id));
    }

    if (tratamiento.getId() == null) {
      tratamiento.setFecha(LocalDate.now());
    }

    model.addAttribute("pets", petsService.getPets());
    model.addAttribute("vets", vetService.getVets());
    model.addAttribute("drogas", drogasService.getDrogas());
    model.addAttribute("tratamiento", tratamiento);

    return petsPath + "add-tratamiento";
  }

  @PostMapping("/pets/tratamiento/add")
  public String agregarTratamiento(@ModelAttribute("tratamiento") Tratamiento tratamiento,
      @RequestParam List<Long> drogasIds) {

    tratamientosService.addTratamiento(tratamiento, drogasIds);

    return "redirect:/vet/pets/" + tratamiento.getPet().getId();
  }

  // ----- Editar Tratamiento de Mascota -----

  @GetMapping("/pets/tratamiento/update/{id}")
  public String mostrarFormularioUpdateTratamiento(@PathVariable("id") Long id, Model model) {
    Tratamiento tratamiento = tratamientosService.getTratamientoById(id);

    model.addAttribute("pets", petsService.getPets());
    model.addAttribute("vets", vetService.getVets());
    model.addAttribute("drogas", drogasService.getDrogas());
    model.addAttribute("tratamiento", tratamiento);
    return petsPath + "add-tratamiento";
  }

  // ----- Mostrar tratamiento -----

  @GetMapping("/pets/tratamiento/{id}")
  public String mostrarTratamiento(@PathVariable("id") Long id, Model model) {
    Tratamiento tratamiento = tratamientosService.getTratamientoById(id);
    model.addAttribute("tratamiento", tratamiento);
    return petsPath + "info-tratamiento";
  }

  // ----- Eliminar Mascota (DELETE) -----

  /*
   * @GetMapping("/pets/delete/{id}")
   * public String eliminarMascota(@PathVariable("id") Long id) {
   * petsService.deletePet(id);
   * clientsService.deletePetFromClient(id);
   * return "redirect:/vet/pets";
   * }
   * //
   */

  // @PostMapping("/pets/cambiar-estado/{id}")
  // public String postMethodName(@PathVariable("id") Long id, @RequestParam(name
  // = "estado", defaultValue = "false") boolean estado) {
  // petsService.cambiarEstadoPet(id, estado);
  // return "redirect:/vet/pets";
  // }

}
