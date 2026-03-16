package com.example.biskit.service.Clients;

import java.util.Collection;
import com.example.biskit.entities.Client;
import com.example.biskit.entities.pets.Pet;

import java.util.List;

public interface ClientsService {

  public Collection<Client> getClients();

  public Client getClientById(Long id);

  public void addClient(Client client);

  public void updateClient(Client client);

  public void deleteClient(Long id);

  public List<Pet> getPetsByClientId(Long clientId);

  public void addPetToClient(Long clientId, Pet pet);

  public void deletePetFromClient(Long petId);

  public boolean autenticarClient(String usuario, String contrasena);

  public Client findByUsuario(String usuario);

  public boolean existeCedula(String cedula);

  public boolean existeCorreo(String usuario);
}
