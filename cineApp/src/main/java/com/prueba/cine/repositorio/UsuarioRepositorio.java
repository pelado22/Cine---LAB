package com.prueba.cine.repositorio;

import com.prueba.cine.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
    
    // Spring Data JPA implementará la consulta SQL automáticamente "SELECT * FROM usuarios WHERE username = ?"
    Optional<Usuario> findByUsername(String username);
}