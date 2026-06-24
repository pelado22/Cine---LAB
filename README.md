# 🎬 CINEMAX - Sistema de Gestión de Películas

**Trabajo Final - Laboratorio de Programación (2024-2026)** **Desarrollado por:** Matias Viña ([@b1gpp](https://github.com/pelado22))

---

##  Descripción del Proyecto
CINEMAX es una aplicación web de arquitectura monolítica desarrollada bajo el framework **Spring Boot**. El sistema permite administrar un catálogo de películas, gestionando sus respectivos géneros, actores y directores mediante operaciones CRUD (Crear, Leer, Actualizar y Eliminar). Además, incorpora un sistema de seguridad y autenticación para restringir el acceso únicamente a usuarios registrados.


---

## Tecnologías Utilizadas y Justificación

A continuación, se detallan las herramientas elegidas para el desarrollo y el motivo académico/técnico de su implementación:

* **Java (JDK 17+) & Spring Boot 3.3.0**
  * *¿Por qué?* Spring Boot simplifica enormemente la configuración inicial (autoconfiguración) y provee un servidor web embebido (Tomcat). Esto nos permite ejecutar la aplicación de forma independiente sin necesidad de desplegar un servidor de aplicaciones complejo externo.
* **Spring Data JPA & Hibernate**
  * *¿Por qué?* Se utilizó para abstraer la capa de acceso a datos (Patrón Repository). JPA nos permite mapear nuestras clases Java directamente a tablas relacionales en MySQL (ORM). Se utilizaron relaciones `@OneToMany` (Director -> Películas) y `@ManyToMany` (Actores <-> Películas) para respetar la cardinalidad lógica del modelo de negocio sin escribir SQL puro.
* **Spring Security 6 & BCrypt**
  * *¿Por qué?* Para cumplir con el requerimiento del login, no basta con hacer un simple `SELECT` en la base de datos. Spring Security intercepta las peticiones y protege las rutas (`/peliculas`, `/new`, etc.). Además, se utilizó `BCryptPasswordEncoder` porque guardar contraseñas en texto plano es una vulnerabilidad crítica; BCrypt genera un hash seguro e irreversible.
* **Thymeleaf (Motor de plantillas) con extensiones de seguridad**
  * *¿Por qué?* Al ser una aplicación monolítica, Thymeleaf permite renderizar HTML dinámico en el lado del servidor. Se eligió por su integración nativa con Spring. Se usa el namespace de Spring Security (`sec:authorize`) para ocultar botones o mostrar el nombre del usuario directamente en la vista según el estado de su sesión.
* **MySQL (vía XAMPP)**
  * *¿Por qué?* Se optó por un motor de base de datos relacional robusto y estándar en la industria, ideal para manejar las relaciones entre entidades.
* **CSS3 Custom (Minimalist UI)**
  * *¿Por qué?* Se implementó un diseño modular mediante hojas de estilo propias para tener control total sobre la paleta de colores y las transiciones, logrando una presentación de la vista de autenticación y el panel de control.

---

## Configuraciones Principales del Sistema

### 1. `application.properties`
El archivo de propiedades fue configurado para automatizar la persistencia. Se destacan los siguientes parámetros:
* `createDatabaseIfNotExist=true`: Anexado a la URL de conexión JDBC. Si el evaluador clona el repositorio, no necesita ejecutar scripts SQL manualmente; al arrancar, el programa crea la base de datos `cine` por sí solo.
* `spring.jpa.hibernate.ddl-auto=update`: Permite que Hibernate lea las entidades (`@Entity`) y cree o modifique las tablas correspondientes automáticamente sin destruir los datos existentes. *(Nota: En un entorno de producción real, esto se desactivaría a favor de herramientas de migración como Flyway o Liquibase).*

### 2. Data Seeding (Inicialización Automática)
Se implementó un `CommandLineRunner` en la clase principal `TestApplication`.
* *¿Por qué?* Para facilitar las pruebas del sistema. Al compilar la aplicación, este componente verifica si las tablas están vacías usando `count() == 0`. Si lo están, inserta automáticamente un catálogo de géneros, un listado de directores y un **usuario administrador por defecto**. Esto asegura que el sistema sea funcional en el primer inicio sin configuración manual extra.

---

## Guía de Instalación y Uso (API/Consideraciones)

### Requisitos Previos
1. Tener instalado **XAMPP** (o cualquier servidor MySQL activo en el puerto `3306`).
2. Tener instalado **Java JDK 17** o superior.

### Pasos para ejecutar:
1. Abrir XAMPP e iniciar el módulo de **MySQL**.
2. Clonar el repositorio y abrir el proyecto en el IDE (Eclipse, VS Code, IntelliJ).
3. Ejecutar la clase principal `TestApplication.java` (o ejecutar `./mvnw spring-boot:run` en la terminal).
4. El sistema creará la base de datos y las tablas automáticamente.
5. Abrir el navegador e ingresar a: `http://localhost:8081/`

### Credenciales de Acceso (Generadas automáticamente)
El sistema está restringido. Para evaluar la plataforma, utilizar las siguientes credenciales:
* **Usuario:** `admin`
* **Contraseña:** `admin123`

### Consideraciones sobre el Flujo de la Aplicación
* **Protección de Rutas:** Cualquier intento de acceder a `http://localhost:8081/peliculas` sin sesión iniciada, será interceptado por el `SecurityFilterChain` y redirigido al formulario `/login`.
* **Manejo de Relaciones Múltiples:** En el formulario de creación/edición de películas, el campo de *Actores* es de selección múltiple (mantener presionado `Ctrl` / `Cmd` para elegir varios), lo cual impacta directamente en la tabla intermedia autogenerada `pelicula_actor`.
* **Cierre de Sesión Seguro:** El botón "Cerrar sesión" en la barra de navegación (Navbar) efectúa una petición `POST` hacia `/logout`, invalidando el token de seguridad y limpiando el contexto del servidor.

**SE SEGUIRAN INTEGRANDO CAMBIOS**
