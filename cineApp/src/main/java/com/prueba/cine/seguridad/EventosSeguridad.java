package com.prueba.cine.seguridad;

import com.prueba.cine.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class EventosSeguridad {

    @Autowired
    private UsuarioServicio usuarioServicio;

    // Escucha cuando alguien PONE MAL la contraseña
    @EventListener
    public void alFallarLogin(AuthenticationFailureBadCredentialsEvent event) {
        // Obtenemos el usuario que intentó ingresar mal
        String username = (String) event.getAuthentication().getPrincipal();
        usuarioServicio.registrarIntentoFallido(username);
    }

    // Escucha cuando alguien PONE BIEN la contraseña
    @EventListener
    public void alAcertarLogin(AuthenticationSuccessEvent event) {
        UserDetails user = (UserDetails) event.getAuthentication().getPrincipal();
        // Si ingresa bien, el contador vuelve a cero
        usuarioServicio.resetearIntentos(user.getUsername());
    }
}