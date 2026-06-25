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

    private static final int MAX_INTENTOS = 5;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // constructor extendido de User de Spring Security
        // El penúltimo parámetro es "accountNonLocked" (cuenta NO bloqueada)
        return new User(
                usuario.getUsername(),
                usuario.getPassword(),
                true, // cuenta habilitada
                true, // cuenta no expirada
                true, // credenciales no expiradas
                !usuario.isCuentaBloqueada(), // ¡Validación de bloqueo!
                Collections.emptyList()
        );
    }

    // --- LÓGICA DE SEGURIDAD ---
    public void registrarIntentoFallido(String username) {
        usuarioRepositorio.findByUsername(username).ifPresent(usuario -> {
            if (!usuario.isCuentaBloqueada()) {
                usuario.setIntentosFallidos(usuario.getIntentosFallidos() + 1);
                if (usuario.getIntentosFallidos() >= MAX_INTENTOS) {
                    usuario.setCuentaBloqueada(true);
                    System.out.println("ALERTA DE SEGURIDAD: Cuenta '" + username + "' bloqueada por exceso de intentos.");
                }
                usuarioRepositorio.save(usuario);
            }
        });
    }

    public void resetearIntentos(String username) {
        usuarioRepositorio.findByUsername(username).ifPresent(usuario -> {
            usuario.setIntentosFallidos(0);
            usuario.setCuentaBloqueada(false); // Desbloquea en caso de que un admin lo requiera
            usuarioRepositorio.save(usuario);
        });
    }
}