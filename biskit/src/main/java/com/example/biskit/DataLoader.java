package com.example.biskit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.example.biskit.entities.Client;
import com.example.biskit.entities.Especie;
import com.example.biskit.entities.Estado;
import com.example.biskit.entities.Pet;
import com.example.biskit.repo.ClientsRepo;
import com.example.biskit.repo.PetsRepo;
import java.util.Random;

@Component
@Transactional
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ClientsRepo clientsRepo;

    @Autowired
    private PetsRepo petsRepo;

    @Override
    public void run(String... args) throws Exception {
        // Crear clientes de ejemplo
        cargarClientes();
        cargarMascotas();
        relacionar();
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
                .celular("3₀₀₀₀₀₀₀₆").usuario("user6").password("pass").build());
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

        clientsRepo.save(
                Client.builder().nombre("Roberth Mendez").cedula("1025").correo("roberth25@mail.com")
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
        clientsRepo.save(Client.builder().nombre("Patricia Cárdenas").cedula("1032").correo("patricia32@mail.com")
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
        clientsRepo.save(Client.builder().nombre("Sebastián Beltrán").cedula("1045").correo("sebastian45@mail.com")
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
        // PERROS ----------------------------------------------------
        petsRepo.save(Pet.builder().nombre("Princesa").especie(Especie.PERRO).raza("Lebrel afgano").edad(4).peso(27f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/hound-afghan/n02088094_1406.jpg")
                .estado(Estado.ACTIVO).build());
        petsRepo.save(Pet.builder().nombre("Charlie").especie(Especie.PERRO).raza("Beagle").edad(3).peso(12f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/beagle/n02088364_11136.jpg")
                .estado(Estado.ACTIVO).build());
        petsRepo.save(Pet.builder().nombre("Buddy").especie(Especie.PERRO).raza("Labrador retriever").edad(5).peso(30f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/labrador/n02099712_7937.jpg")
                .estado(Estado.ACTIVO).build());
        petsRepo.save(Pet.builder().nombre("Toby").especie(Especie.PERRO).raza("Carlino").edad(2).peso(8f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/pug/n02110958_11239.jpg")
                .estado(Estado.ACTIVO).build());
        petsRepo.save(Pet.builder().nombre("Zeus").especie(Especie.PERRO).raza("Husky siberiano").edad(3).peso(22f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/husky/n02110185_1469.jpg")
                .estado(Estado.ACTIVO).build());
        petsRepo.save(Pet.builder().nombre("Leo").especie(Especie.PERRO).raza("Cocker spaniel").edad(4).peso(13f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/spaniel-cocker/n02102318_1671.jpg")
                .estado(Estado.ACTIVO).build());
        petsRepo.save(Pet.builder().nombre("Duke").especie(Especie.PERRO).raza("Terrier de Norwich").edad(6).peso(6f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/terrier-norwich/n02094258_2070.jpg")
                .estado(Estado.ACTIVO).build());
        petsRepo.save(Pet.builder().nombre("Oliver").especie(Especie.PERRO).raza("Golden retriever").edad(3).peso(28f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/retriever-golden/n02099601_3004.jpg")
                .estado(Estado.ACTIVO).build());
        petsRepo.save(Pet.builder().nombre("Bentley").especie(Especie.PERRO).raza("Pastor de Shetland").edad(2)
                .peso(10f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/sheepdog-shetland/n02105855_10095.jpg")
                .estado(Estado.ACTIVO).build());
        petsRepo.save(Pet.builder().nombre("Louie").especie(Especie.PERRO).raza("Terrier de Yorkshire").edad(5).peso(4f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/terrier-yorkshire/n02094433_3812.jpg")
                .estado(Estado.ACTIVO).build());

        petsRepo.save(Pet.builder().nombre("Simba").especie(Especie.PERRO).raza("Bóxer").edad(4).peso(26f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/boxer/n02108089_9724.jpg")
                .estado(Estado.ACTIVO).build());
        petsRepo.save(Pet.builder().nombre("Cooper").especie(Especie.PERRO).raza("Dálmata").edad(3).peso(24f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/dalmatian/cooper2.jpg")
                .estado(Estado.ACTIVO).build());
        petsRepo.save(Pet.builder().nombre("Thor").especie(Especie.PERRO).raza("Bullmastiff").edad(5).peso(40f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/mastiff-bull/n02108422_5106.jpg")
                .estado(Estado.ACTIVO).build());
        petsRepo.save(Pet.builder().nombre("Kira").especie(Especie.PERRO).raza("Akita").edad(4).peso(32f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/akita/512px-Ainu-Dog.jpg")
                .estado(Estado.ACTIVO).build());
        petsRepo.save(Pet.builder().nombre("Loki").especie(Especie.PERRO).raza("Papillón").edad(2).peso(5f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/papillon/n02086910_7949.jpg")
                .estado(Estado.ACTIVO).build());
        petsRepo.save(Pet.builder().nombre("Rex").especie(Especie.PERRO).raza("Terrier americano").edad(3).peso(11f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/terrier-american/n02093428_1482.jpg")
                .estado(Estado.ACTIVO).build());
        petsRepo.save(Pet.builder().nombre("Coco").especie(Especie.PERRO).raza("Chihuahua").edad(2).peso(3f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/chihuahua/n02085620_7436.jpg")
                .estado(Estado.ACTIVO).build());
        petsRepo.save(Pet.builder().nombre("Bobby").especie(Especie.PERRO).raza("Terrier australiano").edad(5).peso(7f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/terrier-australian/n02096294_7175.jpg")
                .estado(Estado.ACTIVO).build());
        petsRepo.save(Pet.builder().nombre("Nala").especie(Especie.PERRO).raza("Dingo").edad(4).peso(18f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/dingo/n02115641_1228.jpg")
                .estado(Estado.ACTIVO).build());
        petsRepo.save(Pet.builder().nombre("Shadow").especie(Especie.PERRO).raza("Kelpie australiano").edad(3).peso(19f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/kelpie/n02105412_1973.jpg")
                .estado(Estado.ACTIVO).build());

        petsRepo.save(Pet.builder().nombre("Bruno").especie(Especie.PERRO).raza("Malamute de Alaska").edad(4).peso(35f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/malamute/n02110063_13625.jpg")
                .estado(Estado.ACTIVO).build());
        petsRepo.save(Pet.builder().nombre("Apolo").especie(Especie.PERRO).raza("Galgo italiano").edad(3).peso(7f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/greyhound-italian/n02091032_1266.jpg")
                .estado(Estado.ACTIVO).build());
        petsRepo.save(Pet.builder().nombre("Hunter").especie(Especie.PERRO).raza("Otterhound").edad(6).peso(32f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/otterhound/n02091635_1319.jpg")
                .estado(Estado.ACTIVO).build());
        petsRepo.save(Pet.builder().nombre("Sasha").especie(Especie.PERRO).raza("Saluki").edad(4).peso(20f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/saluki/n02091831_5745.jpg")
                .estado(Estado.ACTIVO).build());
        petsRepo.save(Pet.builder().nombre("Koda").especie(Especie.PERRO).raza("Basenji").edad(3).peso(10f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/basenji/n02110806_4156.jpg")
                .estado(Estado.ACTIVO).build());
        petsRepo.save(Pet.builder().nombre("Balto").especie(Especie.PERRO).raza("Boyero de Flandes").edad(5).peso(34f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/bouvier/n02106382_9651.jpg")
                .estado(Estado.ACTIVO).build());
        petsRepo.save(Pet.builder().nombre("Ace").especie(Especie.PERRO).raza("Border collie").edad(3).peso(18f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/collie-border/n02106166_355.jpg")
                .estado(Estado.ACTIVO).build());
        petsRepo.save(Pet.builder().nombre("Maverick").especie(Especie.PERRO).raza("Komondor").edad(6).peso(45f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/komondor/n02105505_853.jpg")
                .estado(Estado.ACTIVO).build());
        petsRepo.save(Pet.builder().nombre("Sam").especie(Especie.PERRO).raza("Leonberger").edad(4).peso(48f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/leonberg/n02111129_4903.jpg")
                .estado(Estado.ACTIVO).build());
        petsRepo.save(Pet.builder().nombre("Oso").especie(Especie.PERRO).raza("Terranova").edad(5).peso(50f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/newfoundland/n02111277_3907.jpg")
                .estado(Estado.ACTIVO).build());

        petsRepo.save(Pet.builder().nombre("Chop").especie(Especie.PERRO).raza("Rottweiler").edad(4).peso(42f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/rottweiler/n02106550_8776.jpg")
                .estado(Estado.ACTIVO).build());
        petsRepo.save(Pet.builder().nombre("Buster").especie(Especie.PERRO).raza("Chow chow").edad(6).peso(60f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/chow/n02112137_7428.jpg")
                .estado(Estado.ACTIVO).build());
        petsRepo.save(Pet.builder().nombre("Rita").especie(Especie.PERRO).raza("Weimaraner").edad(3).peso(25f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/weimaraner/n02092339_8024.jpg")
                .estado(Estado.ACTIVO).build());
        petsRepo.save(Pet.builder().nombre("Finn").especie(Especie.PERRO).raza("Whippet").edad(2).peso(9f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/whippet/n02091134_392.jpg")
                .estado(Estado.ACTIVO).build());
        petsRepo.save(Pet.builder().nombre("Oscar").especie(Especie.PERRO).raza("Terrier escocés").edad(5).peso(9f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/terrier-scottish/n02097298_2957.jpg")
                .estado(Estado.ACTIVO).build());
        petsRepo.save(Pet.builder().nombre("Jack").especie(Especie.PERRO).raza("West Highland white terrier").edad(3)
                .peso(8f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/terrier-westhighland/n02098286_4120.jpg")
                .estado(Estado.ACTIVO).build());
        petsRepo.save(Pet.builder().nombre("Luna").especie(Especie.PERRO).raza("Vizsla").edad(4).peso(20f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/vizsla/n02100583_12660.jpg")
                .estado(Estado.ACTIVO).build());
        petsRepo.save(Pet.builder().nombre("Milo").especie(Especie.PERRO).raza("Pekinés").edad(6).peso(6f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/pekinese/n02086079_11089.jpg")
                .estado(Estado.ACTIVO).build());
        petsRepo.save(Pet.builder().nombre("Dexter").especie(Especie.PERRO).raza("Pinscher miniatura").edad(4).peso(5f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/pinscher-miniature/n02107312_1885.jpg")
                .estado(Estado.ACTIVO).build());
        petsRepo.save(Pet.builder().nombre("Ranger").especie(Especie.PERRO).raza("Pointer alemán").edad(3).peso(23f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/pointer-german/n02100236_689.jpg")
                .estado(Estado.ACTIVO).build());

        petsRepo.save(Pet.builder().nombre("Archie").especie(Especie.PERRO).raza("Caniche miniatura").edad(4).peso(9f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/poodle-miniature/n02113712_3049.jpg")
                .estado(Estado.ACTIVO).build());
        petsRepo.save(Pet.builder().nombre("Musa").especie(Especie.PERRO).raza("Caniche estándar").edad(5).peso(22f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/poodle-standard/n02113799_448.jpg")
                .estado(Estado.ACTIVO).build());
        petsRepo.save(Pet.builder().nombre("Gizo").especie(Especie.PERRO).raza("Caniche toy").edad(2).peso(4f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/poodle-toy/n02113624_5459.jpg")
                .estado(Estado.ACTIVO).build());
        petsRepo.save(Pet.builder().nombre("Fausto").especie(Especie.PERRO).raza("Schnauzer miniatura").edad(4).peso(8f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/schnauzer-miniature/n02097047_6884.jpg")
                .estado(Estado.ACTIVO).build());
        petsRepo.save(Pet.builder().nombre("Manolo").especie(Especie.PERRO).raza("Setter inglés").edad(3).peso(24f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/setter-english/n02100735_7553.jpg")
                .estado(Estado.ACTIVO).build());
        petsRepo.save(Pet.builder().nombre("Pequitas").especie(Especie.PERRO).raza("Spaniel bretón").edad(3).peso(18f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/spaniel-brittany/n02101388_2429.jpg")
                .estado(Estado.ACTIVO).build());
        petsRepo.save(Pet.builder().nombre("Momoa").especie(Especie.PERRO).raza("Spaniel irlandés").edad(5).peso(20f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/spaniel-irish/n02102973_1377.jpg")
                .estado(Estado.ACTIVO).build());
        petsRepo.save(Pet.builder().nombre("Benji").especie(Especie.PERRO).raza("Terrier irlandés").edad(4).peso(12f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/terrier-irish/n02093991_1038.jpg")
                .estado(Estado.ACTIVO).build());
        petsRepo.save(Pet.builder().nombre("Rocco").especie(Especie.PERRO).raza("Terrier kerry blue").edad(6).peso(17f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/terrier-kerryblue/n02093859_1239.jpg")
                .estado(Estado.ACTIVO).build());
        petsRepo.save(Pet.builder().nombre("Lucky").especie(Especie.PERRO).raza("Terrier lakeland").edad(3).peso(9f)
                .enfermedad("Ninguna").URLFoto("https://images.dog.ceo/breeds/terrier-lakeland/n02095570_1270.jpg")
                .estado(Estado.ACTIVO).build());

        // GATOSSSS --------------------------------------------------------

        petsRepo.save(Pet.builder().nombre("Luna").especie(Especie.GATO).raza("Abyssinian").edad(2).peso(4.1f)
                .enfermedad("Ninguna").URLFoto("https://cdn2.thecatapi.com/images/0XYvRd7oD.jpg").estado(Estado.ACTIVO)
                .build());

        petsRepo.save(Pet.builder().nombre("Milo").especie(Especie.GATO).raza("Aegean").edad(3).peso(4.5f)
                .enfermedad("Ninguna").URLFoto("https://cdn2.thecatapi.com/images/ozEvzdVM-.jpg").estado(Estado.ACTIVO)
                .build());

        petsRepo.save(Pet.builder().nombre("Nala").especie(Especie.GATO).raza("American Bobtail").edad(4).peso(5.2f)
                .enfermedad("Alergia leve").URLFoto("https://cdn2.thecatapi.com/images/hBXicehMA.jpg")
                .estado(Estado.ACTIVO).build());

        petsRepo.save(Pet.builder().nombre("Simba").especie(Especie.GATO).raza("American Curl").edad(1).peso(3.8f)
                .enfermedad("Ninguna").URLFoto("https://cdn2.thecatapi.com/images/xnsqonbjW.jpg").estado(Estado.ACTIVO)
                .build());

        petsRepo.save(Pet.builder().nombre("Oliver").especie(Especie.GATO).raza("American Shorthair").edad(5).peso(6.1f)
                .enfermedad("Ninguna").URLFoto("https://cdn2.thecatapi.com/images/JFPROfGtQ.jpg").estado(Estado.ACTIVO)
                .build());

        petsRepo.save(Pet.builder().nombre("Leo").especie(Especie.GATO).raza("American Wirehair").edad(3).peso(5.0f)
                .enfermedad("Dermatitis").URLFoto("https://cdn2.thecatapi.com/images/8D--jCd21.jpg")
                .estado(Estado.ACTIVO).build());

        petsRepo.save(Pet.builder().nombre("Chloe").especie(Especie.GATO).raza("Arabian Mau").edad(2).peso(4.0f)
                .enfermedad("Ninguna").URLFoto("https://cdn2.thecatapi.com/images/k71ULYfRr.jpg").estado(Estado.ACTIVO)
                .build());

        petsRepo.save(Pet.builder().nombre("Loki").especie(Especie.GATO).raza("Australian Mist").edad(4).peso(4.9f)
                .enfermedad("Ninguna").URLFoto("https://cdn2.thecatapi.com/images/_6x-3TiCA.jpg").estado(Estado.ACTIVO)
                .build());

        petsRepo.save(Pet.builder().nombre("Bella").especie(Especie.GATO).raza("Balinese").edad(3).peso(4.4f)
                .enfermedad("Problema dental").URLFoto("https://cdn2.thecatapi.com/images/13MkvUreZ.jpg")
                .estado(Estado.ACTIVO).build());

        petsRepo.save(Pet.builder().nombre("Max").especie(Especie.GATO).raza("Bambino").edad(2).peso(3.5f)
                .enfermedad("Ninguna").URLFoto("https://cdn2.thecatapi.com/images/5AdhMjeEu.jpg").estado(Estado.ACTIVO)
                .build());

        petsRepo.save(Pet.builder().nombre("Kira").especie(Especie.GATO).raza("Bengal").edad(3).peso(5.3f)
                .enfermedad("Ninguna").URLFoto("https://cdn2.thecatapi.com/images/a8.jpg").estado(Estado.ACTIVO)
                .build());

        petsRepo.save(Pet.builder().nombre("Tom").especie(Especie.GATO).raza("Birman").edad(6).peso(6.5f)
                .enfermedad("Artritis leve").URLFoto("https://cdn2.thecatapi.com/images/HOrX5gwLS.jpg")
                .estado(Estado.ACTIVO).build());

        petsRepo.save(Pet.builder().nombre("Mia").especie(Especie.GATO).raza("Bombay").edad(4).peso(4.7f)
                .enfermedad("Ninguna").URLFoto("https://cdn2.thecatapi.com/images/5iYq9NmT1.jpg").estado(Estado.ACTIVO)
                .build());

        petsRepo.save(Pet.builder().nombre("Zeus").especie(Especie.GATO).raza("British Longhair").edad(5).peso(6.2f)
                .enfermedad("Sobrepeso").URLFoto("https://cdn2.thecatapi.com/images/7isAO4Cav.jpg")
                .estado(Estado.ACTIVO).build());

        petsRepo.save(Pet.builder().nombre("Nina").especie(Especie.GATO).raza("British Shorthair").edad(2).peso(4.8f)
                .enfermedad("Ninguna").URLFoto("https://cdn2.thecatapi.com/images/9j5.jpg").estado(Estado.ACTIVO)
                .build());

        petsRepo.save(Pet.builder().nombre("Felix").especie(Especie.GATO).raza("Burmese").edad(3).peso(4.6f)
                .enfermedad("Ninguna").URLFoto("https://cdn2.thecatapi.com/images/4lXnnfxac.jpg").estado(Estado.ACTIVO)
                .build());

        petsRepo.save(Pet.builder().nombre("Coco").especie(Especie.GATO).raza("Burmilla").edad(4).peso(5.1f)
                .enfermedad("Ninguna").URLFoto("https://cdn2.thecatapi.com/images/b1.jpg").estado(Estado.ACTIVO)
                .build());

        petsRepo.save(Pet.builder().nombre("Sasha").especie(Especie.GATO).raza("California Spangled").edad(5).peso(5.9f)
                .enfermedad("Ninguna").URLFoto("https://cdn2.thecatapi.com/images/b9.jpg").estado(Estado.ACTIVO)
                .build());

        petsRepo.save(Pet.builder().nombre("Toby").especie(Especie.GATO).raza("Chantilly-Tiffany").edad(2).peso(4.3f)
                .enfermedad("Conjuntivitis").URLFoto("https://cdn2.thecatapi.com/images/c3.jpg")
                .estado(Estado.ACTIVO).build());

        petsRepo.save(Pet.builder().nombre("Daisy").especie(Especie.GATO).raza("Chartreux").edad(3).peso(4.9f)
                .enfermedad("Ninguna").URLFoto("https://cdn2.thecatapi.com/images/j6oFGLpRG.jpg").estado(Estado.ACTIVO)
                .build());

        petsRepo.save(Pet.builder().nombre("Rocky").especie(Especie.GATO).raza("Chausie").edad(4).peso(6.0f)
                .enfermedad("Ninguna").URLFoto("https://cdn2.thecatapi.com/images/c7.jpg").estado(Estado.ACTIVO)
                .build());

        petsRepo.save(Pet.builder().nombre("Lily").especie(Especie.GATO).raza("Cornish Rex").edad(2).peso(3.9f)
                .enfermedad("Ninguna").URLFoto("https://cdn2.thecatapi.com/images/unX21IBVB.jpg").estado(Estado.ACTIVO)
                .build());

        petsRepo.save(Pet.builder().nombre("Oscar").especie(Especie.GATO).raza("Cymric").edad(6).peso(6.4f)
                .enfermedad("Artritis").URLFoto("https://cdn2.thecatapi.com/images/m2.jpg").estado(Estado.ACTIVO)
                .build());

        petsRepo.save(Pet.builder().nombre("Ruby").especie(Especie.GATO).raza("Devon Rex").edad(3).peso(4.1f)
                .enfermedad("Ninguna").URLFoto("https://cdn2.thecatapi.com/images/d9.jpg").estado(Estado.ACTIVO)
                .build());

        petsRepo.save(Pet.builder().nombre("Thor").especie(Especie.GATO).raza("Donskoy").edad(5).peso(5.7f)
                .enfermedad("Problema de piel").URLFoto("https://cdn2.thecatapi.com/images/3KG57GfMW.jpg")
                .estado(Estado.ACTIVO).build());

        petsRepo.save(Pet.builder().nombre("Maya").especie(Especie.GATO).raza("Dragon Li").edad(4).peso(4.8f)
                .enfermedad("Ninguna").URLFoto("https://cdn2.thecatapi.com/images/e2.jpg").estado(Estado.ACTIVO)
                .build());

        petsRepo.save(Pet.builder().nombre("Sam").especie(Especie.GATO).raza("Egyptian Mau").edad(2).peso(4.3f)
                .enfermedad("Ninguna").URLFoto("https://cdn2.thecatapi.com/images/k71ULYfRr.jpg").estado(Estado.ACTIVO)
                .build());

        petsRepo.save(Pet.builder().nombre("Zoe").especie(Especie.GATO).raza("European Shorthair").edad(3).peso(4.6f)
                .enfermedad("Ninguna").URLFoto("https://cdn2.thecatapi.com/images/0XYvRd7oD.jpg").estado(Estado.ACTIVO)
                .build());

        petsRepo.save(Pet.builder().nombre("Bobby").especie(Especie.GATO).raza("Exotic Shorthair").edad(5).peso(6.3f)
                .enfermedad("Sobrepeso").URLFoto("https://cdn2.thecatapi.com/images/e8.jpg")
                .estado(Estado.ACTIVO).build());

        petsRepo.save(Pet.builder().nombre("Kiara").especie(Especie.GATO).raza("Havana Brown").edad(3).peso(4.7f)
                .enfermedad("Ninguna").URLFoto("https://cdn2.thecatapi.com/images/f1.jpg").estado(Estado.ACTIVO)
                .build());

        petsRepo.save(Pet.builder().nombre("Shadow").especie(Especie.GATO).raza("Himalayan").edad(6).peso(6.8f)
                .enfermedad("Problema respiratorio").URLFoto("https://cdn2.thecatapi.com/images/f5.jpg")
                .estado(Estado.ACTIVO).build());

        petsRepo.save(Pet.builder().nombre("Tina").especie(Especie.GATO).raza("Japanese Bobtail").edad(4).peso(4.5f)
                .enfermedad("Ninguna").URLFoto("https://cdn2.thecatapi.com/images/-tm9-znzl.jpg").estado(Estado.ACTIVO)
                .build());

        petsRepo.save(Pet.builder().nombre("Pipo").especie(Especie.GATO).raza("Khao Manee").edad(2).peso(3.9f)
                .enfermedad("Ninguna").URLFoto("https://cdn2.thecatapi.com/images/g2.jpg").estado(Estado.ACTIVO)
                .build());

        petsRepo.save(Pet.builder().nombre("Gala").especie(Especie.GATO).raza("Korat").edad(3).peso(4.4f)
                .enfermedad("Ninguna").URLFoto("https://cdn2.thecatapi.com/images/g7.jpg").estado(Estado.ACTIVO)
                .build());

        petsRepo.save(Pet.builder().nombre("Hugo").especie(Especie.GATO).raza("Kurilian Bobtail").edad(5).peso(5.8f)
                .enfermedad("Ninguna").URLFoto("https://cdn2.thecatapi.com/images/OUfeISEoi.jpg").estado(Estado.ACTIVO)
                .build());

        petsRepo.save(Pet.builder().nombre("Perla").especie(Especie.GATO).raza("LaPerm").edad(4).peso(4.2f)
                .enfermedad("Ninguna").URLFoto("https://cdn2.thecatapi.com/images/h3.jpg").estado(Estado.ACTIVO)
                .build());

        petsRepo.save(Pet.builder().nombre("Atlas").especie(Especie.GATO).raza("Maine Coon").edad(3).peso(7.2f)
                .enfermedad("Ninguna").URLFoto("https://cdn2.thecatapi.com/images/h8.jpg").estado(Estado.ACTIVO)
                .build());

        petsRepo.save(Pet.builder().nombre("Katy").especie(Especie.GATO).raza("Manx").edad(6).peso(5.9f)
                .enfermedad("Problema digestivo").URLFoto("https://cdn2.thecatapi.com/images/i1.jpg")
                .estado(Estado.ACTIVO).build());

        petsRepo.save(Pet.builder().nombre("Mini").especie(Especie.GATO).raza("Munchkin").edad(2).peso(3.4f)
                .enfermedad("Ninguna").URLFoto("https://cdn2.thecatapi.com/images/5AdhMjeEu.jpg").estado(Estado.ACTIVO)
                .build());

        petsRepo.save(Pet.builder().nombre("Nero").especie(Especie.GATO).raza("Nebelung").edad(4).peso(4.9f)
                .enfermedad("Ninguna").URLFoto("https://cdn2.thecatapi.com/images/i6.jpg").estado(Estado.ACTIVO)
                .build());

        petsRepo.save(
                Pet.builder().nombre("Freya").especie(Especie.GATO).raza("Norwegian Forest Cat").edad(5).peso(6.7f)
                        .enfermedad("Ninguna").URLFoto("https://cdn2.thecatapi.com/images/4lXnnfxac.jpg")
                        .estado(Estado.ACTIVO).build());

        petsRepo.save(Pet.builder().nombre("Bolt").especie(Especie.GATO).raza("Ocicat").edad(3).peso(5.0f)
                .enfermedad("Ninguna").URLFoto("https://cdn2.thecatapi.com/images/j2.jpg").estado(Estado.ACTIVO)
                .build());

        petsRepo.save(Pet.builder().nombre("Misty").especie(Especie.GATO).raza("Oriental Shorthair").edad(4).peso(4.3f)
                .enfermedad("Ninguna").URLFoto("https://cdn2.thecatapi.com/images/0XYvRd7oD.jpg").estado(Estado.ACTIVO)
                .build());

        petsRepo.save(Pet.builder().nombre("Fluffy").especie(Especie.GATO).raza("Persian").edad(6).peso(6.5f)
                .enfermedad("Problema ocular").URLFoto("https://cdn2.thecatapi.com/images/-Zfz5z2jK.jpg")
                .estado(Estado.ACTIVO).build());

        petsRepo.save(Pet.builder().nombre("Pixel").especie(Especie.GATO).raza("Peterbald").edad(2).peso(3.6f)
                .enfermedad("Ninguna").URLFoto("https://cdn2.thecatapi.com/images/j9.jpg").estado(Estado.ACTIVO)
                .build());

        petsRepo.save(Pet.builder().nombre("Snow").especie(Especie.GATO).raza("Ragdoll").edad(4).peso(5.8f)
                .enfermedad("Ninguna").URLFoto("https://cdn2.thecatapi.com/images/oGefY4YoG.jpg").estado(Estado.ACTIVO)
                .build());

        petsRepo.save(Pet.builder().nombre("Blue").especie(Especie.GATO).raza("Russian Blue").edad(3).peso(4.4f)
                .enfermedad("Ninguna").URLFoto("https://cdn2.thecatapi.com/images/k4.jpg").estado(Estado.ACTIVO)
                .build());

        petsRepo.save(Pet.builder().nombre("Hunter").especie(Especie.GATO).raza("Savannah").edad(5).peso(7.0f)
                .enfermedad("Ninguna").URLFoto("https://cdn2.thecatapi.com/images/-tm9-znzl.jpg").estado(Estado.ACTIVO)
                .build());

        petsRepo.save(Pet.builder().nombre("Poppy").especie(Especie.GATO).raza("Scottish Fold").edad(2).peso(4.2f)
                .enfermedad("Ninguna").URLFoto("https://cdn2.thecatapi.com/images/k8.jpg").estado(Estado.ACTIVO)
                .build());

        petsRepo.save(Pet.builder().nombre("Neo").especie(Especie.GATO).raza("Sphynx").edad(4).peso(3.9f)
                .enfermedad("Problema de piel").URLFoto("https://cdn2.thecatapi.com/images/l3.jpg")
                .estado(Estado.ACTIVO).build());
    }

    public void relacionar() {
        Random random = new Random(42);

        for (Pet pet : petsRepo.findAll()) {
            int randomNum = random.nextInt(1, clientsRepo.findAll().size());
            Client client = clientsRepo.findById((long) randomNum).orElse(null);
            pet.setOwner(client);
            petsRepo.save(pet);
        }
    }

}
