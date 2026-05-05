package com.example.biskit.repository;

import java.sql.Date;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.biskit.entities.Client;
import com.example.biskit.entities.pets.Enfermedad;
import com.example.biskit.entities.pets.Especie;
import com.example.biskit.entities.pets.Pet;
import com.example.biskit.entities.pets.Raza;
import com.example.biskit.repo.ClientsRepo;
import com.example.biskit.repo.pets.EnfermedadRepo;
import com.example.biskit.repo.pets.EspecieRepo;
import com.example.biskit.repo.pets.PetsRepo;
import com.example.biskit.repo.pets.RazaRepo;

@DataJpaTest
@RunWith(SpringRunner.class)
public class PetsRepoTest {

        @Autowired
        private PetsRepo petsRepo;

        @Autowired
        private ClientsRepo clientsRepo;

        @Autowired
        private EspecieRepo especiesRepo;

        @Autowired
        private RazaRepo razaRepo;

        @Autowired
        private EnfermedadRepo enfermedadRepo;

        private Client juan;
        private Client maria;
        private Raza labrador;
        private Raza siames;
        private Enfermedad parvovirus;
        private Enfermedad rinotraqueitis;
        private Pet firulais;

        @BeforeEach
        public void setUp() {
                juan = clientsRepo.save(Client.builder()
                                .nombre("Juan")
                                .cedula("1000000001")
                                .correo("juan@test.com")
                                .celular("3000000001")
                                .build());

                maria = clientsRepo.save(Client.builder()
                                .nombre("Maria")
                                .cedula("1000000002")
                                .correo("maria@test.com")
                                .celular("3000000002")
                                .build());

                Especie perro = especiesRepo.save(Especie.builder()
                                .nombre("Perro")
                                .build());

                Especie gato = especiesRepo.save(Especie.builder()
                                .nombre("Gato")
                                .build());

                labrador = razaRepo.save(Raza.builder()
                                .nombre("Labrador")
                                .especie(perro)
                                .build());

                siames = razaRepo.save(Raza.builder()
                                .nombre("Siames")
                                .especie(gato)
                                .build());

                parvovirus = enfermedadRepo.save(Enfermedad.builder()
                                .nombre("Parvovirus")
                                .build());

                rinotraqueitis = enfermedadRepo.save(Enfermedad.builder()
                                .nombre("Rinotraqueitis")
                                .build());

                firulais = petsRepo.save(Pet.builder()
                                .nombre("Firulais")
                                .fechaNacimiento(Date.valueOf("2020-01-01"))
                                .raza(labrador)
                                .enfermedad(parvovirus)
                                .owner(juan)
                                .estado(true)
                                .build());

                petsRepo.save(Pet.builder()
                                .nombre("Michi")
                                .fechaNacimiento(Date.valueOf("2019-05-10"))
                                .raza(siames)
                                .enfermedad(rinotraqueitis)
                                .owner(maria)
                                .estado(true)
                                .build());

                petsRepo.save(Pet.builder()
                                .nombre("Rex")
                                .fechaNacimiento(Date.valueOf("2021-03-15"))
                                .raza(labrador)
                                .enfermedad(rinotraqueitis)
                                .owner(maria)
                                .estado(true)
                                .build());

                petsRepo.save(Pet.builder()
                                .nombre("Luna")
                                .fechaNacimiento(Date.valueOf("2018-07-20"))
                                .raza(siames)
                                .enfermedad(parvovirus)
                                .owner(juan)
                                .estado(true)
                                .build());
        }

        /* PRUEBAS DEL CRUD DEL REPOSITORIO DE PET */

        // CREATE
        @Test
        public void PetsRepo_save_Pet() {
                Pet pet = Pet.builder()
                                .nombre("Fido")
                                .fechaNacimiento(Date.valueOf("2020-01-01"))
                                .raza(labrador)
                                .enfermedad(parvovirus)
                                .owner(juan)
                                .estado(true)
                                .build();

                Pet savedPet = petsRepo.save(pet);

                Assertions.assertThat(savedPet).isNotNull();
                Assertions.assertThat(savedPet.getNombre()).isEqualTo("Fido");
                Assertions.assertThat(savedPet.getRaza().getNombre()).isEqualTo("Labrador");
                Assertions.assertThat(savedPet.getEnfermedad().getNombre()).isEqualTo("Parvovirus");
                Assertions.assertThat(savedPet.getOwner().getNombre()).isEqualTo("Juan");
                Assertions.assertThat(savedPet.getFechaNacimiento()).isEqualTo(Date.valueOf("2020-01-01"));
                Assertions.assertThat(savedPet.isEstado()).isTrue();
        }

        //CREATE que es incorrecto por fecha
        @Test
        public void PetsRepo_save_PetWithoutFechaNacimiento_ThrowsException() {
                Pet pet = Pet.builder()
                                .nombre("Fido")
                                .fechaNacimiento(null) //fecha de nacimiento no puede ser nula
                                .raza(labrador)
                                .enfermedad(parvovirus)
                                .owner(juan)
                                .estado(true)
                                .build();

                Assertions.assertThatThrownBy(() -> petsRepo.save(pet))
                                .isInstanceOf(Exception.class);
        }


        //CREATE que es incorrecto por nombre muy largo
        @Test
        public void PetsRepo_save_PetWithNombreTooLong_ThrowsException() {
                Pet pet = Pet.builder()
                                .nombre("F".repeat(101)) //nombre no puede tener más de 100 caracteres
                                .fechaNacimiento(Date.valueOf("2020-01-01"))
                                .raza(labrador)
                                .enfermedad(parvovirus)
                                .owner(juan)
                                .estado(true)
                                .build();

                Assertions.assertThatThrownBy(() -> petsRepo.save(pet))
                                .isInstanceOf(Exception.class);
        }

        //CREATE que es incorrecto por nombre nulo
        @Test
        public void PetsRepo_save_PetWithoutNombre_ThrowsException() {
                Pet pet = Pet.builder()
                                .nombre(null) //nombre no puede ser nulo
                                .fechaNacimiento(null) 
                                .raza(labrador)
                                .enfermedad(parvovirus)
                                .owner(juan)
                                .estado(true)
                                .build();

                Assertions.assertThatThrownBy(() -> petsRepo.save(pet))
                                .isInstanceOf(Exception.class);
        }

        // FIND ALL (READ)
        @Test
        public void PetsRepo_findAll_NotEmptyList() {
                List<Pet> pets = petsRepo.findAll();

                Assertions.assertThat(pets).isNotEmpty();
                Assertions.assertThat(pets).hasSize(4);
                Assertions.assertThat(pets).extracting(Pet::getNombre).containsExactlyInAnyOrder("Firulais", "Michi",
                                "Rex",
                                "Luna");

                petsRepo.save(Pet.builder()
                                .nombre("Fido")
                                .fechaNacimiento(Date.valueOf("2020-01-01"))
                                .raza(labrador)
                                .enfermedad(parvovirus)
                                .owner(juan)
                                .estado(true)
                                .build());

                pets = petsRepo.findAll();
                Assertions.assertThat(pets).hasSize(5);
                Assertions.assertThat(pets).extracting(Pet::getNombre).containsExactlyInAnyOrder("Firulais", "Michi",
                                "Rex",
                                "Luna", "Fido");
        }

        /* FIND BY ID (READ) */
        @Test
        public void PetsRepo_findById_ReturnsPet() {
                Pet pet = petsRepo.findById(firulais.getId()).orElse(null);

                Assertions.assertThat(pet).isNotNull();
                Assertions.assertThat(pet.getNombre()).isEqualTo("Firulais");
                Assertions.assertThat(pet.getRaza().getNombre()).isEqualTo("Labrador");
                Assertions.assertThat(pet.getEnfermedad().getNombre()).isEqualTo("Parvovirus");
                Assertions.assertThat(pet.getOwner().getNombre()).isEqualTo("Juan");
                Assertions.assertThat(pet.getFechaNacimiento()).isEqualTo(Date.valueOf("2020-01-01"));
                Assertions.assertThat(pet.isEstado()).isTrue();
        }

        @Test
        public void PetsRepo_findById_ReturnsEmpty() {
                Pet pet = petsRepo.findById(-1L).orElse(null);

                Assertions.assertThat(pet).isNull();
        }

        /* DELETE BY ID (DELETE) */

        @Test
        public void PetsRepo_deleteById_RemovesPet() {
                petsRepo.deleteById(firulais.getId());
                Pet pet = petsRepo.findById(firulais.getId()).orElse(null);

                Assertions.assertThat(pet).isNull();
        }

        /* UPDATE BY NAME (UPDATE) */
        @Test
        public void PetsRepo_updateByName_Pet() {

                Pet pet = petsRepo.findById(firulais.getId()).orElse(null);
                pet.setNombre("Firulais modificado");
                Pet updatedPet = petsRepo.save(pet);

                Assertions.assertThat(updatedPet).isNotNull();
                Assertions.assertThat(updatedPet.getNombre()).isEqualTo("Firulais modificado");
        }
}
