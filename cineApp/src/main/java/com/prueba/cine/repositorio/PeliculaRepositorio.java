
package com.prueba.cine.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.prueba.cine.modelo.Pelicula;;


@Repository
public interface PeliculaRepositorio extends JpaRepository<Pelicula, Long> {



}