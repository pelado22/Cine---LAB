package com.prueba.cine.modelo;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "directores")
public class Director {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    // Un director puede dirigir muchas películas
    @OneToMany(mappedBy = "director")
    private List<Pelicula> peliculas = new ArrayList<>();

    // Constructores, Getters y Setters...
    public Director() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public List<Pelicula> getPeliculas() { return peliculas; }
    public void setPeliculas(List<Pelicula> peliculas) { this.peliculas = peliculas; }
}