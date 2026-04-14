package com.example.biskit.controller.Vets;

import java.sql.Date;
import java.text.SimpleDateFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.biskit.service.Clients.ClientsService;
import com.example.biskit.service.Pets.PetsService;
import com.example.biskit.service.Pets.Especie.EspecieService;
import com.example.biskit.service.Pets.Raza.RazaService;
import com.example.biskit.service.Pets.Enfermedad.EnfermedadService;

import com.example.biskit.service.Vets.VetService;
import com.example.biskit.service.Tratamientos.DrogasService;
import com.example.biskit.entities.Tratamiento;
import com.example.biskit.service.Tratamientos.TratamientosService;
import com.example.biskit.entities.Client;
import com.example.biskit.entities.pets.Especie;
import com.example.biskit.entities.pets.Enfermedad;
import com.example.biskit.entities.pets.Pet;
import com.example.biskit.entities.pets.Raza;
import com.example.biskit.entities.vets.Vet;

@RestController
@RequestMapping("/vet")
@CrossOrigin(origins = "http://localhost:4200")
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

  @Autowired
  private VetService vetService;

  @Autowired
  private DrogasService drogasService;

  @Autowired
  private TratamientosService tratamientosService;

  private static final String petsPath = "vet/pets/";

  // ================== MASCOTA ==================

  // ----- Añadir Mascota (CREATE) -----
  @PostMapping("/pets/add")
  public void agregarMascota(@RequestBody Pet pet) {
    pet = petsService.asignarRelacionesDePetPorIds(pet);
    clientsService.addPetToClient(pet.getOwner().getId(), pet);
    petsService.addPet(pet);
  }

  // ----- Mostrar Mascotas (READ) -----
  @GetMapping("/pets")
  public List<Pet> mostrarMascotas() {
    return petsService.getPets();
  }

  // ----- Mostrar Mascota (READ) -----
  @GetMapping("/pets/{id}")
  public Pet mostrarMascota(@PathVariable("id") Long id) {
    return petsService.getPetById(id);
  }

  // ----- Editar Mascota (UPDATE) -----
  @PutMapping("/pets/update/{id}")
  public void updatePet(@PathVariable("id") Long id, @RequestBody Pet pet) {
    pet = petsService.asignarRelacionesDePetPorIds(pet);
    petsService.updatePet(pet);
  }

  // ----- Cambiar Estado de Mascota (DELETE) -----
  @PatchMapping("/pets/update-estado/{id}")
  public void cambiarEstadoMascota(@PathVariable("id") Long id, @RequestBody Map<String, Boolean> body) {
    petsService.cambiarEstadoMascota(id, body.get("estado"));
  }


  // Añadir Raza

  @PostMapping("/pets/razas/add")
  @ResponseBody
  public void agregarRaza(@RequestBody Raza raza) {

    String nombreRaza = raza.getNombre() == null ? "" : raza.getNombre().trim();
    String nombreEspecie = raza.getEspecie() == null ? "" : raza.getEspecie().getNombre().trim();

    Raza razaExistente = razaService.getRazaByNombre(nombreRaza);
    Especie especie = especieService.getEspecieByNombre(nombreEspecie);
  
    if (razaExistente == null && especie != null) {
      raza = Raza.builder().nombre(nombreRaza).especie(especie).build();
      razaService.saveRaza(raza);
    }
  } 












  // ----- Tratamientos de Mascota -----
  @GetMapping("pets/tratamientos/{id}")
  public List<Tratamiento> getTratamientosByPetId(@PathVariable("id") Long id) {
      return tratamientosService.getTratamientosByPetId(id);
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
    Pet pet = tratamiento.getPet();
    Client owner = pet.getOwner();
    Vet vet = tratamiento.getVet();
    model.addAttribute("tratamiento", tratamiento);
    model.addAttribute("pet", pet);
    model.addAttribute("owner", owner);
    model.addAttribute("veterinario", vet);
    return petsPath + "info-tratamiento";
  }

  // ----- Eliminar Tratamiento -----

  @PostMapping("/pets/tratamiento/delete/{id}")
  public String eliminarTratamiento(@PathVariable("id") Long id) {
    Tratamiento tratamiento = tratamientosService.getTratamientoById(id);
    Long petId = tratamiento.getPet().getId();
    tratamientosService.deleteTratamiento(id);
    return "redirect:/vet/pets/" + petId;
  }

  /*
   * Configura en Spring MVC cómo convertir las fechas que llegan como texto desde
   * un formulario (formato yyyy-MM-dd) en objetos Date de Java, validando que
   * tengan un formato correcto.
   */
  @InitBinder // "Ejecuta este método antes de hacer el binding de los datos del formulario a
              // los objetos Java
  public void initBinder(WebDataBinder binder) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    dateFormat.setLenient(false);
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
  }

}
