# Documentación de Seguridad - CINEMAX

Este documento detalla las capas de seguridad implementadas en la plataforma CINEMAX para garantizar la integridad, disponibilidad y protección del panel de gestión frente a vulnerabilidades Web comunes.

## 1. Autenticación y Cifrado (Hashing)
* **Tecnología:** Spring Security 6 & BCrypt.
* **Descripción:** Toda ruta de la aplicación se encuentra protegida globalmente. Las credenciales de los administradores se procesan mediante el algoritmo criptográfico **BCrypt** (`BCryptPasswordEncoder`). Este incluye la generación de *Salts* aleatorios por cada registro, previniendo ataques de Tablas Arcoíris (Rainbow Tables) en caso de una filtración de datos.

## 2. Mitigación de Fuerza Bruta (Account Lockout)
* **Tecnología:** Patrón *Event Listener* de Spring Security.
* **Descripción:** Se implementó una política de protección de fuerza bruta respaldada en la base de datos (Entidad `Usuario`). 
* **Regla:** El sistema monitorea los eventos `AuthenticationFailureBadCredentialsEvent`. Si un usuario falla su autenticación **5 veces consecutivas**, el atributo `cuentaBloqueada` cambia a `true`. Desde ese instante, Spring Security emite un `LockedException` que impide el inicio de sesión, incluso si en un futuro intento el atacante adivina la contraseña correcta. El contador de la base de datos se restablece automáticamente a cero (`0`) en un ingreso exitoso para evitar falsos positivos acumulativos.

## 3. Limitación de Tasa (Rate Limiting Anti-Spam)
* **Tecnología:** Interceptor de Servlets (`Filter` de Jakarta EE).
* **Descripción:** Se diseñó el componente `FiltroPeticiones` apoyado en un mapa de memoria volátil (`ConcurrentHashMap`) para auditar la frecuencia de peticiones HTTP por dirección IP. 
* **Regla:** Se rechaza mediante un código HTTP `429 Too Many Requests` cualquier conexión de una IP que realice iteraciones con una frecuencia inferior a los 500 milisegundos.
* **Objetivo:** Prevenir denegación de servicio (DoS) a nivel de la capa de aplicación y bloquear ataques automatizados mediante scripts o *bots*.

## 4. Gestión Estricta de Sesión y Tiempo de Vida (Timeout)
* **Descripción:** La aplicación no permite sesiones "zombies" o eternas, lo que significa un riesgo latente de secuestro de sesión (Session Hijacking) si el administrador deja el terminal desatendido.
* **Configuración:** A nivel contenedor (Tomcat), en el `application.properties`, se estableció la regla `server.servlet.session.timeout=15m`. Transcurridos 15 minutos de inactividad de red, el JSESSIONID se invalida y el servidor purga el contexto de autenticación de la RAM, obligando al usuario a identificarse nuevamente.

## 5. Prevención de CSRF e Inyección SQL
* **Protección CSRF:** Activada de forma nativa por Spring Security. El motor de vistas Thymeleaf inyecta de manera imperceptible un *Token CSRF* criptográfico en cada formulario `POST` (ej: Alta de películas, Logout), garantizando que las acciones provengan explícitamente desde nuestra UI y no de ataques maliciosos externos.
* **Protección SQLi:** Gracias a la abstracción ORM provista por Spring Data JPA y Hibernate, toda entrada proveniente del usuario viaja a través de *Consultas Preparadas* (Prepared Statements). Esto santifica las comillas simples o comandos maliciosos introducidos en los `inputs` HTML, tratándolos estrictamente como cadenas de texto sin valor ejecutable para MySQL.