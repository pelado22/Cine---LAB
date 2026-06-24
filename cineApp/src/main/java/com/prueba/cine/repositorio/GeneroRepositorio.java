package com.prueba.cine.repositorio;

import com.prueba.cine.modelo.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneroRepositorio extends JpaRepository<Genero, Long> {


    
}
