package com.example.biskit.service.Clients;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.biskit.entities.Client;
import com.example.biskit.entities.Credenciales;
import com.example.biskit.entities.pets.Pet;
import com.example.biskit.repo.ClientsRepo;
import com.example.biskit.service.Pets.PetsService;
import com.example.biskit.service.Credenciales.CredencialesService;

import com.example.biskit.errors.ClientNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class ClientsImpl implements ClientsService {

  @Autowired
  private ClientsRepo clientsRepo;

  @Autowired
  private PetsService petsService;

  @Autowired
  private CredencialesService credencialesService;

  @Override
  public List<Client> getClients() {
    return clientsRepo.findAll();
  }

  @Override
  public void addClient(Client client) {

    Credenciales credenciales = Credenciales.builder()
        .usuario(client.getCorreo())
        .password(client.getCedula())
        .build();

    credencialesService.addCredenciales(credenciales);
    client.setCredenciales(credenciales);
    clientsRepo.save(client);
  }

  @Override
  public void updateClient(Client client) {
    clientsRepo.save(client);
  }

  @Override
  @Transactional
  public void deleteClient(Long id) {

    Client client = clientsRepo.findById(id).orElseThrow(() -> new ClientNotFoundException(id));

    List<Pet> pets = client.getPets() == null ? List.of() : new ArrayList<>(client.getPets());
    
    for (Pet pet : pets) {
      petsService.deletePet(pet.getId());
    }

    if (client.getPets() != null) {
      client.getPets().clear();
    }

    Long credencialesId = client.getCredenciales() == null ? null : client.getCredenciales().getId();
    client.setCredenciales(null);
    clientsRepo.saveAndFlush(client);

    if (Objects.nonNull(credencialesId)) {
      credencialesService.deleteCredenciales(credencialesId);
    }

    clientsRepo.delete(client);

  }

  @Override
  public List<Pet> getPetsByClientId(Long clientId) {
    Client client = clientsRepo.findById(clientId).orElse(null);
    // Obtener las versiones actualizadas de las mascotas desde PetsRepo
    List<Pet> updatedPets = new ArrayList<>();
    for (Pet pet : client.getPets()) {
      Pet updatedPet = petsService.getPetById(pet.getId());
      if (updatedPet != null) {
        updatedPets.add(updatedPet);
      }
    }
    return updatedPets;
  }

  @Override
  public void addPetToClient(Long clientId, Pet pet) {

    Client dueñoIngresado = clientsRepo.findById(clientId).orElse(null);

    if (pet.getId() == null) {
      dueñoIngresado.getPets().add(pet);
      pet.setEstado(true);
    }
    pet.setOwner(dueñoIngresado);

  }

  @Override
  public void deletePetFromClient(Long petId) {
    petsService.deletePet(petId);
  }

  @Override
  public boolean autenticarClient(String usuario, String contrasena) {
    return clientsRepo.findAll().stream()
        .anyMatch(client -> client.getCredenciales().getUsuario().equals(usuario)
            && client.getCredenciales().getPassword().equals(contrasena));
  }

  @Override
  public Client findByUsuario(String usuario) {
    return clientsRepo.findAll().stream()
        .filter(client -> client.getCredenciales().getUsuario().equals(usuario))
        .findFirst()
        .orElse(null);
  }

  @Override
  public Client getClientById(Long id) {
    return clientsRepo.findById(id)
        .orElseThrow(() -> new ClientNotFoundException(id));
  }

  @Override
  public boolean existeCedula(String cedula) {
    return clientsRepo.findAll().stream()
        .anyMatch(client -> client.getCedula().equals(cedula));
  }

  @Override
  public boolean existeCorreo(String correo) {
    return clientsRepo.findAll().stream()
        .anyMatch(client -> client.getCorreo().equals(correo));
  }
}