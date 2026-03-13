package com.example.biskit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.biskit.entities.Client;
import com.example.biskit.entities.pets.Especie;
import com.example.biskit.entities.pets.Enfermedad;
import com.example.biskit.entities.pets.Pet;
import com.example.biskit.entities.pets.Raza;

import com.example.biskit.repo.ClientsRepo;
import com.example.biskit.repo.pets.EspecieRepo;
import com.example.biskit.repo.pets.EnfermedadRepo;
import com.example.biskit.repo.pets.PetsRepo;
import com.example.biskit.repo.pets.RazaRepo;

import java.sql.Date;
import java.time.LocalDate;
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

        @Override
        public void run(String... args) throws Exception {
                // Crear clientes de ejemplo
                cargarEnfermedades();
                cargarEspecies();
                cargarRazas();
                cargarClientes();
                cargarMascotas();
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

                razaRepo.save(Raza.builder().nombre("Abyssinian").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Aegean").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("American Bobtail").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("American Curl").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("American Shorthair").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("American Wirehair").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Arabian Mau").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Australian Mist").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Balinese").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Bambino").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Bengal").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Birman").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Bombay").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("British Longhair").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("British Shorthair").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Burmese").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Burmilla").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("California Spangled").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Chantilly-Tiffany").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Chartreux").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Chausie").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Cornish Rex").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Cymric").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Devon Rex").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Donskoy").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Dragon Li").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Egyptian Mau").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("European Shorthair").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Exotic Shorthair").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Havana Brown").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Himalayan").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Japanese Bobtail").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Khao Manee").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Korat").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Kurilian Bobtail").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("LaPerm").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Maine Coon").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Manx").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Munchkin").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Nebelung").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Norwegian Forest Cat").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Ocicat").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Oriental Shorthair").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Persian").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Peterbald").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Ragdoll").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Russian Blue").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Savannah").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Scottish Fold").especie(gato).build());
                razaRepo.save(Raza.builder().nombre("Sphynx").especie(gato).build());

        }

        public void cargarEspecies() {
                especieRepo.save(Especie.builder().nombre("Perro").build());
                especieRepo.save(Especie.builder().nombre("Gato").build());
        }

        public void cargarClientes() {
                clientsRepo.save(Client.builder().nombre("Carlos Ramírez").cedula("1001").correo("carlos1@mail.com")
                                .celular("300000001").usuario("user1").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Laura Gómez").cedula("1002").correo("laura2@mail.com")
                                .celular("300000002").usuario("user2").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Andrés Torres").cedula("1003").correo("andres3@mail.com")
                                .celular("300000003").usuario("user3").password("pass").build());
                clientsRepo.save(Client.builder().nombre("María López").cedula("1004").correo("maria4@mail.com")
                                .celular("300000004").usuario("user4").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Daniel Rojas").cedula("1005").correo("daniel5@mail.com")
                                .celular("300000005").usuario("user5").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Ana Martínez").cedula("1006").correo("ana6@mail.com")
                                .celular("300000006").usuario("user6").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Jorge Pérez").cedula("1007").correo("jorge7@mail.com")
                                .celular("300000007").usuario("user7").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Paula Díaz").cedula("1008").correo("paula8@mail.com")
                                .celular("300000008").usuario("user8").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Miguel Castro").cedula("1009").correo("miguel9@mail.com")
                                .celular("300000009").usuario("user9").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Sofía Herrera").cedula("1010").correo("sofia10@mail.com")
                                .celular("300000010").usuario("user10").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Luis Vargas").cedula("1011").correo("luis11@mail.com")
                                .celular("300000011").usuario("user11").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Valentina Ruiz").cedula("1012").correo("valentina12@mail.com")
                                .celular("300000012").usuario("user12").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Pedro Silva").cedula("1013").correo("pedro13@mail.com")
                                .celular("300000013").usuario("user13").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Camila Molina").cedula("1014").correo("camila14@mail.com")
                                .celular("300000014").usuario("user14").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Juan Castillo").cedula("1015").correo("juan15@mail.com")
                                .celular("300000015").usuario("user15").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Juliana Navarro").cedula("1016").correo("juliana16@mail.com")
                                .celular("300000016").usuario("user16").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Diego Ortega").cedula("1017").correo("diego17@mail.com")
                                .celular("300000017").usuario("user17").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Natalia Peña").cedula("1018").correo("natalia18@mail.com")
                                .celular("300000018").usuario("user18").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Felipe Guerrero").cedula("1019").correo("felipe19@mail.com")
                                .celular("300000019").usuario("user19").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Daniela Arias").cedula("1020").correo("daniela20@mail.com")
                                .celular("300000020").usuario("user20").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Ricardo Soto").cedula("1021").correo("ricardo21@mail.com")
                                .celular("300000021").usuario("user21").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Sara Cabrera").cedula("1022").correo("sara22@mail.com")
                                .celular("300000022").usuario("user22").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Alberto Salazar").cedula("1023").correo("alberto23@mail.com")
                                .celular("300000023").usuario("user23").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Lucía Romero").cedula("1024").correo("lucia24@mail.com")
                                .celular("300000024").usuario("user24").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Roberth Mendez").cedula("1025").correo("roberth25@mail.com")
                                .celular("300000025")
                                .usuario("user25").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Mónica Vega").cedula("1026").correo("monica26@mail.com")
                                .celular("300000026").usuario("user26").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Santiago Pardo").cedula("1027").correo("santiago27@mail.com")
                                .celular("300000027").usuario("user27").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Luz Salazar").cedula("1028").correo("luz28@mail.com")
                                .celular("300000028").usuario("user28").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Mauricio León").cedula("1029").correo("mauricio29@mail.com")
                                .celular("300000029").usuario("user29").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Laura Mendoza").cedula("1030").correo("laura30@mail.com")
                                .celular("300000030").usuario("user30").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Cristian Luna").cedula("1031").correo("cristian31@mail.com")
                                .celular("300000031").usuario("user31").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Patricia Cárdenas").cedula("1032")
                                .correo("patricia32@mail.com")
                                .celular("300000032").usuario("user32").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Fernando Rocha").cedula("1033").correo("fernando33@mail.com")
                                .celular("300000033").usuario("user33").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Carolina Mejía").cedula("1034").correo("carolina34@mail.com")
                                .celular("300000034").usuario("user34").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Óscar Duarte").cedula("1035").correo("oscar35@mail.com")
                                .celular("300000035").usuario("user35").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Paola Fuentes").cedula("1036").correo("paola36@mail.com")
                                .celular("300000036").usuario("user36").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Kevin Rincón").cedula("1037").correo("kevin37@mail.com")
                                .celular("300000037").usuario("user37").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Natalia Muñoz").cedula("1038").correo("natalia38@mail.com")
                                .celular("300000038").usuario("user38").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Julio Castaño").cedula("1039").correo("julio39@mail.com")
                                .celular("300000039").usuario("user39").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Andrea Valencia").cedula("1040").correo("andrea40@mail.com")
                                .celular("300000040").usuario("user40").password("pass").build());
                clientsRepo.save(Client.builder().nombre("David Zapata").cedula("1041").correo("david41@mail.com")
                                .celular("300000041").usuario("user41").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Lorena Hidalgo").cedula("1042").correo("lorena42@mail.com")
                                .celular("300000042").usuario("user42").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Tomás Medina").cedula("1043").correo("tomas43@mail.com")
                                .celular("300000043").usuario("user43").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Angela Prieto").cedula("1044").correo("angela44@mail.com")
                                .celular("300000044").usuario("user44").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Sebastián Beltrán").cedula("1045")
                                .correo("sebastian45@mail.com")
                                .celular("300000045").usuario("user45").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Mariana Quintero").cedula("1046").correo("mariana46@mail.com")
                                .celular("300000046").usuario("user46").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Raúl Rivas").cedula("1047").correo("raul47@mail.com")
                                .celular("300000047").usuario("user47").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Tatiana Lozano").cedula("1048").correo("tatiana48@mail.com")
                                .celular("300000048").usuario("user48").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Iván Gallego").cedula("1049").correo("ivan49@mail.com")
                                .celular("300000049").usuario("user49").password("pass").build());
                clientsRepo.save(Client.builder().nombre("Diana Acosta").cedula("1050").correo("diana50@mail.com")
                                .celular("300000050").usuario("user50").password("pass").build());
        }

        public void cargarMascotas() {
                Especie perro = especieRepo.findByNombre("Perro");
                Especie gato = especieRepo.findByNombre("Gato");

                // PERROS ----------------------------------------------------
                petsRepo.save(Pet.builder().nombre("Princesa").especie(perro)
                                .raza(razaDesdeString("Lebrel afgano"))
                                .fechaNacimiento(calcularFechaNacimiento(4)).peso(27f)

                                .URLFoto("https://images.dog.ceo/breeds/hound-afghan/n02088094_1406.jpg")
                                .estado(true).build());
                petsRepo.save(Pet.builder().nombre("Charlie").especie(perro).raza(razaDesdeString("Beagle"))
                                .fechaNacimiento(calcularFechaNacimiento(3)).peso(12f)

                                .URLFoto("https://images.dog.ceo/breeds/beagle/n02088364_11136.jpg")
                                .estado(true).build());
                petsRepo.save(Pet.builder().nombre("Buddy").especie(perro)
                                .raza(razaDesdeString("Labrador retriever"))
                                .fechaNacimiento(calcularFechaNacimiento(5)).peso(30f)

                                .URLFoto("https://images.dog.ceo/breeds/labrador/n02099712_7937.jpg")
                                .estado(true).build());
                petsRepo.save(Pet.builder().nombre("Toby").especie(perro).raza(razaDesdeString("Carlino"))
                                .fechaNacimiento(calcularFechaNacimiento(2)).peso(8f)
                                .URLFoto("https://images.dog.ceo/breeds/pug/n02110958_11239.jpg")
                                .estado(true).build());
                petsRepo.save(Pet.builder().nombre("Zeus").especie(perro)
                                .raza(razaDesdeString("Husky siberiano"))
                                .fechaNacimiento(calcularFechaNacimiento(3)).peso(22f)
                                .URLFoto("https://images.dog.ceo/breeds/husky/n02110185_1469.jpg")
                                .estado(true).build());
                petsRepo.save(Pet.builder().nombre("Leo").especie(perro).raza(razaDesdeString("Cocker spaniel"))
                                .fechaNacimiento(calcularFechaNacimiento(4)).peso(13f)

                                .URLFoto("https://images.dog.ceo/breeds/spaniel-cocker/n02102318_1671.jpg")
                                .estado(true).build());
                petsRepo.save(Pet.builder().nombre("Duke").especie(perro)
                                .raza(razaDesdeString("Terrier de Norwich"))
                                .fechaNacimiento(calcularFechaNacimiento(6)).peso(6f)

                                .URLFoto("https://images.dog.ceo/breeds/terrier-norwich/n02094258_2070.jpg")
                                .estado(true).build());
                petsRepo.save(Pet.builder().nombre("Oliver").especie(perro)
                                .raza(razaDesdeString("Golden retriever"))
                                .fechaNacimiento(calcularFechaNacimiento(3)).peso(28f)

                                .URLFoto("https://images.dog.ceo/breeds/retriever-golden/n02099601_3004.jpg")
                                .estado(true).build());
                petsRepo.save(Pet.builder().nombre("Bentley").especie(perro)
                                .raza(razaDesdeString("Pastor de Shetland"))
                                .fechaNacimiento(calcularFechaNacimiento(2)).peso(10f)

                                .URLFoto("https://images.dog.ceo/breeds/sheepdog-shetland/n02105855_10095.jpg")
                                .estado(true).build());
                petsRepo.save(Pet.builder().nombre("Louie").especie(perro)
                                .raza(razaDesdeString("Terrier de Yorkshire"))
                                .fechaNacimiento(calcularFechaNacimiento(5)).peso(4f)

                                .URLFoto("https://images.dog.ceo/breeds/terrier-yorkshire/n02094433_3812.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Simba").especie(perro).raza(razaDesdeString("Bóxer"))
                                .fechaNacimiento(calcularFechaNacimiento(4)).peso(26f)
                                .URLFoto("https://images.dog.ceo/breeds/boxer/n02108089_9724.jpg")
                                .estado(true).build());
                petsRepo.save(Pet.builder().nombre("Cooper").especie(perro).raza(razaDesdeString("Dálmata"))
                                .fechaNacimiento(calcularFechaNacimiento(3)).peso(24f)
                                .URLFoto("https://images.dog.ceo/breeds/dalmatian/cooper2.jpg")
                                .estado(true).build());
                petsRepo.save(Pet.builder().nombre("Thor").especie(perro).raza(razaDesdeString("Bullmastiff"))
                                .fechaNacimiento(calcularFechaNacimiento(5)).peso(40f)

                                .URLFoto("https://images.dog.ceo/breeds/mastiff-bull/n02108422_5106.jpg")
                                .estado(true).build());
                petsRepo.save(Pet.builder().nombre("Kira").especie(perro).raza(razaDesdeString("Akita"))
                                .fechaNacimiento(calcularFechaNacimiento(4)).peso(32f)
                                .URLFoto("https://images.dog.ceo/breeds/akita/512px-Ainu-Dog.jpg")
                                .estado(true).build());
                petsRepo.save(Pet.builder().nombre("Loki").especie(perro).raza(razaDesdeString("Papillón"))
                                .fechaNacimiento(calcularFechaNacimiento(2)).peso(5f)

                                .URLFoto("https://images.dog.ceo/breeds/papillon/n02086910_7949.jpg")
                                .estado(true).build());
                petsRepo.save(Pet.builder().nombre("Rex").especie(perro)
                                .raza(razaDesdeString("Terrier americano"))
                                .fechaNacimiento(calcularFechaNacimiento(3)).peso(11f)

                                .URLFoto("https://images.dog.ceo/breeds/terrier-american/n02093428_1482.jpg")
                                .estado(true).build());
                petsRepo.save(Pet.builder().nombre("Coco").especie(perro).raza(razaDesdeString("Chihuahua"))
                                .fechaNacimiento(calcularFechaNacimiento(2)).peso(3f)

                                .URLFoto("https://images.dog.ceo/breeds/chihuahua/n02085620_7436.jpg")
                                .estado(true).build());
                petsRepo.save(Pet.builder().nombre("Bobby").especie(perro)
                                .raza(razaDesdeString("Terrier australiano"))
                                .fechaNacimiento(calcularFechaNacimiento(5)).peso(7f)

                                .URLFoto("https://images.dog.ceo/breeds/terrier-australian/n02096294_7175.jpg")
                                .estado(true).build());
                petsRepo.save(Pet.builder().nombre("Nala").especie(perro).raza(razaDesdeString("Dingo"))
                                .fechaNacimiento(calcularFechaNacimiento(4)).peso(18f)
                                .URLFoto("https://images.dog.ceo/breeds/dingo/n02115641_1228.jpg")
                                .estado(true).build());
                petsRepo.save(Pet.builder().nombre("Shadow").especie(perro)
                                .raza(razaDesdeString("Kelpie australiano"))
                                .fechaNacimiento(calcularFechaNacimiento(3)).peso(19f)

                                .URLFoto("https://images.dog.ceo/breeds/kelpie/n02105412_1973.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Bruno").especie(perro)
                                .raza(razaDesdeString("Malamute de Alaska"))
                                .fechaNacimiento(calcularFechaNacimiento(4)).peso(35f)

                                .URLFoto("https://images.dog.ceo/breeds/malamute/n02110063_13625.jpg")
                                .estado(true).build());
                petsRepo.save(Pet.builder().nombre("Apolo").especie(perro)
                                .raza(razaDesdeString("Galgo italiano"))
                                .fechaNacimiento(calcularFechaNacimiento(3)).peso(7f)

                                .URLFoto("https://images.dog.ceo/breeds/greyhound-italian/n02091032_1266.jpg")
                                .estado(true).build());
                petsRepo.save(Pet.builder().nombre("Hunter").especie(perro).raza(razaDesdeString("Otterhound"))
                                .fechaNacimiento(calcularFechaNacimiento(6)).peso(32f)

                                .URLFoto("https://images.dog.ceo/breeds/otterhound/n02091635_1319.jpg")
                                .estado(true).build());
                petsRepo.save(Pet.builder().nombre("Sasha").especie(perro).raza(razaDesdeString("Saluki"))
                                .fechaNacimiento(calcularFechaNacimiento(4)).peso(20f)

                                .URLFoto("https://images.dog.ceo/breeds/saluki/n02091831_5745.jpg")
                                .estado(true).build());
                petsRepo.save(Pet.builder().nombre("Koda").especie(perro).raza(razaDesdeString("Basenji"))
                                .fechaNacimiento(calcularFechaNacimiento(3)).peso(10f)

                                .URLFoto("https://images.dog.ceo/breeds/basenji/n02110806_4156.jpg")
                                .estado(true).build());
                petsRepo.save(Pet.builder().nombre("Balto").especie(perro)
                                .raza(razaDesdeString("Boyero de Flandes"))
                                .fechaNacimiento(calcularFechaNacimiento(5)).peso(34f)

                                .URLFoto("https://images.dog.ceo/breeds/bouvier/n02106382_9651.jpg")
                                .estado(true).build());
                petsRepo.save(Pet.builder().nombre("Ace").especie(perro).raza(razaDesdeString("Border collie"))
                                .fechaNacimiento(calcularFechaNacimiento(3)).peso(18f)

                                .URLFoto("https://images.dog.ceo/breeds/collie-border/n02106166_355.jpg")
                                .estado(true).build());
                petsRepo.save(Pet.builder().nombre("Maverick").especie(perro).raza(razaDesdeString("Komondor"))
                                .fechaNacimiento(calcularFechaNacimiento(6)).peso(45f)

                                .URLFoto("https://images.dog.ceo/breeds/komondor/n02105505_853.jpg")
                                .estado(true).build());
                petsRepo.save(Pet.builder().nombre("Sam").especie(perro).raza(razaDesdeString("Leonberger"))
                                .fechaNacimiento(calcularFechaNacimiento(4)).peso(48f)

                                .URLFoto("https://images.dog.ceo/breeds/leonberg/n02111129_4903.jpg")
                                .estado(true).build());
                petsRepo.save(Pet.builder().nombre("Oso").especie(perro).raza(razaDesdeString("Terranova"))
                                .fechaNacimiento(calcularFechaNacimiento(5)).peso(50f)

                                .URLFoto("https://images.dog.ceo/breeds/newfoundland/n02111277_3907.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Chop").especie(perro).raza(razaDesdeString("Rottweiler"))
                                .fechaNacimiento(calcularFechaNacimiento(4)).peso(42f)

                                .URLFoto("https://images.dog.ceo/breeds/rottweiler/n02106550_8776.jpg")
                                .estado(true).build());
                petsRepo.save(Pet.builder().nombre("Buster").especie(perro).raza(razaDesdeString("Chow chow"))
                                .fechaNacimiento(calcularFechaNacimiento(6)).peso(60f)
                                .URLFoto("https://images.dog.ceo/breeds/chow/n02112137_7428.jpg")
                                .estado(true).build());
                petsRepo.save(Pet.builder().nombre("Rita").especie(perro).raza(razaDesdeString("Weimaraner"))
                                .fechaNacimiento(calcularFechaNacimiento(3)).peso(25f)

                                .URLFoto("https://images.dog.ceo/breeds/weimaraner/n02092339_8024.jpg")
                                .estado(true).build());
                petsRepo.save(Pet.builder().nombre("Finn").especie(perro).raza(razaDesdeString("Whippet"))
                                .fechaNacimiento(calcularFechaNacimiento(2)).peso(9f)

                                .URLFoto("https://images.dog.ceo/breeds/whippet/n02091134_392.jpg")
                                .estado(true).build());
                petsRepo.save(Pet.builder().nombre("Oscar").especie(perro)
                                .raza(razaDesdeString("Terrier escocés"))
                                .fechaNacimiento(calcularFechaNacimiento(5)).peso(9f)

                                .URLFoto("https://images.dog.ceo/breeds/terrier-scottish/n02097298_2957.jpg")
                                .estado(true).build());
                petsRepo.save(Pet.builder().nombre("Jack").especie(perro)
                                .raza(razaDesdeString("West Highland white terrier"))
                                .fechaNacimiento(calcularFechaNacimiento(3)).peso(8f)

                                .URLFoto("https://images.dog.ceo/breeds/terrier-westhighland/n02098286_4120.jpg")
                                .estado(true).build());
                petsRepo.save(Pet.builder().nombre("Luna").especie(perro).raza(razaDesdeString("Vizsla"))
                                .fechaNacimiento(calcularFechaNacimiento(4)).peso(20f)

                                .URLFoto("https://images.dog.ceo/breeds/vizsla/n02100583_12660.jpg")
                                .estado(true).build());
                petsRepo.save(Pet.builder().nombre("Milo").especie(perro).raza(razaDesdeString("Pekinés"))
                                .fechaNacimiento(calcularFechaNacimiento(6)).peso(6f)

                                .URLFoto("https://images.dog.ceo/breeds/pekinese/n02086079_11089.jpg")
                                .estado(true).build());
                petsRepo.save(Pet.builder().nombre("Dexter").especie(perro)
                                .raza(razaDesdeString("Pinscher miniatura"))
                                .fechaNacimiento(calcularFechaNacimiento(4)).peso(5f)

                                .URLFoto("https://images.dog.ceo/breeds/pinscher-miniature/n02107312_1885.jpg")
                                .estado(true).build());
                petsRepo.save(Pet.builder().nombre("Ranger").especie(perro)
                                .raza(razaDesdeString("Pointer alemán"))
                                .fechaNacimiento(calcularFechaNacimiento(3)).peso(23f)

                                .URLFoto("https://images.dog.ceo/breeds/pointer-german/n02100236_689.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Archie").especie(perro)
                                .raza(razaDesdeString("Caniche miniatura"))
                                .fechaNacimiento(calcularFechaNacimiento(4)).peso(9f)

                                .URLFoto("https://images.dog.ceo/breeds/poodle-miniature/n02113712_3049.jpg")
                                .estado(true).build());
                petsRepo.save(Pet.builder().nombre("Musa").especie(perro)
                                .raza(razaDesdeString("Caniche estándar"))
                                .fechaNacimiento(calcularFechaNacimiento(5)).peso(22f)

                                .URLFoto("https://images.dog.ceo/breeds/poodle-standard/n02113799_448.jpg")
                                .estado(true).build());
                petsRepo.save(Pet.builder().nombre("Gizo").especie(perro).raza(razaDesdeString("Caniche toy"))
                                .fechaNacimiento(calcularFechaNacimiento(2)).peso(4f)

                                .URLFoto("https://images.dog.ceo/breeds/poodle-toy/n02113624_5459.jpg")
                                .estado(true).build());
                petsRepo.save(Pet.builder().nombre("Fausto").especie(perro)
                                .raza(razaDesdeString("Schnauzer miniatura"))
                                .fechaNacimiento(calcularFechaNacimiento(4)).peso(8f)

                                .URLFoto("https://images.dog.ceo/breeds/schnauzer-miniature/n02097047_6884.jpg")
                                .estado(true).build());
                petsRepo.save(Pet.builder().nombre("Manolo").especie(perro)
                                .raza(razaDesdeString("Setter inglés"))
                                .fechaNacimiento(calcularFechaNacimiento(3)).peso(24f)

                                .URLFoto("https://images.dog.ceo/breeds/setter-english/n02100735_7553.jpg")
                                .estado(true).build());
                petsRepo.save(Pet.builder().nombre("Pequitas").especie(perro)
                                .raza(razaDesdeString("Spaniel bretón"))
                                .fechaNacimiento(calcularFechaNacimiento(3)).peso(18f)

                                .URLFoto("https://images.dog.ceo/breeds/spaniel-brittany/n02101388_2429.jpg")
                                .estado(true).build());
                petsRepo.save(Pet.builder().nombre("Momoa").especie(perro)
                                .raza(razaDesdeString("Spaniel irlandés"))
                                .fechaNacimiento(calcularFechaNacimiento(5)).peso(20f)

                                .URLFoto("https://images.dog.ceo/breeds/spaniel-irish/n02102973_1377.jpg")
                                .estado(true).build());
                petsRepo.save(Pet.builder().nombre("Benji").especie(perro)
                                .raza(razaDesdeString("Terrier irlandés"))
                                .fechaNacimiento(calcularFechaNacimiento(4)).peso(12f)

                                .URLFoto("https://images.dog.ceo/breeds/terrier-irish/n02093991_1038.jpg")
                                .estado(true).build());
                petsRepo.save(Pet.builder().nombre("Rocco").especie(perro)
                                .raza(razaDesdeString("Terrier kerry blue"))
                                .fechaNacimiento(calcularFechaNacimiento(6)).peso(17f)

                                .URLFoto("https://images.dog.ceo/breeds/terrier-kerryblue/n02093859_1239.jpg")
                                .estado(true).build());
                petsRepo.save(Pet.builder().nombre("Lucky").especie(perro)
                                .raza(razaDesdeString("Terrier lakeland"))
                                .fechaNacimiento(calcularFechaNacimiento(3)).peso(9f)

                                .URLFoto("https://images.dog.ceo/breeds/terrier-lakeland/n02095570_1270.jpg")
                                .estado(true).build());

                // GATOSSSS --------------------------------------------------------

                petsRepo.save(Pet.builder().nombre("Luna").especie(gato).raza(razaDesdeString("Abyssinian"))
                                .fechaNacimiento(calcularFechaNacimiento(2)).peso(4.1f)
                                .URLFoto("https://cdn2.thecatapi.com/images/0XYvRd7oD.jpg")
                                .estado(true)
                                .build());

                petsRepo.save(Pet.builder().nombre("Milo").especie(gato).raza(razaDesdeString("Aegean"))
                                .fechaNacimiento(calcularFechaNacimiento(3)).peso(4.5f)
                                .URLFoto("https://cdn2.thecatapi.com/images/ozEvzdVM-.jpg")
                                .estado(true)
                                .build());

                petsRepo.save(Pet.builder().nombre("Nala").especie(gato).raza(razaDesdeString("American Bobtail"))
                                .fechaNacimiento(calcularFechaNacimiento(4)).peso(5.2f)
                                .URLFoto("https://cdn2.thecatapi.com/images/hBXicehMA.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Simba").especie(gato).raza(razaDesdeString("American Curl"))
                                .fechaNacimiento(calcularFechaNacimiento(1)).peso(3.8f)
                                .URLFoto("https://cdn2.thecatapi.com/images/xnsqonbjW.jpg")
                                .estado(true)
                                .build());

                petsRepo.save(Pet.builder().nombre("Oliver").especie(gato)
                                .raza(razaDesdeString("American Shorthair"))
                                .fechaNacimiento(calcularFechaNacimiento(5)).peso(6.1f)
                                .URLFoto("https://cdn2.thecatapi.com/images/JFPROfGtQ.jpg")
                                .estado(true)
                                .build());

                petsRepo.save(Pet.builder().nombre("Leo").especie(gato).raza(razaDesdeString("American Wirehair"))
                                .fechaNacimiento(calcularFechaNacimiento(3)).peso(5.0f)
                                .URLFoto("https://cdn2.thecatapi.com/images/8D--jCd21.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Chloe").especie(gato).raza(razaDesdeString("Arabian Mau"))
                                .fechaNacimiento(calcularFechaNacimiento(2)).peso(4.0f)
                                .URLFoto("https://cdn2.thecatapi.com/images/k71ULYfRr.jpg")
                                .estado(true)
                                .build());

                petsRepo.save(Pet.builder().nombre("Loki").especie(gato).raza(razaDesdeString("Australian Mist"))
                                .fechaNacimiento(calcularFechaNacimiento(4)).peso(4.9f)
                                .URLFoto("https://cdn2.thecatapi.com/images/_6x-3TiCA.jpg")
                                .estado(true)
                                .build());

                petsRepo.save(Pet.builder().nombre("Bella").especie(gato).raza(razaDesdeString("Balinese"))
                                .fechaNacimiento(calcularFechaNacimiento(3)).peso(4.4f)
                                .URLFoto("https://cdn2.thecatapi.com/images/13MkvUreZ.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Max").especie(gato).raza(razaDesdeString("Bambino"))
                                .fechaNacimiento(calcularFechaNacimiento(2)).peso(3.5f)
                                .URLFoto("https://cdn2.thecatapi.com/images/5AdhMjeEu.jpg")
                                .estado(true)
                                .build());

                petsRepo.save(Pet.builder().nombre("Kira").especie(gato).raza(razaDesdeString("Bengal"))
                                .fechaNacimiento(calcularFechaNacimiento(3)).peso(5.3f)
                                .URLFoto("https://cdn2.thecatapi.com/images/a8.jpg").estado(true)
                                .build());

                petsRepo.save(Pet.builder().nombre("Tom").especie(gato).raza(razaDesdeString("Birman"))
                                .fechaNacimiento(calcularFechaNacimiento(6)).peso(6.5f)
                                .URLFoto("https://cdn2.thecatapi.com/images/HOrX5gwLS.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Mia").especie(gato).raza(razaDesdeString("Bombay"))
                                .fechaNacimiento(calcularFechaNacimiento(4)).peso(4.7f)
                                .URLFoto("https://cdn2.thecatapi.com/images/5iYq9NmT1.jpg")
                                .estado(true)
                                .build());

                petsRepo.save(Pet.builder().nombre("Zeus").especie(gato).raza(razaDesdeString("British Longhair"))
                                .fechaNacimiento(calcularFechaNacimiento(5)).peso(6.2f)
                                .URLFoto("https://cdn2.thecatapi.com/images/7isAO4Cav.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Nina").especie(gato)
                                .raza(razaDesdeString("British Shorthair"))
                                .fechaNacimiento(calcularFechaNacimiento(2)).peso(4.8f)
                                .URLFoto("https://cdn2.thecatapi.com/images/9j5.jpg").estado(true)
                                .build());

                petsRepo.save(Pet.builder().nombre("Felix").especie(gato).raza(razaDesdeString("Burmese"))
                                .fechaNacimiento(calcularFechaNacimiento(3)).peso(4.6f)
                                .URLFoto("https://cdn2.thecatapi.com/images/4lXnnfxac.jpg")
                                .estado(true)
                                .build());

                petsRepo.save(Pet.builder().nombre("Coco").especie(gato).raza(razaDesdeString("Burmilla"))
                                .fechaNacimiento(calcularFechaNacimiento(4)).peso(5.1f)
                                .URLFoto("https://cdn2.thecatapi.com/images/b1.jpg").estado(true)
                                .build());

                petsRepo.save(Pet.builder().nombre("Sasha").especie(gato)
                                .raza(razaDesdeString("California Spangled"))
                                .fechaNacimiento(calcularFechaNacimiento(5)).peso(5.9f)
                                .URLFoto("https://cdn2.thecatapi.com/images/b9.jpg").estado(true)
                                .build());

                petsRepo.save(Pet.builder().nombre("Toby").especie(gato)
                                .raza(razaDesdeString("Chantilly-Tiffany"))
                                .fechaNacimiento(calcularFechaNacimiento(2)).peso(4.3f)
                                .URLFoto("https://cdn2.thecatapi.com/images/c3.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Daisy").especie(gato).raza(razaDesdeString("Chartreux"))
                                .fechaNacimiento(calcularFechaNacimiento(3)).peso(4.9f)
                                .URLFoto("https://cdn2.thecatapi.com/images/j6oFGLpRG.jpg")
                                .estado(true)
                                .build());

                petsRepo.save(Pet.builder().nombre("Rocky").especie(gato).raza(razaDesdeString("Chausie"))
                                .fechaNacimiento(calcularFechaNacimiento(4)).peso(6.0f)
                                .URLFoto("https://cdn2.thecatapi.com/images/c7.jpg").estado(true)
                                .build());

                petsRepo.save(Pet.builder().nombre("Lily").especie(gato).raza(razaDesdeString("Cornish Rex"))
                                .fechaNacimiento(calcularFechaNacimiento(2)).peso(3.9f)
                                .URLFoto("https://cdn2.thecatapi.com/images/unX21IBVB.jpg")
                                .estado(true)
                                .build());

                petsRepo.save(Pet.builder().nombre("Oscar").especie(gato).raza(razaDesdeString("Cymric"))
                                .fechaNacimiento(calcularFechaNacimiento(6)).peso(6.4f)
                                .URLFoto("https://cdn2.thecatapi.com/images/m2.jpg").estado(true)
                                .build());

                petsRepo.save(Pet.builder().nombre("Ruby").especie(gato).raza(razaDesdeString("Devon Rex"))
                                .fechaNacimiento(calcularFechaNacimiento(3)).peso(4.1f)
                                .URLFoto("https://cdn2.thecatapi.com/images/d9.jpg").estado(true)
                                .build());

                petsRepo.save(Pet.builder().nombre("Thor").especie(gato).raza(razaDesdeString("Donskoy"))
                                .fechaNacimiento(calcularFechaNacimiento(5)).peso(5.7f)
                                .URLFoto("https://cdn2.thecatapi.com/images/3KG57GfMW.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Maya").especie(gato).raza(razaDesdeString("Dragon Li"))
                                .fechaNacimiento(calcularFechaNacimiento(4)).peso(4.8f)
                                .URLFoto("https://cdn2.thecatapi.com/images/e2.jpg").estado(true)
                                .build());

                petsRepo.save(Pet.builder().nombre("Sam").especie(gato).raza(razaDesdeString("Egyptian Mau"))
                                .fechaNacimiento(calcularFechaNacimiento(2)).peso(4.3f)
                                .URLFoto("https://cdn2.thecatapi.com/images/k71ULYfRr.jpg")
                                .estado(true)
                                .build());

                petsRepo.save(Pet.builder().nombre("Zoe").especie(gato)
                                .raza(razaDesdeString("European Shorthair"))
                                .fechaNacimiento(calcularFechaNacimiento(3)).peso(4.6f)
                                .URLFoto("https://cdn2.thecatapi.com/images/0XYvRd7oD.jpg")
                                .estado(true)
                                .build());

                petsRepo.save(Pet.builder().nombre("Bobby").especie(gato)
                                .raza(razaDesdeString("Exotic Shorthair"))
                                .fechaNacimiento(calcularFechaNacimiento(5)).peso(6.3f)
                                .URLFoto("https://cdn2.thecatapi.com/images/e8.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Kiara").especie(gato).raza(razaDesdeString("Havana Brown"))
                                .fechaNacimiento(calcularFechaNacimiento(3)).peso(4.7f)
                                .URLFoto("https://cdn2.thecatapi.com/images/f1.jpg").estado(true)
                                .build());

                petsRepo.save(Pet.builder().nombre("Shadow").especie(gato).raza(razaDesdeString("Himalayan"))
                                .fechaNacimiento(calcularFechaNacimiento(6)).peso(6.8f)
                                .URLFoto("https://cdn2.thecatapi.com/images/f5.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Tina").especie(gato).raza(razaDesdeString("Japanese Bobtail"))
                                .fechaNacimiento(calcularFechaNacimiento(4)).peso(4.5f)
                                .URLFoto("https://cdn2.thecatapi.com/images/-tm9-znzl.jpg")
                                .estado(true)
                                .build());

                petsRepo.save(Pet.builder().nombre("Pipo").especie(gato).raza(razaDesdeString("Khao Manee"))
                                .fechaNacimiento(calcularFechaNacimiento(2)).peso(3.9f)
                                .URLFoto("https://cdn2.thecatapi.com/images/g2.jpg").estado(true)
                                .build());

                petsRepo.save(Pet.builder().nombre("Gala").especie(gato).raza(razaDesdeString("Korat"))
                                .fechaNacimiento(calcularFechaNacimiento(3)).peso(4.4f)
                                .URLFoto("https://cdn2.thecatapi.com/images/g7.jpg").estado(true)
                                .build());

                petsRepo.save(Pet.builder().nombre("Hugo").especie(gato).raza(razaDesdeString("Kurilian Bobtail"))
                                .fechaNacimiento(calcularFechaNacimiento(5)).peso(5.8f)
                                .URLFoto("https://cdn2.thecatapi.com/images/OUfeISEoi.jpg")
                                .estado(true)
                                .build());

                petsRepo.save(Pet.builder().nombre("Perla").especie(gato).raza(razaDesdeString("LaPerm"))
                                .fechaNacimiento(calcularFechaNacimiento(4)).peso(4.2f)
                                .URLFoto("https://cdn2.thecatapi.com/images/h3.jpg").estado(true)
                                .build());

                petsRepo.save(Pet.builder().nombre("Atlas").especie(gato).raza(razaDesdeString("Maine Coon"))
                                .fechaNacimiento(calcularFechaNacimiento(3)).peso(7.2f)
                                .URLFoto("https://cdn2.thecatapi.com/images/h8.jpg").estado(true)
                                .build());

                petsRepo.save(Pet.builder().nombre("Katy").especie(gato).raza(razaDesdeString("Manx"))
                                .fechaNacimiento(calcularFechaNacimiento(6)).peso(5.9f)
                                .URLFoto("https://cdn2.thecatapi.com/images/i1.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Mini").especie(gato).raza(razaDesdeString("Munchkin"))
                                .fechaNacimiento(calcularFechaNacimiento(2)).peso(3.4f)
                                .URLFoto("https://cdn2.thecatapi.com/images/5AdhMjeEu.jpg")
                                .estado(true)
                                .build());

                petsRepo.save(Pet.builder().nombre("Nero").especie(gato).raza(razaDesdeString("Nebelung"))
                                .fechaNacimiento(calcularFechaNacimiento(4)).peso(4.9f)
                                .URLFoto("https://cdn2.thecatapi.com/images/i6.jpg").estado(true)
                                .build());

                petsRepo.save(
                                Pet.builder().nombre("Freya").especie(gato)
                                                .raza(razaDesdeString("Norwegian Forest Cat"))
                                                .fechaNacimiento(calcularFechaNacimiento(5)).peso(6.7f)

                                                .URLFoto("https://cdn2.thecatapi.com/images/4lXnnfxac.jpg")
                                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Bolt").especie(gato).raza(razaDesdeString("Ocicat"))
                                .fechaNacimiento(calcularFechaNacimiento(3)).peso(5.0f)
                                .URLFoto("https://cdn2.thecatapi.com/images/j2.jpg").estado(true)
                                .build());

                petsRepo.save(Pet.builder().nombre("Misty").especie(gato)
                                .raza(razaDesdeString("Oriental Shorthair"))
                                .fechaNacimiento(calcularFechaNacimiento(4)).peso(4.3f)
                                .URLFoto("https://cdn2.thecatapi.com/images/0XYvRd7oD.jpg")
                                .estado(true)
                                .build());

                petsRepo.save(Pet.builder().nombre("Fluffy").especie(gato).raza(razaDesdeString("Persian"))
                                .fechaNacimiento(calcularFechaNacimiento(6)).peso(6.5f)
                                .URLFoto("https://cdn2.thecatapi.com/images/-Zfz5z2jK.jpg")
                                .estado(true).build());

                petsRepo.save(Pet.builder().nombre("Pixel").especie(gato).raza(razaDesdeString("Peterbald"))
                                .fechaNacimiento(calcularFechaNacimiento(2)).peso(3.6f)
                                .URLFoto("https://cdn2.thecatapi.com/images/j9.jpg").estado(true)
                                .build());

                petsRepo.save(Pet.builder().nombre("Snow").especie(gato).raza(razaDesdeString("Ragdoll"))
                                .fechaNacimiento(calcularFechaNacimiento(4)).peso(5.8f)
                                .URLFoto("https://cdn2.thecatapi.com/images/oGefY4YoG.jpg")
                                .estado(true)
                                .build());

                petsRepo.save(Pet.builder().nombre("Blue").especie(gato).raza(razaDesdeString("Russian Blue"))
                                .fechaNacimiento(calcularFechaNacimiento(3)).peso(4.4f)
                                .URLFoto("https://cdn2.thecatapi.com/images/k4.jpg").estado(true)
                                .build());

                petsRepo.save(Pet.builder().nombre("Hunter").especie(gato).raza(razaDesdeString("Savannah"))
                                .fechaNacimiento(calcularFechaNacimiento(5)).peso(7.0f)
                                .URLFoto("https://cdn2.thecatapi.com/images/-tm9-znzl.jpg")
                                .estado(true)
                                .build());

                petsRepo.save(Pet.builder().nombre("Poppy").especie(gato).raza(razaDesdeString("Scottish Fold"))
                                .fechaNacimiento(calcularFechaNacimiento(2)).peso(4.2f)
                                .URLFoto("https://cdn2.thecatapi.com/images/k8.jpg").estado(true)
                                .build());

                petsRepo.save(Pet.builder().nombre("Neo").especie(gato).raza(razaDesdeString("Sphynx"))
                                .fechaNacimiento(calcularFechaNacimiento(4)).peso(3.9f)
                                .URLFoto("https://cdn2.thecatapi.com/images/l3.jpg")
                                .estado(true).build());
        }

        private Raza razaDesdeString(String nombreRaza) {
                Raza raza = razaRepo.findByNombre(nombreRaza);
                return raza;
        }

        private Date calcularFechaNacimiento(int edadEnAnios) {
                LocalDate fecha = LocalDate.now().minusYears(edadEnAnios);
                return Date.valueOf(fecha);
        }

        public void relacionar() {
                Random random = new Random(42);

                for (Pet pet : petsRepo.findAll()) {
                        int randomNum = random.nextInt(1, clientsRepo.findAll().size());
                        Client client = clientsRepo.findById((long) randomNum).orElse(null);
                        pet.setOwner(client);
                        petsRepo.save(pet);
                }

                for (Pet pet : petsRepo.findAll()) {
                        int randomNum = random.nextInt(1, enfermedadRepo.findAll().size());
                        Enfermedad enfermedad = enfermedadRepo.findById((long) randomNum).orElse(null);
                        pet.setEnfermedad(enfermedad);
                        petsRepo.save(pet);
                }
        }

}
