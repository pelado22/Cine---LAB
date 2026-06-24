package com.prueba.cine.seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**", "/js/**", "/img/**").permitAll() // Permitir diseño sin login
                .anyRequest().authenticated() // Cualquier otra ruta pedirá iniciar sesión
            )
            .formLogin(login -> login
                .loginPage("/login") // Indicamos nuestra pantalla de login personalizada
                .defaultSuccessUrl("/", true) // A dónde ir al ingresar con éxito (página principal)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout") // A dónde ir al cerrar sesión
                .permitAll()
            );
        return http.build();
    }

    // BCrypt es el estándar actual para encriptar contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}