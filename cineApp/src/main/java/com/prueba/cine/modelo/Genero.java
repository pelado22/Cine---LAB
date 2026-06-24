package com.prueba.cine.modelo;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
//import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


//import com.prueba.cine.modelo.Pelicula;


@Entity
@Table (name= "genero")
public class Genero {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (name="titulo", nullable = false, length = 30)
    protected String titulo;

  // 3. Relación correcta: un género tiene muchas películas
    @OneToMany(mappedBy = "genero")
    private List<Pelicula> peliculas = new ArrayList<>();

// Constructor vacío
    public Genero() {}

    public Genero(Long id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    // Getters y Setters para la lista
    public List<Pelicula> getPeliculas() {
        return peliculas;
    }
    public void setPeliculas(List<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }
}

    


