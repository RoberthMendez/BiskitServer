package com.example.biskit.service.Clients;

import com.example.biskit.entities.Client;
import com.example.biskit.entities.Credenciales;
import com.example.biskit.entities.Pets.Pet;
import com.example.biskit.errors.NoExiste.ClientNoExisteException;
import com.example.biskit.errors.YaExiste.ClienteYaExisteException;
import com.example.biskit.repo.ClientsRepo;
import com.example.biskit.security.CustomUserDetailService;
import com.example.biskit.service.Credenciales.CorreosService;
import com.example.biskit.service.Credenciales.CredencialesService;
import com.example.biskit.service.Pets.PetsService;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ClientsImpl implements ClientsService {

  private static final Logger logger = LoggerFactory.getLogger(ClientsImpl.class);

  @Autowired
  private ClientsRepo clientsRepo;

  @Autowired
  private PetsService petsService;

  @Autowired
  private CredencialesService credencialesService;

  @Autowired
  private CorreosService correosService;

  @Autowired
  private CustomUserDetailService userDetailsService;

  @Override
  public List<Client> getClients() {
    return clientsRepo.findAll();
  }

  @Override
  public Client addClient(Client client) {
    if (client.getCorreo() != null && credencialesService.existeUsuario(client.getCorreo())) {
      throw new ClienteYaExisteException(client.getCorreo());
    }

    Credenciales credenciales = userDetailsService.clientToCredenciales(client);
    credencialesService.addCredenciales(credenciales);
    client.setCredenciales(credenciales);

    Client clientGuardado = clientsRepo.save(client);
    try {
      correosService.enviarBienvenida(client);
    } catch (Exception e) {
      logger.warn("No se pudo enviar el correo de bienvenida al cliente {}", client.getCorreo(), e);
    }
    return clientGuardado;
  }

  @Override
  @Transactional
  public Client updateClient(Client client) {
    Client clientExistente = clientsRepo
      .findById(client.getId())
      .orElseThrow(() -> new ClientNoExisteException(client.getId()));

    clientExistente.setNombre(client.getNombre());
    clientExistente.setCedula(client.getCedula());
    clientExistente.setCorreo(client.getCorreo());
    clientExistente.setCelular(client.getCelular());
    clientExistente.getCredenciales().setUsername(client.getCorreo());

    Client guardado = clientsRepo.save(clientExistente);
    return guardado;
  }

  @Override
  @Transactional
  public void deleteClient(Long id) {
    Client client = clientsRepo.findById(id).orElseThrow(() -> new ClientNoExisteException(id));

    List<Pet> pets = client.getPets() == null ? List.of() : new ArrayList<>(client.getPets());

    for (Pet pet : pets) {
      petsService.deletePet(pet.getId());
    }

    if (client.getPets() != null) {
      client.getPets().clear();
    }

    Long credencialesId =
      client.getCredenciales() == null ? null : client.getCredenciales().getId();
    client.setCredenciales(null);
    clientsRepo.saveAndFlush(client);

    if (Objects.nonNull(credencialesId)) {
      credencialesService.deleteCredenciales(credencialesId);
    }

    clientsRepo.delete(client);
  }

  @Override
  public List<Pet> getPetsByClientId(Long clientId) {
    Client client = clientsRepo
      .findById(clientId)
      .orElseThrow(() -> new ClientNoExisteException(clientId));
    // Obtener las versiones actualizadas de las mascotas desde PetsRepo
    List<Pet> updatedPets = new ArrayList<>();
    if (client.getPets() == null) {
      return updatedPets;
    }
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
    Client dueñoIngresado = clientsRepo
      .findById(clientId)
      .orElseThrow(() -> new ClientNoExisteException(clientId));

    if (pet.getId() == null) {
      if (dueñoIngresado.getPets() != null) {
        dueñoIngresado.getPets().add(pet);
      }
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
    return clientsRepo
      .findAll()
      .stream()
      .anyMatch(
        client ->
          client.getCredenciales().getUsername().equals(usuario) &&
          client.getCredenciales().getPassword().equals(contrasena)
      );
  }

  @Override
  public Client findByUsuario(String usuario) {
    return clientsRepo
      .findAll()
      .stream()
      .filter(client -> client.getCredenciales().getUsername().equals(usuario))
      .findFirst()
      .orElse(null);
  }

  @Override
  public Client getClientById(Long id) {
    return clientsRepo.findById(id).orElseThrow(() -> new ClientNoExisteException(id));
  }

  @Override
  public boolean existeCedula(String cedula) {
    return clientsRepo
      .findAll()
      .stream()
      .anyMatch(client -> client.getCedula().equals(cedula));
  }

  @Override
  public boolean existeCorreo(String correo) {
    return clientsRepo
      .findAll()
      .stream()
      .anyMatch(client -> client.getCorreo().equals(correo));
  }

  @Override
  public Long getClientsCount() {
    return clientsRepo.count();
  }
}
