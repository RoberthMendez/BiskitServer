package com.example.biskit.service.Clients;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.biskit.entities.Client;
import com.example.biskit.entities.Estado;
import com.example.biskit.entities.Pet;

import com.example.biskit.repo.ClientsRepo;
import com.example.biskit.service.Pets.PetsImpl;

@Service
public class ClientsImpl implements ClientsService {

  @Autowired
  private ClientsRepo clientsRepo;

  @Autowired
  private PetsImpl petsService;

  @Override
  public Collection<Client> getClients() {
    return clientsRepo.findAll();
  }

  @Override
  public Client getClientById(Long id) {
    return clientsRepo.findById(id).orElse(null);
  }

  @Override
  public void addClient(Client client) {
    clientsRepo.save(client);
  }

  @Override
  public void updateClient(Client client) {
    clientsRepo.save(client);
  }

  @Override
  public void deleteClient(Long id) {
    // clientsRepo.deleteById(id);
    Client client = clientsRepo.findById(id).orElseThrow();
    for (Pet pet : client.getPets()) {
      petsService.deletePet(pet.getId());
    }
    client.getPets().clear();
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
      pet.setEstado(Estado.ACTIVO);
    }
    pet.setOwner(dueñoIngresado);

    petsService.addPet(pet);

  }

  @Override
  public void deletePetFromClient(Long petId) {
    petsService.deletePet(petId);
  }

  @Override
  public boolean autenticarClient(String usuario, String contrasena) {
    return clientsRepo.findAll().stream()
        .anyMatch(client -> client.getUsuario().equals(usuario) && client.getPassword().equals(contrasena));
  }

  @Override
  public Client findByUsuario(String usuario) {
    return clientsRepo.findAll().stream()
        .filter(client -> client.getUsuario().equals(usuario))
        .findFirst()
        .orElse(null);
  }

}