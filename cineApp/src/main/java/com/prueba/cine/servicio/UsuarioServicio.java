package com.prueba.cine.servicio;

import com.prueba.cine.modelo.Usuario;
import com.prueba.cine.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscamos el usuario en la BD usando el repositorio que creamos antes
        Usuario usuario = usuarioRepositorio.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // Convertimos nuestro "Usuario" al "User" que entiende Spring Security
        return new User(
                usuario.getUsername(),
                usuario.getPassword(),
                Collections.emptyList() // Aquí irían los roles (ADMIN, USER), lo dejamos vacío por ahora
        );
    }
}