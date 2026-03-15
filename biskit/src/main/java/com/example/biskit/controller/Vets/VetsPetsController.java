package com.example.biskit.controller.Vets;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.biskit.service.Clients.ClientsService;
import com.example.biskit.service.Pets.PetsService;
import com.example.biskit.service.Pets.Especie.EspecieService;
import com.example.biskit.service.Pets.Raza.RazaService;
import com.example.biskit.service.Pets.Enfermedad.EnfermedadService;

import com.example.biskit.entities.Client;
import com.example.biskit.entities.pets.Especie;
import com.example.biskit.entities.pets.Enfermedad;
import com.example.biskit.entities.pets.Pet;
import com.example.biskit.entities.pets.Raza;

@Controller
@RequestMapping("/vet")
public class VetsPetsController {

  @Autowired
  private PetsService petsService;

  @Autowired
  private ClientsService clientsService;

  @Autowired
  private EspecieService especieService;

  @Autowired
  private RazaService razaService;

  @Autowired
  private EnfermedadService enfermedadService;

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
    model.addAttribute("especies", especieService.getAllEspecies());
    model.addAttribute("razas", razaService.getAllRazas());
    model.addAttribute("enfermedades", enfermedadService.getAllEnfermedades());
    return petsPath + "add-pet";
  }

  @PostMapping("/pets/add")
  public String agregarMascota(@ModelAttribute("pet") Pet pet, @RequestParam("idCliente") Long idCliente,
      @RequestParam("idEspecie") Long idEspecie, @RequestParam("idRaza") Long idRaza,
      @RequestParam("idEnfermedad") Long idEnfermedad) {
    pet = petsService.asignarRelacionesDePetPorIds(pet, idEspecie, idRaza, idEnfermedad);
    clientsService.addPetToClient(idCliente, pet);
    return "redirect:/vet/pets";
  }

  @PostMapping("/pets/enfermedades/add")
  @ResponseBody
  public Map<String, Object> agregarEnfermedad(@RequestParam("nombre") String nombre) {
    String nombreNormalizado = nombre == null ? "" : nombre.trim();
    Map<String, Object> response = new HashMap<>();

    if (nombreNormalizado.isEmpty()) {
      response.put("ok", false);
      response.put("message", "El nombre de la enfermedad es obligatorio");
      return response;
    }

    Enfermedad enfermedad = enfermedadService.getEnfermedadByNombre(nombreNormalizado);
    if (enfermedad == null) {
      enfermedad = Enfermedad.builder().nombre(nombreNormalizado).build();
      enfermedadService.saveEnfermedad(enfermedad);
    }

    response.put("ok", true);
    response.put("id", enfermedad.getId());
    response.put("nombre", enfermedad.getNombre());
    return response;
  }

  @PostMapping("/pets/razas/add")
  @ResponseBody
  public Map<String, Object> agregarRaza(@RequestParam("nombre") String nombre,
      @RequestParam("idEspecie") Long idEspecie) {
    String nombreNormalizado = nombre == null ? "" : nombre.trim();
    Map<String, Object> response = new HashMap<>();

    if (nombreNormalizado.isEmpty()) {
      response.put("ok", false);
      response.put("message", "El nombre de la raza es obligatorio");
      return response;
    }

    if (idEspecie == null) {
      response.put("ok", false);
      response.put("message", "Selecciona una especie antes de agregar la raza");
      return response;
    }

    Especie especie = especieService.getEspecieById(idEspecie);
    if (especie == null) {
      response.put("ok", false);
      response.put("message", "La especie seleccionada no existe");
      return response;
    }

    Raza raza = razaService.getRazaByNombre(nombreNormalizado);
    if (raza == null) {
      raza = Raza.builder().nombre(nombreNormalizado).especie(especie).build();
      razaService.saveRaza(raza);
    }

    response.put("ok", true);
    response.put("id", raza.getId());
    response.put("nombre", raza.getNombre());
    response.put("idEspecie", raza.getEspecie().getId());
    return response;
  }

  // ----- Editar Mascota (UPDATE) -----

  @GetMapping("/pets/update/{id}")
  public String mostrarFormularioUpdatePet(@PathVariable("id") Long id, Model model) {
    Pet pet = petsService.getPetById(id);
    Client owner = pet.getOwner();
    model.addAttribute("pet", pet);
    model.addAttribute("owner", owner);
    model.addAttribute("clientes", clientsService.getClients());
    model.addAttribute("especies", especieService.getAllEspecies());
    model.addAttribute("razas", razaService.getAllRazas());
    model.addAttribute("enfermedades", enfermedadService.getAllEnfermedades());
    return petsPath + "add-pet";
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
