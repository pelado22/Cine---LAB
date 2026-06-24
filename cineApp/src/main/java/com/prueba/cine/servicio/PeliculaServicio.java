package com.prueba.cine.servicio;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.prueba.cine.modelo.Pelicula;
import com.prueba.cine.repositorio.PeliculaRepositorio;


@Service
public class PeliculaServicio {
    
    @Autowired
    private PeliculaRepositorio peliculaRep;

    public Pelicula save(Pelicula pelicula) {
        return peliculaRep.save(pelicula);
    }

    public List<Pelicula> findAll() {
        return peliculaRep.findAll();
    }

    public Optional<Pelicula> findById(Long id) {
        return peliculaRep.findById(id);
    }

    public void deleteById(Long id) {
        peliculaRep.deleteById(id);
    }
}