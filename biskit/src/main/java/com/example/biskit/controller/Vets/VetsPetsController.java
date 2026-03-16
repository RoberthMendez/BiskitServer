package com.example.biskit.controller.Vets;

import java.util.HashMap;
import java.util.Map;
import java.sql.Date;
import java.text.SimpleDateFormat;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
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

  @Autowired
  private VetService vetService;

  @Autowired
  private DrogasService drogasService;

  @Autowired
  private TratamientosService tratamientosService;

  private static final String petsPath = "vet/pets/";

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
  public String mostrarFormularioAddPet(@RequestParam(required = false) String error, Model model) {

    if (error != null) {
      if (error.equals("1")) {
        model.addAttribute("error", "Debes ingresar el nombre de la mascota");
      } else if (error.equals("2")) {
        model.addAttribute("error", "Debes seleccionar un cliente para la mascota");
      } else if (error.equals("3")) {
        model.addAttribute("error", "Debes seleccionar una especie para la mascota");
      } else if (error.equals("4")) {
        model.addAttribute("error", "Debes seleccionar una raza para la mascota");
      } else if (error.equals("5")) {
        model.addAttribute("error", "Debes seleccionar una enfermedad para la mascota");
      } else if (error.equals("6")) {
        model.addAttribute("error", "Debes seleccionar la fecha de nacimiento de la mascota");
      } else if (error.equals("7")) {
        model.addAttribute("error", "Debes ingresar la URL de la foto de la mascota");
      } else if (error.equals("8")) {
        model.addAttribute("error", "Debes ingresar un peso válido para la mascota");
      }
    }
    Pet pet = new Pet();
    pet.setFechaNacimiento(null);
    model.addAttribute("pet", pet);
    model.addAttribute("clientes", clientsService.getClients());
    model.addAttribute("especies", especieService.getAllEspecies());
    model.addAttribute("razas", razaService.getAllRazas());
    model.addAttribute("enfermedades", enfermedadService.getAllEnfermedades());
    return petsPath + "add-pet";
  }

  @PostMapping("/pets/add")
  public String agregarMascota(@ModelAttribute("pet") Pet pet,
      @RequestParam(name = "idCliente", required = false) Long idCliente,
      @RequestParam(name = "idEspecie", required = false) Long idEspecie,
      @RequestParam(name = "idRaza", required = false) Long idRaza,
      @RequestParam(name = "idEnfermedad", required = false) Long idEnfermedad) {

    if (pet.getNombre() == null || pet.getNombre().trim().isEmpty()) {
      return "redirect:/vet/pets/add?error=1";
    } else if (idCliente == null) {
      return "redirect:/vet/pets/add?error=2";
    } else if (idEspecie == null) {
      return "redirect:/vet/pets/add?error=3";
    } else if (idRaza == null) {
      return "redirect:/vet/pets/add?error=4";
    } else if (idEnfermedad == null) {
      return "redirect:/vet/pets/add?error=5";
    } else if (pet.getFechaNacimiento() == null) {
      return "redirect:/vet/pets/add?error=6";
    } else if (pet.getURLFoto() == null || pet.getURLFoto().trim().isEmpty()) {
      return "redirect:/vet/pets/add?error=7";
    } else if (pet.getPeso() == 0 || pet.getPeso() <= 0) {
      return "redirect:/vet/pets/add?error=8";

    }

    pet = petsService.asignarRelacionesDePetPorIds(pet, idEspecie, idRaza, idEnfermedad);
    clientsService.addPetToClient(idCliente, pet);
    return "redirect:/vet/pets";
  }

  // ----- Agregar Enfermedad -----
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

  // Añadir Raza

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
    model.addAttribute("pet", pet);
    model.addAttribute("clientes", clientsService.getClients());
    model.addAttribute("especies", especieService.getAllEspecies());
    model.addAttribute("razas", razaService.getAllRazas());
    model.addAttribute("enfermedades", enfermedadService.getAllEnfermedades());
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

}
