package com.example.biskit.service.Vets;

import com.example.biskit.entities.vets.Especialidad;
import com.example.biskit.entities.vets.Vet;
import com.example.biskit.repo.vets.VetsRepo;
import com.example.biskit.service.Credenciales.CredencialesService;

import org.springframework.stereotype.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class VetImpl implements VetService {

    @Autowired
    private VetsRepo vetsRepo;

    @Autowired
    private EspecialidadesService especialidadesService;

    @Autowired
    private CredencialesService credencialesService;

    @Override
    public List<Vet> getVets() {
        return vetsRepo.findAll();
    }

    @Override
    public Vet getVetById(Long id) {
        return vetsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró veterinario con id: " + id));
    }

    @Override
    public void addVet(Vet vet) {

        if (vet.getId() == null) {
            vet.setEstado(true);
        }

        if (vet.getCredenciales().getId() == null) {
            vet.getCredenciales().setUsuario(vet.getCorreo());
            vet.getCredenciales().setPassword(vet.getCedula());
            credencialesService.addCredenciales(vet.getCredenciales());
        }

        Especialidad especialidad = especialidadesService.getEspecialidadById(vet.getEspecialidad().getId());
        vet.setEspecialidad(especialidad);
        vetsRepo.save(vet);
    }

    @Override
    public void saveVet(Vet vet) {
        vetsRepo.save(vet);
    }

    @Override
    public boolean autenticarVet(String usuario, String contrasena) {
        return vetsRepo.findAll().stream()
                .filter(vet -> vet.getCredenciales() != null)
                .anyMatch(vet -> usuario.equals(vet.getCredenciales().getUsuario())
                        && contrasena.equals(vet.getCredenciales().getPassword()));
    }

    @Override
    public Vet findByUsuario(String usuario) {
        return vetsRepo.findAll().stream()
                .filter(vet -> vet.getCredenciales() != null)
                .filter(vet -> usuario.equals(vet.getCredenciales().getUsuario()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Long getVetsInactivosCount() {
        return vetsRepo.countByEstadoFalse();
    }

    @Override
    public Long getVetsActivosCount() {
        return vetsRepo.countByEstadoTrue();
    }
}
