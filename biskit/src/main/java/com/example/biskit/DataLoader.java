package com.example.biskit;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.h2.store.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.CellType;
import java.util.List;
import java.util.ArrayList;

import com.example.biskit.entities.Admin;
import com.example.biskit.entities.Client;
import com.example.biskit.entities.Credenciales;
import com.example.biskit.entities.Droga;
import com.example.biskit.entities.Tratamiento;
import com.example.biskit.entities.pets.Especie;
import com.example.biskit.entities.pets.Enfermedad;
import com.example.biskit.entities.pets.Pet;
import com.example.biskit.entities.pets.Raza;
import com.example.biskit.entities.vets.Especialidad;
import com.example.biskit.entities.vets.Vet;
import com.example.biskit.repo.AdminRepo;
import com.example.biskit.repo.ClientsRepo;
import com.example.biskit.repo.CredencialesRepo;
import com.example.biskit.repo.DrogasRepo;
import com.example.biskit.repo.TratamientosRepo;
import com.example.biskit.repo.pets.EspecieRepo;
import com.example.biskit.repo.pets.EnfermedadRepo;
import com.example.biskit.repo.pets.PetsRepo;
import com.example.biskit.repo.pets.RazaRepo;
import com.example.biskit.repo.vets.EspecialidadRepo;
import com.example.biskit.repo.vets.VetsRepo;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Component
@Transactional
public class DataLoader implements CommandLineRunner {

        @Autowired
        private ClientsRepo clientsRepo;

        @Autowired
        private PetsRepo petsRepo;

        @Autowired
        private EspecieRepo especieRepo;

        @Autowired
        private EnfermedadRepo enfermedadRepo;

        @Autowired
        private RazaRepo razaRepo;

        @Autowired
        private EspecialidadRepo especialidadRepo;

        @Autowired
        private CredencialesRepo credencialesRepo;

        @Autowired
        private VetsRepo vetsRepo;

        @Autowired
        private AdminRepo adminRepo;

        @Autowired
        private TratamientosRepo tratamientosRepo;

        @Autowired
        private DrogasRepo drogasRepo;

        @Override
        public void run(String... args) throws Exception {

                cargarEnfermedades();
                cargarEspecies();
                cargarRazas();
                cargarEspecialidades();
                cargarClientes();
                cargarMascotas();
                cargarVeterinarios();
                cargarAdministradores();
                cargarCredenciales();
                cargarTratamientos();
                cargarDrogas();
                relacionar();

        }

        public void cargarEnfermedades() {

                enfermedadRepo.save(Enfermedad.builder().nombre("Ninguna").build());
                enfermedadRepo.save(Enfermedad.builder().nombre("Parásitos").build());
                enfermedadRepo.save(Enfermedad.builder().nombre("Alergía").build());
                enfermedadRepo.save(Enfermedad.builder().nombre("Infección").build());
                enfermedadRepo.save(Enfermedad.builder().nombre("Artritis").build());
                enfermedadRepo.save(Enfermedad.builder().nombre("Diabetes").build());
                enfermedadRepo.save(Enfermedad.builder().nombre("Obesidad").build());
                enfermedadRepo.save(Enfermedad.builder().nombre("Fractura").build());
                enfermedadRepo.save(Enfermedad.builder().nombre("Dermatitis").build());
                enfermedadRepo.save(Enfermedad.builder().nombre("Otitis").build());
                enfermedadRepo.save(Enfermedad.builder().nombre("Gastritis").build());
                enfermedadRepo.save(Enfermedad.builder().nombre("Conjuntivitis").build());
                enfermedadRepo.save(Enfermedad.builder().nombre("Anemia").build());
                enfermedadRepo.save(Enfermedad.builder().nombre("Insuficiencia renal").build());
                enfermedadRepo.save(Enfermedad.builder().nombre("Problemas dentales").build());
                enfermedadRepo.save(Enfermedad.builder().nombre("Tos de las perreras").build());
                enfermedadRepo.save(Enfermedad.builder().nombre("Moquillo").build());
                enfermedadRepo.save(Enfermedad.builder().nombre("Parvovirus").build());
                enfermedadRepo.save(Enfermedad.builder().nombre("Leptospirosis").build());
                enfermedadRepo.save(Enfermedad.builder().nombre("Hepatitis").build());
                enfermedadRepo.save(Enfermedad.builder().nombre("Hipotiroidismo").build());
                enfermedadRepo.save(Enfermedad.builder().nombre("Cáncer").build());
                enfermedadRepo.save(Enfermedad.builder().nombre("Tumor benigno").build());
                enfermedadRepo.save(Enfermedad.builder().nombre("Luxación").build());
                enfermedadRepo.save(Enfermedad.builder().nombre("Problemas cardíacos").build());
                enfermedadRepo.save(Enfermedad.builder().nombre("Cálculos urinarios").build());
                enfermedadRepo.save(Enfermedad.builder().nombre("Asma").build());
                enfermedadRepo.save(Enfermedad.builder().nombre("Leucemia").build());
                enfermedadRepo.save(Enfermedad.builder().nombre("Inmunodeficiencia").build());
                enfermedadRepo.save(Enfermedad.builder().nombre("Problemas respiratorios").build());

        }

        public void cargarEspecies() {

                especieRepo.save(Especie.builder().nombre("Perro").build());
                especieRepo.save(Especie.builder().nombre("Gato").build());

        }

        public void cargarRazas() {

                Especie perro = especieRepo.findByNombre("Perro");
                Especie gato = especieRepo.findByNombre("Gato");
                razaRepo.save(Raza.builder().nombre("Lebrel afgano").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Beagle").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Labrador retriever").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Carlino").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Husky siberiano").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Cocker spaniel").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Terrier de Norwich").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Golden retriever").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Pastor de Shetland").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Terrier de Yorkshire").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Bóxer").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Dálmata").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Bullmastiff").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Akita").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Papillón").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Terrier americano").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Chihuahua").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Terrier australiano").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Dingo").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Kelpie australiano").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Malamute de Alaska").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Galgo italiano").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Otterhound").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Saluki").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Basenji").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Boyero de Flandes").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Border collie").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Komondor").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Leonberger").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Terranova").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Rottweiler").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Chow chow").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Weimaraner").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Whippet").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Terrier escocés").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("West Highland white terrier").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Vizsla").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Pekinés").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Pinscher miniatura").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Pointer alemán").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Caniche miniatura").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Caniche estándar").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Caniche toy").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Schnauzer miniatura").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Setter inglés").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Spaniel bretón").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Spaniel irlandés").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Terrier irlandés").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Terrier kerry blue").especie(perro).build());
                razaRepo.save(Raza.builder().nombre("Terrier lakeland").especie(perro).build());

                razaRepo.save(Raza.builder().nombre("Abisinio").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Egeo").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Bobtail americano").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Curl americano").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Americano de pelo corto").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Americano de pelo duro").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Mau árabe").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Mist australiano").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Balinés").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Bambino").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Bengalí").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Sagrado de Birmania").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Bombay").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Británico de pelo largo").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Británico de pelo corto").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Burmés").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Burmilla").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("California Spangled").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Chantilly Tiffany").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Chartreux").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Chausie").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Rex de Cornualles").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Cymric").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Rex de Devon").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Don Sphynx").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Dragón Li").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Mau egipcio").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Europeo de pelo corto").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Exótico de pelo corto").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Habana").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Himalayo").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Bobtail japonés").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Khao Manee").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Korat").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Bobtail kuriliano").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("LaPerm").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Maine Coon").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Manx").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Munchkin").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Nebelung").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Bosque de Noruega").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Ocicat").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Oriental de pelo corto").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Persa").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Peterbald").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Ragdoll").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Azul ruso").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Savannah").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Scottish Fold").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Esfinge").especie(gato).build());
        }

        public void cargarEspecialidades() {

                especialidadRepo.save(Especialidad.builder().nombre("Cardiología").build());
                especialidadRepo.save(Especialidad.builder().nombre("Dermatología").build());
                especialidadRepo.save(Especialidad.builder().nombre("Neurología").build());
                especialidadRepo.save(Especialidad.builder().nombre("Oncología").build());
                especialidadRepo.save(Especialidad.builder().nombre("Oftalmología").build());
                especialidadRepo.save(Especialidad.builder().nombre("Cirugía").build());
                especialidadRepo.save(Especialidad.builder().nombre("Medicina General").build());
                especialidadRepo.save(Especialidad.builder().nombre("Medicina Interna").build());
                especialidadRepo.save(Especialidad.builder().nombre("Emergencia").build());
                especialidadRepo.save(Especialidad.builder().nombre("Nutrición").build());

        }

        public void cargarClientes() {

                clientsRepo.save(Client.builder().nombre("Carlos Ramírez").cedula("1001").correo("carlos1@mail.com")
                                .celular("300000001").build());
                clientsRepo.save(Client.builder().nombre("Laura Gómez").cedula("1002").correo("laura2@mail.com")
                                .celular("300000002").build());
                clientsRepo.save(Client.builder().nombre("Andrés Torres").cedula("1003").correo("andres3@mail.com")
                                .celular("300000003").build());
                clientsRepo.save(Client.builder().nombre("María López").cedula("1004").correo("maria4@mail.com")
                                .celular("300000004").build());
                clientsRepo.save(Client.builder().nombre("Daniel Rojas").cedula("1005").correo("daniel5@mail.com")
                                .celular("300000005").build());
                clientsRepo.save(Client.builder().nombre("Ana Martínez").cedula("1006").correo("ana6@mail.com")
                                .celular("300000006").build());
                clientsRepo.save(Client.builder().nombre("Jorge Pérez").cedula("1007").correo("jorge7@mail.com")
                                .celular("300000007").build());
                clientsRepo.save(Client.builder().nombre("Paula Díaz").cedula("1008").correo("paula8@mail.com")
                                .celular("300000008").build());
                clientsRepo.save(Client.builder().nombre("Miguel Castro").cedula("1009").correo("miguel9@mail.com")
                                .celular("300000009").build());
                clientsRepo.save(Client.builder().nombre("Sofía Herrera").cedula("1010").correo("sofia10@mail.com")
                                .celular("300000010").build());
                clientsRepo.save(Client.builder().nombre("Luis Vargas").cedula("1011").correo("luis11@mail.com")
                                .celular("300000011").build());
                clientsRepo.save(Client.builder().nombre("Valentina Ruiz").cedula("1012").correo("valentina12@mail.com")
                                .celular("300000012").build());
                clientsRepo.save(Client.builder().nombre("Pedro Silva").cedula("1013").correo("pedro13@mail.com")
                                .celular("300000013").build());
                clientsRepo.save(Client.builder().nombre("Camila Molina").cedula("1014").correo("camila14@mail.com")
                                .celular("300000014").build());
                clientsRepo.save(Client.builder().nombre("Juan Castillo").cedula("1015").correo("juan15@mail.com")
                                .celular("300000015").build());
                clientsRepo.save(Client.builder().nombre("Juliana Navarro").cedula("1016").correo("juliana16@mail.com")
                                .celular("300000016").build());
                clientsRepo.save(Client.builder().nombre("Diego Ortega").cedula("1017").correo("diego17@mail.com")
                                .celular("300000017").build());
                clientsRepo.save(Client.builder().nombre("Natalia Peña").cedula("1018").correo("natalia18@mail.com")
                                .celular("300000018").build());
                clientsRepo.save(Client.builder().nombre("Felipe Guerrero").cedula("1019").correo("felipe19@mail.com")
                                .celular("300000019").build());
                clientsRepo.save(Client.builder().nombre("Daniela Arias").cedula("1020").correo("daniela20@mail.com")
                                .celular("300000020").build());
                clientsRepo.save(Client.builder().nombre("Ricardo Soto").cedula("1021").correo("ricardo21@mail.com")
                                .celular("300000021").build());
                clientsRepo.save(Client.builder().nombre("Sara Cabrera").cedula("1022").correo("sara22@mail.com")
                                .celular("300000022").build());
                clientsRepo.save(Client.builder().nombre("Alberto Salazar").cedula("1023").correo("alberto23@mail.com")
                                .celular("300000023").build());
                clientsRepo.save(Client.builder().nombre("Lucía Romero").cedula("1024").correo("lucia24@mail.com")
                                .celular("300000024").build());
                clientsRepo.save(Client.builder().nombre("Roberth Mendez").cedula("1025").correo("roberth25@mail.com")
                                .celular("300000025").build());
                clientsRepo.save(Client.builder().nombre("Mónica Vega").cedula("1026").correo("monica26@mail.com")
                                .celular("300000026").build());
                clientsRepo.save(Client.builder().nombre("Santiago Pardo").cedula("1027").correo("santiago27@mail.com")
                                .celular("300000027").build());
                clientsRepo.save(Client.builder().nombre("Luz Salazar").cedula("1028").correo("luz28@mail.com")
                                .celular("300000028").build());
                clientsRepo.save(Client.builder().nombre("Mauricio León").cedula("1029").correo("mauricio29@mail.com")
                                .celular("300000029").build());
                clientsRepo.save(Client.builder().nombre("Laura Mendoza").cedula("1030").correo("laura30@mail.com")
                                .celular("300000030").build());
                clientsRepo.save(Client.builder().nombre("Cristian Luna").cedula("1031").correo("cristian31@mail.com")
                                .celular("300000031").build());
                clientsRepo.save(Client.builder().nombre("Patricia Cárdenas").cedula("1032")
                                .correo("patricia32@mail.com")
                                .celular("300000032").build());
                clientsRepo.save(Client.builder().nombre("Fernando Rocha").cedula("1033").correo("fernando33@mail.com")
                                .celular("300000033").build());
                clientsRepo.save(Client.builder().nombre("Carolina Mejía").cedula("1034").correo("carolina34@mail.com")
                                .celular("300000034").build());
                clientsRepo.save(Client.builder().nombre("Óscar Duarte").cedula("1035").correo("oscar35@mail.com")
                                .celular("300000035").build());
                clientsRepo.save(Client.builder().nombre("Paola Fuentes").cedula("1036").correo("paola36@mail.com")
                                .celular("300000036").build());
                clientsRepo.save(Client.builder().nombre("Kevin Rincón").cedula("1037").correo("kevin37@mail.com")
                                .celular("300000037").build());
                clientsRepo.save(Client.builder().nombre("Natalia Muñoz").cedula("1038").correo("natalia38@mail.com")
                                .celular("300000038").build());
                clientsRepo.save(Client.builder().nombre("Julio Castaño").cedula("1039").correo("julio39@mail.com")
                                .celular("300000039").build());
                clientsRepo.save(Client.builder().nombre("Andrea Valencia").cedula("1040").correo("andrea40@mail.com")
                                .celular("300000040").build());
                clientsRepo.save(Client.builder().nombre("David Zapata").cedula("1041").correo("david41@mail.com")
                                .celular("300000041").build());
                clientsRepo.save(Client.builder().nombre("Lorena Hidalgo").cedula("1042").correo("lorena42@mail.com")
                                .celular("300000042").build());
                clientsRepo.save(Client.builder().nombre("Tomás Medina").cedula("1043").correo("tomas43@mail.com")
                                .celular("300000043").build());
                clientsRepo.save(Client.builder().nombre("Angela Prieto").cedula("1044").correo("angela44@mail.com")
                                .celular("300000044").build());
                clientsRepo.save(Client.builder().nombre("Sebastián Beltrán").cedula("1045")
                                .correo("sebastian45@mail.com")
                                .celular("300000045").build());
                clientsRepo.save(Client.builder().nombre("Mariana Quintero").cedula("1046").correo("mariana46@mail.com")
                                .celular("300000046").build());
                clientsRepo.save(Client.builder().nombre("Raúl Rivas").cedula("1047").correo("raul47@mail.com")
                                .celular("300000047").build());
                clientsRepo.save(Client.builder().nombre("Tatiana Lozano").cedula("1048").correo("tatiana48@mail.com")
                                .celular("300000048").build());
                clientsRepo.save(Client.builder().nombre("Iván Gallego").cedula("1049").correo("ivan49@mail.com")
                                .celular("300000049").build());
                clientsRepo.save(Client.builder().nombre("Diana Acosta").cedula("1050").correo("diana50@mail.com")
                                .celular("300000050").build());

        }

        public void cargarMascotas() {

          // PERROS ----------------------------------------------------
          petsRepo.save(Pet.builder().nombre("Princesa")
                           .raza(razaDesdeString("Lebrel afgano"))
                           .fechaNacimiento(calcularFechaNacimiento(4)).peso(27f)
                           .urlFoto("https://images.dog.ceo/breeds/hound-afghan/n02088094_1406.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Charlie")
                           .raza(razaDesdeString("Beagle"))
                           .fechaNacimiento(calcularFechaNacimiento(3)).peso(12f)
                           .urlFoto("https://images.dog.ceo/breeds/beagle/n02088364_11136.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Buddy")
                           .raza(razaDesdeString("Labrador retriever"))
                           .fechaNacimiento(calcularFechaNacimiento(5)).peso(30f)
                           .urlFoto("https://images.dog.ceo/breeds/labrador/n02099712_7937.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Toby")
                           .raza(razaDesdeString("Carlino"))
                           .fechaNacimiento(calcularFechaNacimiento(2)).peso(8f)
                           .urlFoto("https://images.dog.ceo/breeds/pug/n02110958_11239.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Zeus")
                           .raza(razaDesdeString("Husky siberiano"))
                           .fechaNacimiento(calcularFechaNacimiento(3)).peso(22f)
                           .urlFoto("https://images.dog.ceo/breeds/husky/n02110185_1469.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Leo")
                           .raza(razaDesdeString("Cocker spaniel"))
                           .fechaNacimiento(calcularFechaNacimiento(4)).peso(13f)
                           .urlFoto("https://images.dog.ceo/breeds/spaniel-cocker/n02102318_1671.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Duke")
                           .raza(razaDesdeString("Terrier de Norwich"))
                           .fechaNacimiento(calcularFechaNacimiento(6)).peso(6f)
                           .urlFoto("https://images.dog.ceo/breeds/terrier-norwich/n02094258_2070.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Oliver")
                           .raza(razaDesdeString("Golden retriever"))
                           .fechaNacimiento(calcularFechaNacimiento(3)).peso(28f)
                           .urlFoto("https://images.dog.ceo/breeds/retriever-golden/n02099601_3004.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Bentley")
                           .raza(razaDesdeString("Pastor de Shetland"))
                           .fechaNacimiento(calcularFechaNacimiento(2)).peso(10f)
                           .urlFoto("https://images.dog.ceo/breeds/sheepdog-shetland/n02105855_10095.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Louie")
                           .raza(razaDesdeString("Terrier de Yorkshire"))
                           .fechaNacimiento(calcularFechaNacimiento(5)).peso(4f)
                           .urlFoto("https://images.dog.ceo/breeds/terrier-yorkshire/n02094433_3812.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Simba")
                           .raza(razaDesdeString("Bóxer"))
                           .fechaNacimiento(calcularFechaNacimiento(4)).peso(26f)
                           .urlFoto("https://images.dog.ceo/breeds/boxer/n02108089_9724.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Cooper")
                           .raza(razaDesdeString("Dálmata"))
                           .fechaNacimiento(calcularFechaNacimiento(3)).peso(24f)
                           .urlFoto("https://images.dog.ceo/breeds/dalmatian/cooper2.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Thor")
                           .raza(razaDesdeString("Bullmastiff"))
                           .fechaNacimiento(calcularFechaNacimiento(5)).peso(40f)
                           .urlFoto("https://images.dog.ceo/breeds/mastiff-bull/n02108422_5106.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Kira")
                           .raza(razaDesdeString("Akita"))
                           .fechaNacimiento(calcularFechaNacimiento(4)).peso(32f)
                           .urlFoto("https://images.dog.ceo/breeds/akita/512px-Ainu-Dog.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Loki")
                           .raza(razaDesdeString("Papillón"))
                           .fechaNacimiento(calcularFechaNacimiento(2)).peso(5f)
                           .urlFoto("https://images.dog.ceo/breeds/papillon/n02086910_7949.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Rex")
                           .raza(razaDesdeString("Terrier americano"))
                           .fechaNacimiento(calcularFechaNacimiento(3)).peso(11f)
                           .urlFoto("https://images.dog.ceo/breeds/terrier-american/n02093428_1482.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Coco")
                           .raza(razaDesdeString("Chihuahua"))
                           .fechaNacimiento(calcularFechaNacimiento(2)).peso(3f)
                           .urlFoto("https://images.dog.ceo/breeds/chihuahua/n02085620_7436.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Bobby")
                           .raza(razaDesdeString("Terrier australiano"))
                           .fechaNacimiento(calcularFechaNacimiento(5)).peso(7f)
                           .urlFoto("https://images.dog.ceo/breeds/terrier-australian/n02096294_7175.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Nala")
                           .raza(razaDesdeString("Dingo"))
                           .fechaNacimiento(calcularFechaNacimiento(4)).peso(18f)
                           .urlFoto("https://images.dog.ceo/breeds/dingo/n02115641_1228.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Shadow")
                           .raza(razaDesdeString("Kelpie australiano"))
                           .fechaNacimiento(calcularFechaNacimiento(3)).peso(19f)
                           .urlFoto("https://images.dog.ceo/breeds/kelpie/n02105412_1973.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Bruno")
                           .raza(razaDesdeString("Malamute de Alaska"))
                           .fechaNacimiento(calcularFechaNacimiento(4)).peso(35f)
                           .urlFoto("https://images.dog.ceo/breeds/malamute/n02110063_13625.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Apolo")
                           .raza(razaDesdeString("Galgo italiano"))
                           .fechaNacimiento(calcularFechaNacimiento(3)).peso(7f)
                           .urlFoto("https://images.dog.ceo/breeds/greyhound-italian/n02091032_1266.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Hunter")
                           .raza(razaDesdeString("Otterhound"))
                           .fechaNacimiento(calcularFechaNacimiento(6)).peso(32f)
                           .urlFoto("https://images.dog.ceo/breeds/otterhound/n02091635_1319.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Sasha")
                           .raza(razaDesdeString("Saluki"))
                           .fechaNacimiento(calcularFechaNacimiento(4)).peso(20f)
                           .urlFoto("https://images.dog.ceo/breeds/saluki/n02091831_5745.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Koda")
                           .raza(razaDesdeString("Basenji"))
                           .fechaNacimiento(calcularFechaNacimiento(3)).peso(10f)
                           .urlFoto("https://images.dog.ceo/breeds/basenji/n02110806_4156.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Balto")
                          .raza(razaDesdeString("Boyero de Flandes"))
                          .fechaNacimiento(calcularFechaNacimiento(5)).peso(34f)
                          .urlFoto("https://images.dog.ceo/breeds/bouvier/n02106382_9651.jpg")
                          .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Ace")
                           .raza(razaDesdeString("Border collie"))
                           .fechaNacimiento(calcularFechaNacimiento(3)).peso(18f)
                           .urlFoto("https://images.dog.ceo/breeds/collie-border/n02106166_355.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Maverick")
                           .raza(razaDesdeString("Komondor"))
                           .fechaNacimiento(calcularFechaNacimiento(6)).peso(45f)
                           .urlFoto("https://images.dog.ceo/breeds/komondor/n02105505_853.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Sam")
                           .raza(razaDesdeString("Leonberger"))
                           .fechaNacimiento(calcularFechaNacimiento(4)).peso(48f)
                           .urlFoto("https://images.dog.ceo/breeds/leonberg/n02111129_4903.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Oso")
                           .raza(razaDesdeString("Terranova"))
                           .fechaNacimiento(calcularFechaNacimiento(5)).peso(50f)
                           .urlFoto("https://images.dog.ceo/breeds/newfoundland/n02111277_3907.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Chop")
                           .raza(razaDesdeString("Rottweiler"))
                           .fechaNacimiento(calcularFechaNacimiento(4)).peso(42f)
                           .urlFoto("https://images.dog.ceo/breeds/rottweiler/n02106550_8776.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Buster")
                           .raza(razaDesdeString("Chow chow"))
                           .fechaNacimiento(calcularFechaNacimiento(6)).peso(60f)
                           .urlFoto("https://images.dog.ceo/breeds/chow/n02112137_7428.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Rita")
                           .raza(razaDesdeString("Weimaraner"))
                           .fechaNacimiento(calcularFechaNacimiento(3)).peso(25f)
                           .urlFoto("https://images.dog.ceo/breeds/weimaraner/n02092339_8024.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Finn")
                           .raza(razaDesdeString("Whippet"))
                           .fechaNacimiento(calcularFechaNacimiento(2)).peso(9f)
                           .urlFoto("https://images.dog.ceo/breeds/whippet/n02091134_392.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Oscar")
                           .raza(razaDesdeString("Terrier escocés"))
                           .fechaNacimiento(calcularFechaNacimiento(5)).peso(9f)
                           .urlFoto("https://images.dog.ceo/breeds/terrier-scottish/n02097298_2957.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Jack Sparrow")
                           .raza(razaDesdeString("West Highland white terrier"))
                           .fechaNacimiento(calcularFechaNacimiento(3)).peso(8f)
                           .urlFoto("https://images.dog.ceo/breeds/terrier-westhighland/n02098286_4120.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Luna")
                           .raza(razaDesdeString("Vizsla"))
                           .fechaNacimiento(calcularFechaNacimiento(4)).peso(20f)
                           .urlFoto("https://images.dog.ceo/breeds/vizsla/n02100583_12660.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Milo")
                           .raza(razaDesdeString("Pekinés"))
                           .fechaNacimiento(calcularFechaNacimiento(6)).peso(6f)
                           .urlFoto("https://images.dog.ceo/breeds/pekinese/n02086079_11089.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Dexter")
                           .raza(razaDesdeString("Pinscher miniatura"))
                           .fechaNacimiento(calcularFechaNacimiento(4)).peso(5f)
                           .urlFoto("https://images.dog.ceo/breeds/pinscher-miniature/n02107312_1885.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Ranger")
                           .raza(razaDesdeString("Pointer alemán"))
                           .fechaNacimiento(calcularFechaNacimiento(3)).peso(23f)
                           .urlFoto("https://images.dog.ceo/breeds/pointer-german/n02100236_689.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Archie")
                          .raza(razaDesdeString("Caniche miniatura"))
                          .fechaNacimiento(calcularFechaNacimiento(4)).peso(9f)
                          .urlFoto("https://images.dog.ceo/breeds/poodle-miniature/n02113712_3049.jpg")
                          .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Musa")
                           .raza(razaDesdeString("Caniche estándar"))
                           .fechaNacimiento(calcularFechaNacimiento(5)).peso(22f)
                           .urlFoto("https://images.dog.ceo/breeds/poodle-standard/n02113799_448.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Gizo")
                           .raza(razaDesdeString("Caniche toy"))
                           .fechaNacimiento(calcularFechaNacimiento(2)).peso(4f)
                           .urlFoto("https://images.dog.ceo/breeds/poodle-toy/n02113624_5459.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Fausto")
                           .raza(razaDesdeString("Schnauzer miniatura"))
                           .fechaNacimiento(calcularFechaNacimiento(4)).peso(8f)
                           .urlFoto("https://images.dog.ceo/breeds/schnauzer-miniature/n02097047_6884.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Manolo")
                           .raza(razaDesdeString("Setter inglés"))
                           .fechaNacimiento(calcularFechaNacimiento(3)).peso(24f)
                           .urlFoto("https://images.dog.ceo/breeds/setter-english/n02100735_7553.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Pequitas")
                           .raza(razaDesdeString("Spaniel bretón"))
                           .fechaNacimiento(calcularFechaNacimiento(3)).peso(18f)
                           .urlFoto("https://images.dog.ceo/breeds/spaniel-brittany/n02101388_2429.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Momoa")
                           .raza(razaDesdeString("Spaniel irlandés"))
                           .fechaNacimiento(calcularFechaNacimiento(5)).peso(20f)
                           .urlFoto("https://images.dog.ceo/breeds/spaniel-irish/n02102973_1377.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Benji")
                           .raza(razaDesdeString("Terrier irlandés"))
                           .fechaNacimiento(calcularFechaNacimiento(4)).peso(12f)
                           .urlFoto("https://images.dog.ceo/breeds/terrier-irish/n02093991_1038.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Rocco")
                           .raza(razaDesdeString("Terrier kerry blue"))
                           .fechaNacimiento(calcularFechaNacimiento(6)).peso(17f)
                           .urlFoto("https://images.dog.ceo/breeds/terrier-kerryblue/n02093859_1239.jpg")
                           .estado(true).build());
          petsRepo.save(Pet.builder().nombre("Lucky")
                           .raza(razaDesdeString("Terrier lakeland"))
                           .fechaNacimiento(calcularFechaNacimiento(3)).peso(9f)
                           .urlFoto("https://images.dog.ceo/breeds/terrier-lakeland/n02095570_1270.jpg")
                           .estado(true).build());

                // GATOSSSS --------------------------------------------------------

                petsRepo.save(Pet.builder().nombre("Luna")
                                .raza(razaDesdeString("Abisinio"))
                                .fechaNacimiento(calcularFechaNacimiento(2)).peso(4.1f)
                                .urlFoto("https://cdn2.thecatapi.com/images/0XYvRd7oD.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Milo")
                                .raza(razaDesdeString("Egeo"))
                                .fechaNacimiento(calcularFechaNacimiento(3)).peso(4.5f)
                                .urlFoto("https://cdn2.thecatapi.com/images/ozEvzdVM-.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Nala")
                                .raza(razaDesdeString("Bobtail americano"))
                                .fechaNacimiento(calcularFechaNacimiento(4)).peso(5.2f)
                                .urlFoto("https://cdn2.thecatapi.com/images/hBXicehMA.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Simba")
                                .raza(razaDesdeString("Curl americano"))
                                .fechaNacimiento(calcularFechaNacimiento(1)).peso(3.8f)
                                .urlFoto("https://cdn2.thecatapi.com/images/xnsqonbjW.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Oliver")
                                .raza(razaDesdeString("Americano de pelo corto"))
                                .fechaNacimiento(calcularFechaNacimiento(5)).peso(6.1f)
                                .urlFoto("https://cdn2.thecatapi.com/images/JFPROfGtQ.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Leo")
                                .raza(razaDesdeString("Americano de pelo duro"))
                                .fechaNacimiento(calcularFechaNacimiento(3)).peso(5.0f)
                                .urlFoto("https://cdn2.thecatapi.com/images/8D--jCd21.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Chloe")
                                .raza(razaDesdeString("Mau árabe"))
                                .fechaNacimiento(calcularFechaNacimiento(2)).peso(4.0f)
                                .urlFoto("https://cdn2.thecatapi.com/images/k71ULYfRr.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Loki")
                                .raza(razaDesdeString("Mist australiano"))
                                .fechaNacimiento(calcularFechaNacimiento(4)).peso(4.9f)
                                .urlFoto("https://cdn2.thecatapi.com/images/_6x-3TiCA.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Bella")
                                .raza(razaDesdeString("Balinés"))
                                .fechaNacimiento(calcularFechaNacimiento(3)).peso(4.4f)
                                .urlFoto("https://cdn2.thecatapi.com/images/13MkvUreZ.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Max")
                                .raza(razaDesdeString("Bambino"))
                                .fechaNacimiento(calcularFechaNacimiento(2)).peso(3.5f)
                                .urlFoto("https://cdn2.thecatapi.com/images/5AdhMjeEu.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Kira")
                                .raza(razaDesdeString("Bengalí"))
                                .fechaNacimiento(calcularFechaNacimiento(3)).peso(5.3f)
                                .urlFoto("https://cdn2.thecatapi.com/images/a8.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Tom")
                                .raza(razaDesdeString("Sagrado de Birmania"))
                                .fechaNacimiento(calcularFechaNacimiento(6)).peso(6.5f)
                                .urlFoto("https://cdn2.thecatapi.com/images/HOrX5gwLS.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Mia")
                                .raza(razaDesdeString("Bombay"))
                                .fechaNacimiento(calcularFechaNacimiento(4)).peso(4.7f)
                                .urlFoto("https://cdn2.thecatapi.com/images/5iYq9NmT1.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Zeus")
                                .raza(razaDesdeString("Británico de pelo largo"))
                                .fechaNacimiento(calcularFechaNacimiento(5)).peso(6.2f)
                                .urlFoto("https://cdn2.thecatapi.com/images/7isAO4Cav.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Nina")
                                .raza(razaDesdeString("Británico de pelo corto"))
                                .fechaNacimiento(calcularFechaNacimiento(2)).peso(4.8f)
                                .urlFoto("https://cdn2.thecatapi.com/images/9j5.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Felix")
                                .raza(razaDesdeString("Burmés"))
                                .fechaNacimiento(calcularFechaNacimiento(3)).peso(4.6f)
                                .urlFoto("https://cdn2.thecatapi.com/images/4lXnnfxac.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Coco")
                                .raza(razaDesdeString("Burmilla"))
                                .fechaNacimiento(calcularFechaNacimiento(4)).peso(5.1f)
                                .urlFoto("https://cdn2.thecatapi.com/images/b1.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Sasha")
                                .raza(razaDesdeString("California Spangled"))
                                .fechaNacimiento(calcularFechaNacimiento(5)).peso(5.9f)
                                .urlFoto("https://cdn2.thecatapi.com/images/b9.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Toby")
                                .raza(razaDesdeString("Chantilly Tiffany"))
                                .fechaNacimiento(calcularFechaNacimiento(2)).peso(4.3f)
                                .urlFoto("https://cdn2.thecatapi.com/images/c3.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Daisy")
                                .raza(razaDesdeString("Chartreux"))
                                .fechaNacimiento(calcularFechaNacimiento(3)).peso(4.9f)
                                .urlFoto("https://cdn2.thecatapi.com/images/j6oFGLpRG.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Rocky")
                                .raza(razaDesdeString("Chausie"))
                                .fechaNacimiento(calcularFechaNacimiento(4)).peso(6.0f)
                                .urlFoto("https://cdn2.thecatapi.com/images/c7.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Lily")
                                .raza(razaDesdeString("Rex de Cornualles"))
                                .fechaNacimiento(calcularFechaNacimiento(2)).peso(3.9f)
                                .urlFoto("https://cdn2.thecatapi.com/images/unX21IBVB.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Oscar")
                                .raza(razaDesdeString("Cymric"))
                                .fechaNacimiento(calcularFechaNacimiento(6)).peso(6.4f)
                                .urlFoto("https://cdn2.thecatapi.com/images/m2.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Ruby")
                                .raza(razaDesdeString("Rex de Devon"))
                                .fechaNacimiento(calcularFechaNacimiento(3)).peso(4.1f)
                                .urlFoto("https://cdn2.thecatapi.com/images/d9.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Thor")
                                .raza(razaDesdeString("Don Sphynx"))
                                .fechaNacimiento(calcularFechaNacimiento(5)).peso(5.7f)
                                .urlFoto("https://cdn2.thecatapi.com/images/3KG57GfMW.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Maya")
                                .raza(razaDesdeString("Dragón Li"))
                                .fechaNacimiento(calcularFechaNacimiento(4)).peso(4.8f)
                                .urlFoto("https://cdn2.thecatapi.com/images/e2.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Sam")
                                .raza(razaDesdeString("Mau egipcio"))
                                .fechaNacimiento(calcularFechaNacimiento(2)).peso(4.3f)
                                .urlFoto("https://cdn2.thecatapi.com/images/k71ULYfRr.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Zoe")
                                .raza(razaDesdeString("Europeo de pelo corto"))
                                .fechaNacimiento(calcularFechaNacimiento(3)).peso(4.6f)
                                .urlFoto("https://cdn2.thecatapi.com/images/0XYvRd7oD.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Bobby")
                                .raza(razaDesdeString("Exótico de pelo corto"))
                                .fechaNacimiento(calcularFechaNacimiento(5)).peso(6.3f)
                                .urlFoto("https://cdn2.thecatapi.com/images/e8.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Kiara")
                                .raza(razaDesdeString("Habana"))
                                .fechaNacimiento(calcularFechaNacimiento(3)).peso(4.7f)
                                .urlFoto("https://cdn2.thecatapi.com/images/f1.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Shadow")
                                .raza(razaDesdeString("Himalayo"))
                                .fechaNacimiento(calcularFechaNacimiento(6)).peso(6.8f)
                                .urlFoto("https://cdn2.thecatapi.com/images/f5.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Tina")
                                .raza(razaDesdeString("Bobtail japonés"))
                                .fechaNacimiento(calcularFechaNacimiento(4)).peso(4.5f)
                                .urlFoto("https://cdn2.thecatapi.com/images/-tm9-znzl.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Pipo")
                                .raza(razaDesdeString("Khao Manee"))
                                .fechaNacimiento(calcularFechaNacimiento(2)).peso(3.9f)
                                .urlFoto("https://cdn2.thecatapi.com/images/g2.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Gala")
                                .raza(razaDesdeString("Korat"))
                                .fechaNacimiento(calcularFechaNacimiento(3)).peso(4.4f)
                                .urlFoto("https://cdn2.thecatapi.com/images/g7.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Hugo")
                                .raza(razaDesdeString("Bobtail kuriliano"))
                                .fechaNacimiento(calcularFechaNacimiento(5)).peso(5.8f)
                                .urlFoto("https://cdn2.thecatapi.com/images/OUfeISEoi.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Perla")
                                .raza(razaDesdeString("LaPerm"))
                                .fechaNacimiento(calcularFechaNacimiento(4)).peso(4.2f)
                                .urlFoto("https://cdn2.thecatapi.com/images/h3.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Atlas")
                                .raza(razaDesdeString("Maine Coon"))
                                .fechaNacimiento(calcularFechaNacimiento(3)).peso(7.2f)
                                .urlFoto("https://cdn2.thecatapi.com/images/h8.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Katy")
                                .raza(razaDesdeString("Manx"))
                                .fechaNacimiento(calcularFechaNacimiento(6)).peso(5.9f)
                                .urlFoto("https://cdn2.thecatapi.com/images/i1.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Mini")
                                .raza(razaDesdeString("Munchkin"))
                                .fechaNacimiento(calcularFechaNacimiento(2)).peso(3.4f)
                                .urlFoto("https://cdn2.thecatapi.com/images/5AdhMjeEu.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Nero")
                                .raza(razaDesdeString("Nebelung"))
                                .fechaNacimiento(calcularFechaNacimiento(4)).peso(4.9f)
                                .urlFoto("https://cdn2.thecatapi.com/images/i6.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Freya")
                                .raza(razaDesdeString("Bosque de Noruega"))
                                .fechaNacimiento(calcularFechaNacimiento(5)).peso(6.7f)
                                .urlFoto("https://cdn2.thecatapi.com/images/4lXnnfxac.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Bolt")
                                .raza(razaDesdeString("Ocicat"))
                                .fechaNacimiento(calcularFechaNacimiento(3)).peso(5.0f)
                                .urlFoto("https://cdn2.thecatapi.com/images/j2.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Misty")
                                .raza(razaDesdeString("Oriental de pelo corto"))
                                .fechaNacimiento(calcularFechaNacimiento(4)).peso(4.3f)
                                .urlFoto("https://cdn2.thecatapi.com/images/0XYvRd7oD.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Fluffy")
                                .raza(razaDesdeString("Persa"))
                                .fechaNacimiento(calcularFechaNacimiento(6)).peso(6.5f)
                                .urlFoto("https://cdn2.thecatapi.com/images/-Zfz5z2jK.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Pixel")
                                .raza(razaDesdeString("Peterbald"))
                                .fechaNacimiento(calcularFechaNacimiento(2)).peso(3.6f)
                                .urlFoto("https://cdn2.thecatapi.com/images/j9.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Snow")
                                .raza(razaDesdeString("Ragdoll"))
                                .fechaNacimiento(calcularFechaNacimiento(4)).peso(5.8f)
                                .urlFoto("https://cdn2.thecatapi.com/images/oGefY4YoG.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Blue")
                                .raza(razaDesdeString("Azul ruso"))
                                .fechaNacimiento(calcularFechaNacimiento(3)).peso(4.4f)
                                .urlFoto("https://cdn2.thecatapi.com/images/k4.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Hunter")
                                .raza(razaDesdeString("Savannah"))
                                .fechaNacimiento(calcularFechaNacimiento(5)).peso(7.0f)
                                .urlFoto("https://cdn2.thecatapi.com/images/-tm9-znzl.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Poppy")
                                .raza(razaDesdeString("Scottish Fold"))
                                .fechaNacimiento(calcularFechaNacimiento(2)).peso(4.2f)
                                .urlFoto("https://cdn2.thecatapi.com/images/k8.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Neo")
                                .raza(razaDesdeString("Esfinge"))
                                .fechaNacimiento(calcularFechaNacimiento(4)).peso(3.9f)
                                .urlFoto("https://cdn2.thecatapi.com/images/l3.jpg")
                                .estado(true).build());
        }

        public void cargarVeterinarios() {

                vetsRepo.save(Vet.builder().nombre("Ana González")
                                .estado(true)
                                .correo("ana.gonzalez@biskit.com")
                                .cedula("101")
                                .urlFoto("https://media.istockphoto.com/id/1995860815/es/foto/mujer-veterinaria-sosteniendo-al-perro-jack-russell.jpg?s=612x612&w=0&k=20&c=ZLFwLWVnPXnCx876vI312OhVUUOEU5Z_9ZHQfvBS4jk=")
                                .build());
                vetsRepo.save(Vet.builder().nombre("Juan Malaver")
                                .estado(true)
                                .correo("juan.malaver@biskit.com")
                                .cedula("102")
                                .urlFoto("https://clinicaveterinariasangabriel.pe/wp-content/uploads/2016/06/DSC_07971-1024x925-1.jpg")
                                .build());
                vetsRepo.save(Vet.builder().nombre("Cristiano Ronaldo")
                                .estado(true)
                                .correo("cristiano.ronaldo@biskit.com")
                                .cedula("103")
                                .urlFoto("https://www.shutterstock.com/image-photo/cheerful-man-veterinarian-doctor-cuddling-600nw-2684638717.jpg")
                                .build());
                vetsRepo.save(Vet.builder().nombre("Jude Bellingham")
                                .estado(true)
                                .correo("jude.bellingham@biskit.com")
                                .cedula("104")
                                .urlFoto("https://www.shutterstock.com/image-photo/cheerful-man-veterinarian-doctor-cuddling-600nw-2684638717.jpg")
                                .build());
                vetsRepo.save(Vet.builder().nombre("Carla García")
                                .estado(true)
                                .correo("carla.garcia@biskit.com")
                                .cedula("105")
                                .urlFoto("https://img.freepik.com/foto-gratis/veterinario-que-controla-salud-cachorro_23-2148728396.jpg?semt=ais_rp_50_assets&w=740&q=80")
                                .build());
                vetsRepo.save(Vet.builder().nombre("Isabella Garzón")
                                .estado(true)
                                .correo("isabella.garzon@biskit.com")
                                .cedula("106")
                                .urlFoto("https://metodoeisi.com/wp-content/uploads/2020/10/medicina-veterinaria.png")
                                .build());
                vetsRepo.save(Vet.builder().nombre("José Rodríguez")
                                .estado(true)
                                .correo("jose.rodriguez@biskit.com")
                                .cedula("107")
                                .urlFoto("https://veterinariacavero.com/wp-content/uploads/2024/08/Dr-Omar-cavero-Medico-Veterinario-especializado-en-Neurologia-y-Traumatologia.jpg")
                                .build());
                vetsRepo.save(Vet.builder().nombre("Emiliano Martínez")
                                .estado(true)
                                .correo("emiliano.martinez@biskit.com")
                                .cedula("108")
                                .urlFoto("https://veterinariacavero.com/wp-content/uploads/2024/08/Dr-Omar-cavero-Medico-Veterinario-especializado-en-Neurologia-y-Traumatologia.jpg")
                                .build());
                vetsRepo.save(Vet.builder().nombre("Gustavo Lara")
                                .estado(true)
                                .correo("gustavo.lara@biskit.com")
                                .cedula("109")
                                .urlFoto("https://veterinariacavero.com/wp-content/uploads/2024/08/Dr-Omar-cavero-Medico-Veterinario-especializado-en-Neurologia-y-Traumatologia.jpg")
                                .build());
                vetsRepo.save(Vet.builder().nombre("Nadia Jiménez")
                                .estado(true)
                                .correo("nadia.jimenez@biskit.com")
                                .cedula("110")
                                .urlFoto("https://eq2imhfmrcc.exactdn.com/wp-content/uploads/2012/06/perro-veterinaria.jpg?strip=all")
                                .build());

        }

        public void cargarAdministradores() {

                adminRepo.save(Admin.builder()
                                .nombre("Roberth")
                                .correo("roberth@biskit.com")
                                .cedula("10")
                                .build());
                adminRepo.save(Admin.builder()
                                .nombre("Guden")
                                .correo("guden@biskit.com")
                                .cedula("20")
                                .build());
                adminRepo.save(Admin.builder()
                                .nombre("Luz")
                                .correo("luz@biskit.com")
                                .cedula("30")
                                .build());
                adminRepo.save(Admin.builder()
                                .nombre("Santiago")
                                .correo("santiago@biskit.com")
                                .cedula("40")
                                .build());
                adminRepo.save(Admin.builder()
                                .nombre("Angarita")
                                .correo("angarita@biskit.com")
                                .cedula("50")
                                .build());

        }

        public void cargarCredenciales() {

                for (Client client : clientsRepo.findAll()) {
                        Credenciales credenciales = Credenciales.builder().usuario(client.getCorreo())
                                        .password(client.getCedula()).build();
                        credencialesRepo.save(credenciales);
                        client.setCredenciales(credenciales);
                }

                for (Vet vet : vetsRepo.findAll()) {
                        Credenciales credenciales = Credenciales.builder().usuario(vet.getCorreo())
                                        .password(vet.getCedula()).build();
                        credencialesRepo.save(credenciales);
                        vet.setCredenciales(credenciales);
                }

                for (Admin admin : adminRepo.findAll()) {
                        Credenciales credenciales = Credenciales.builder().usuario(admin.getCorreo())
                                        .password(admin.getCedula()).build();
                        credencialesRepo.save(credenciales);
                        admin.setCredenciales(credenciales);
                }

        }

        public void cargarTratamientos() {

                for (int i = 0; i < 20; i++) {
                        Tratamiento tratamiento = Tratamiento.builder().fecha(LocalDate.now().plusDays(i)).build();
                        tratamientosRepo.save(tratamiento);
                }

        }

        public void cargarDrogas() {
                
                // Cargar archivo desde resources (classpath)
                InputStream inputStream = getClass()
                        .getClassLoader()
                        .getResourceAsStream("data/MEDICAMENTOS.xlsx");

                if (inputStream == null) {
                throw new RuntimeException("No se encontró el archivo MEDICAMENTOS.xlsx en resources/data");
                }
         
                try (Workbook workbook = new XSSFWorkbook(inputStream)) {
                
                        Sheet sheet = workbook.getSheetAt(0);

                        for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                                Row row = sheet.getRow(i);
                                if (row == null) continue;

                                Cell nombreCell = row.getCell(0);
                                if (nombreCell == null || nombreCell.getStringCellValue().trim().isEmpty()) {
                                        continue;
                                }
                                String nombre = nombreCell.getStringCellValue().trim();

                                Cell cell = row.getCell(1);
                                long precioVenta = 0;
                                if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                                precioVenta = (long) cell.getNumericCellValue();
                                }


                                cell = row.getCell(2);
                                long precioCompra = 0;
                                if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                                        precioCompra = (long) cell.getNumericCellValue();
                                }

                                cell = row.getCell(3);
                                int unidadesDisp = 0;
                                if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                                unidadesDisp = (int) cell.getNumericCellValue();
                                }

                                cell = row.getCell(4);
                                int unidadesVend = 0;
                                if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                                        unidadesVend = (int) cell.getNumericCellValue();
                                }
 
                                drogasRepo.save(Droga.builder()
                                        .nombre(nombre)
                                        .precioVenta(precioVenta)
                                        .precioCompra(precioCompra)
                                        .unidadesDisponibles(unidadesDisp)
                                        .unidadesVendidas(unidadesVend)
                                        .build());
                        }

                } catch (Exception e) {
                        System.err.println("Error al leer el archivo MEDICAMENTOS.xlsx: " + e.getMessage());
                        e.printStackTrace();  // Para ver la traza completa de la excepción
                }

  
        }

        public void relacionar() {

                Random random = new Random(42);

                int cantidadClients = clientsRepo.findAll().size();
                for (Pet pet : petsRepo.findAll()) {
                        int randomNum = random.nextInt(1, cantidadClients + 1);
                        Client client = clientsRepo.findById((long) randomNum).orElse(null);
                        pet.setOwner(client);
                        petsRepo.save(pet);
                }

                int cantidadEnfermedades = enfermedadRepo.findAll().size();
                for (Pet pet : petsRepo.findAll()) {
                        int randomNum = random.nextInt(1, cantidadEnfermedades + 1);
                        Enfermedad enfermedad = enfermedadRepo.findById((long) randomNum).orElse(null);
                        pet.setEnfermedad(enfermedad);
                        petsRepo.save(pet);
                }

                int cantidadEspecialidades = especialidadRepo.findAll().size();
                for (Vet vet : vetsRepo.findAll()) {
                        int randomNum = random.nextInt(1, cantidadEspecialidades + 1);
                        Especialidad especialidad = especialidadRepo.findById((long) randomNum).orElse(null);
                        vet.setEspecialidad(especialidad);
                        vetsRepo.save(vet);
                }

                int cantidadPets = petsRepo.findAll().size();
                int cantidadVets = vetsRepo.findAll().size();
                for (Tratamiento tratamiento : tratamientosRepo.findAll()) {
                        int randomNumPet = random.nextInt(1, cantidadPets + 1);
                        Pet pet = petsRepo.findById((long) randomNumPet).orElse(null);
                        tratamiento.setPet(pet);

                        int randomNumVet = random.nextInt(1, cantidadVets + 1);
                        Vet vet = vetsRepo.findById((long) randomNumVet).orElse(null);
                        tratamiento.setVet(vet);

                        tratamientosRepo.save(tratamiento);
                }

                int cantidadDrogasTratamiento = 3;
                int totalDrogas = drogasRepo.findAll().size();
                for (Tratamiento tratamiento : tratamientosRepo.findAll())
                        for (int i = 0; i < cantidadDrogasTratamiento; i++) {
                                int randomNumDroga = random.nextInt(1, totalDrogas + 1);
                                Droga droga = drogasRepo.findById((long) randomNumDroga).orElse(null);
                                tratamiento.getDrogas().add(droga);
                                tratamientosRepo.save(tratamiento);
                        }

        }

        private Raza razaDesdeString(String nombreRaza) {
                Raza raza = razaRepo.findByNombre(nombreRaza);
                return raza;
        }

        private Date calcularFechaNacimiento(int edadEnAnios) {
                LocalDate fecha = LocalDate.now().minusYears(edadEnAnios);
                return Date.valueOf(fecha);
        }

}
