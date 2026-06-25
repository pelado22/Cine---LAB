package com.prueba.cine.seguridad;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class FiltroPeticiones implements Filter {

    private final Map<String, Long> registroPeticiones = new ConcurrentHashMap<>();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        // EXCEPCIÓN DE SEGURIDAD: Dejar pasar libremente los archivos de diseño (CSS, JS, Imágenes)
        String uri = req.getRequestURI();
        if (uri.startsWith("/css/") || uri.startsWith("/js/") || uri.startsWith("/img/")) {
            chain.doFilter(request, response);
            return; // no evaluar lmt tiempo
        }

        String ipCliente = req.getRemoteAddr();
        long tiempoActual = System.currentTimeMillis();

        // limite: si hizo otra peticion en menos de 500 milisegundos
        if (registroPeticiones.containsKey(ipCliente)) {
            long ultimaPeticion = registroPeticiones.get(ipCliente);
            
            if (tiempoActual - ultimaPeticion < 500) {
                res.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                res.setContentType("text/plain;charset=UTF-8");
                res.getWriter().write("Error 429: Demasiadas peticiones. Por favor, espera un momento.");
                return; 
            }
        }

        registroPeticiones.put(ipCliente, tiempoActual);
        chain.doFilter(request, response);
    }
}