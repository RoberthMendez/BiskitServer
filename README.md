# 🐾 Biskit – Sistema de Gestión para Veterinaria

## Contexto del negocio

La clínica veterinaria recibe mascotas que necesitan permanecer hospitalizadas durante algunos días mientras reciben tratamiento médico.

Cuando un cliente llega por primera vez al establecimiento, el veterinario registra sus datos personales en el sistema, incluyendo:
<img width="256" height="134" alt="image" src="https://github.com/user-attachments/assets/57b52511-a998-4b04-a3b7-f1894960c326" />

- Nombre
- Cédula
- Correo electrónico
- Número de celular

Posteriormente se registran los datos de la mascota. Entre los datos que se almacenan se encuentran:

- Nombre
- Especie
- Raza
- Edad
- Peso
- Enfermedad
- Fotografía

Durante su estancia en la veterinaria, los veterinarios realizan diferentes atenciones médicas hasta que la mascota se recupera.

Una vez la mascota es dada de alta y regresa a casa, su información **no se elimina del sistema**, sino que pasa a un estado **inactivo**, permitiendo conservar el historial de mascotas atendidas por la veterinaria.

---

## Tecnologías utilizadas

### Backend

- Java
- Spring Boot
- Spring MVC
- Spring Data JPA
- Hibernate

### Base de datos

- H2 Database

### Frontend

- Thymeleaf
- TailwindCSS
- HTML
- JavaScript

### Herramientas

- Maven
- Lombok
- Git
- GitHub

---

## Arquitectura del sistema

El proyecto está organizado siguiendo una arquitectura por capas que permite separar las responsabilidades dentro del sistema y mantener el código más organizado.

### Controller

Los controladores se encargan de manejar las solicitudes HTTP que llegan al sistema y definir las rutas de la aplicación.

### Service

En esta capa se encuentra la lógica de negocio del sistema. Los servicios procesan la información antes de enviarla a la base de datos.

### Repository

Los repositorios permiten interactuar con la base de datos utilizando Spring Data JPA.

### Entities

Las entidades representan las tablas de la base de datos y contienen los atributos de cada modelo del sistema.

### Errors

El proyecto incluye un manejo de errores que permite mostrar páginas personalizadas cuando ocurre algún problema dentro de la aplicación.

---

## Modelo de datos

Actualmente el sistema maneja dos entidades principales: **Client** y **Pet**.

### Cliente (Client)

La entidad Client representa a los dueños de las mascotas registradas en la veterinaria.

Cada cliente almacena información como:

- nombre
- cédula
- correo electrónico
- celular
- usuario
- contraseña

Un cliente puede tener varias mascotas registradas en el sistema.

---

### Mascota (Pet)

La entidad Pet representa una mascota registrada dentro de la veterinaria.

Los datos principales que se almacenan son:

- nombre
- especie
- raza
- edad
- peso
- enfermedad
- foto
- estado

Cada mascota pertenece a un cliente.

---

## Estados de las mascotas

Las mascotas pueden encontrarse en dos estados dentro del sistema.

**Activo**
La mascota se encuentra actualmente en tratamiento dentro de la veterinaria.

**Inactivo**
La mascota ya fue dada de alta y regresó a casa.

Este enfoque permite conservar el historial de mascotas sin eliminar registros de la base de datos.

---

## Funcionalidades implementadas

Actualmente el sistema cuenta con varias funcionalidades principales.

### Landing Page

El sistema cuenta con una página principal donde se presenta la veterinaria y desde donde se puede acceder a las diferentes secciones del sistema.

### Login de clientes

Los clientes pueden iniciar sesión utilizando su usuario y contraseña.

### Visualización de mascotas

Una vez autenticado, el cliente puede visualizar únicamente las mascotas asociadas a su cuenta.

### Portal veterinario

El personal veterinario puede administrar la información del sistema.

Entre las funcionalidades disponibles están:

- Registrar nuevos clientes
- Consultar clientes registrados
- Actualizar información de clientes
- Eliminar clientes

También se pueden gestionar mascotas:

- Registrar nuevas mascotas
- Consultar mascotas existentes
- Editar información de mascotas
- Cambiar el estado de una mascota

Las mascotas **no se eliminan directamente del sistema**, únicamente se cambia su estado a **inactivo**.

---

## Base de datos

El proyecto utiliza **H2 Database**, una base de datos en memoria que permite ejecutar el sistema sin necesidad de instalar un motor de base de datos adicional.

Al iniciar la aplicación se cargan automáticamente datos de prueba que incluyen aproximadamente:

- 50 clientes
- 100 mascotas

Esto permite probar el funcionamiento del sistema desde el inicio.

---

## Cómo ejecutar el proyecto

### 1. Clonar el repositorio

```
git clone https://github.com/RoberthMendez/Biskit.git
```

### 2. Buscar el test y correr el BiskitApplication.java

cuando encontramos el .java lo abrimos

### 3. Corremos

```
buscamos el boton para correr el proyecto y le damos compilar
```

---

## Estructura del proyecto

```
src/main/java/com/example/biskit

controller
service
repo
entities
errors
BiskitApplication.java
DataLoader.java

src/main/resources

templates
static
application.properties
```

---

## Integrantes del equipo

- Guden Silva Rojas
- Luz Salazar Varon
- Roberth Mendez Rivera
- Santiago Martinez Lopez

---

## Estado actual del proyecto

Actualmente el sistema cumple con los requerimientos establecidos para el **Sprint 4 del proyecto**:

- Implementación de entidades utilizando **JPA**
- Restricciones en los atributos de las entidades (`nullable`, `unique`, `length`)
- Base de datos **H2** con datos de prueba
- Login funcional para clientes
- Visualización de mascotas asociadas a cada dueño
- CRUD de clientes
- CRUD de mascotas
- Página de manejo de errores
- Eliminación lógica de mascotas mediante cambio de estado
