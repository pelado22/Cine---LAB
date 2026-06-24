package com.prueba.cine.modelo;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "peliculas")
public class Pelicula {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column (name="titulo", nullable = false, length = 40)
    public String titulo;
    
    @Column(name = "año", nullable = false)
    private int año;

    @ManyToOne
    @JoinColumn(name = "genero_id")
    protected Genero genero;

    // Relación Muchos a Muchos con Actor (tabla intermedia pelicula_actor)
    @ManyToMany
    @JoinTable(
        name = "pelicula_actor",
        joinColumns = @JoinColumn(name = "pelicula_id"),
        inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private List<Actor> actores = new ArrayList<>();

    // Relación Muchos a Uno con Director
    @ManyToOne
    @JoinColumn(name = "director_id")
    private Director director;

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

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public List<Actor> getActores() {
        return actores;
    }

    public void setActores(List<Actor> actores) {
        this.actores = actores;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    
}