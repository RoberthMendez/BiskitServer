package com.example.biskit.controller.Vets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.biskit.entities.vets.Vet;
import com.example.biskit.service.Vets.EspecialidadesService;
import com.example.biskit.service.Vets.VetService;
import com.example.biskit.entities.Credenciales;
import com.example.biskit.entities.vets.Especialidad;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/admin")
public class AdminVetsController {
    
    @Autowired
    private VetService vetService;

    @Autowired
    private EspecialidadesService especialidadesService;

    private static final String vetsPath = "admin/vet/";

    // ----- Añadir Veterinario (CREATE) -----
    @GetMapping("/vets/add")
    public String mostrarFormularioNuevoVeterinario(Model model) {
        Vet vet = new Vet();
        vet.setCredenciales(new Credenciales());

        model.addAttribute("especialidades", especialidadesService.getEspecialidades());
        model.addAttribute("vet", vet);
        return vetsPath + "add-vet";
    }

    @PostMapping("/vets/add")
    public String agregarVeterinario(@ModelAttribute("vet") Vet vet) {
        
        vetService.addVet(vet);
        
        return "redirect:/admin/vets";
    }

    // ----- Ver Veterinario -----
    @GetMapping("/vets")
    public String mostrarVeterinarios(Model model) {
        model.addAttribute("vets", vetService.getVets());
        return vetsPath + "vets";
    }

    // ----- Actualizar Veterinario (UPDATE) -----
    @GetMapping("/vets/update/{id}")
    public String mostrarFormularioActualizacionVeterinario(@PathVariable("id") Long id, Model model) {
        Vet vet = vetService.getVetById(id);
        model.addAttribute("especialidades", especialidadesService.getEspecialidades());
        model.addAttribute("vet", vet);
        return vetsPath + "add-vet";
    }


    // ----- Agregar Especialidad (CREATE) -----
    @PostMapping("/vets/especialidades/add")
    @ResponseBody
    public Map<String, Object> agregarEspecialidad(@org.springframework.web.bind.annotation.RequestParam("nombre") String nombre) {
        String nombreNormalizado = nombre == null ? "" : nombre.trim();
        Map<String, Object> response = new HashMap<>();

        if (nombreNormalizado.isEmpty()) {
            response.put("ok", false);
            response.put("message", "El nombre de la especialidad es obligatorio");
            return response;
        }

        Especialidad especialidad = especialidadesService.getEspecialidadByNombre(nombreNormalizado);
        if (especialidad == null) {
            especialidad = Especialidad.builder().nombre(nombreNormalizado).build();
            especialidadesService.addEspecialidad(especialidad);
        }

        response.put("ok", true);
        response.put("id", especialidad.getId());
        response.put("nombre", especialidad.getNombre());
        return response;
    }

}
