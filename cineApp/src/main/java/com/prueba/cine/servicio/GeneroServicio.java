package com.prueba.cine.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.prueba.cine.modelo.Genero;
import com.prueba.cine.repositorio.GeneroRepositorio;

@Service
public class GeneroServicio {

    @Autowired
    private GeneroRepositorio generoRepositorio;

    public List<Genero> findAll() {
        return generoRepositorio.findAll();
    }

    public Optional<Genero> findById(Long id) {
        return generoRepositorio.findById(id);
    }

    public Genero save(Genero genero) {
        return generoRepositorio.save(genero);
    }
}
