package com.prueba.cine;

import com.prueba.cine.modelo.Usuario;
import com.prueba.cine.repositorio.UsuarioRepositorio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class TestApplication {

    public static void main(String[] args) {
        // Arranca el servidor web embebido (Tomcat) e inicializa el contexto de Spring
        SpringApplication.run(TestApplication.class, args);
    }

    /**
     * Bean encargado de la inicialización de datos (Data Seeding).
     * Se ejecuta de forma automática inmediatamente después de que el contexto de la aplicación arranca.
     */
    @Bean
    public CommandLineRunner initData(UsuarioRepositorio usuarioRep, PasswordEncoder passwordEncoder) {
        return args -> {
            // Buscamos si ya existe el usuario "admin" en la base de datos
            if (usuarioRep.findByUsername("admin").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setUsername("admin");
                
                // CRÍTICO: Encriptamos la contraseña utilizando BCrypt antes de guardarla.
                // Spring Security rechazará contraseñas en texto plano por motivos de seguridad.
                admin.setPassword(passwordEncoder.encode("admin123")); 
                
                usuarioRep.save(admin);
                System.out.println(">>> Base de datos: Usuario administrador inicial ('admin' / 'admin123') creado.");
            }
        };
    }
}