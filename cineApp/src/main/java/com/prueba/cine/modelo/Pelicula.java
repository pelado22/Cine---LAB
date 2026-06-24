package com.prueba.cine.modelo;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
//import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "peliculas")
public class Pelicula {


    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (name="titulo", nullable = false, length = 40)
    //@NotBlank(message="El título no puede estar vacio")
    public String titulo;
    @Column (name="protagonista",nullable = false, length = 30)
    public String protagonista;
    @Column(name = "año", nullable = false)
    private int año;


    //@OneToOne
    @ManyToOne
    @JoinColumn(name = "genero_id" /*,nullable = false*/)
    protected Genero genero;




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
    public String getProtagonista() {
        return protagonista;
    }
    public void setProtagonista(String protagonista) {
        this.protagonista = protagonista;
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


 

    

}


    

