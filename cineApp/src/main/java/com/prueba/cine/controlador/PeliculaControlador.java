package com.prueba.cine.controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.prueba.cine.modelo.Pelicula;
import com.prueba.cine.servicio.PeliculaServicio;
import com.prueba.cine.modelo.Genero;
import com.prueba.cine.servicio.GeneroServicio;

@Controller
@RequestMapping("/peliculas")
public class PeliculaControlador {

    @Autowired
    private PeliculaServicio peliculaServ;

    @Autowired
    private GeneroServicio generoServ;
    


    @GetMapping
    public String peliculas(Model model) {
        List<Pelicula> peliculas = peliculaServ.findAll();
        model.addAttribute("peliculas", peliculas);
        return "peliculas";
    }

    @GetMapping("/new")
    public String showNewForm(Model model) {
        Pelicula pelicula = new Pelicula();
        List<Genero> generos = generoServ.findAll();
        model.addAttribute("pelicula", pelicula);
        model.addAttribute("generos", generos);
        return "aggPelicula";
    }

    @PostMapping
    public String savePelicula(@ModelAttribute("pelicula") Pelicula pelicula) {
        peliculaServ.save(pelicula);
        return "redirect:/peliculas";
    }

    @GetMapping("/editar/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Optional<Pelicula> peliculaOptional = peliculaServ.findById(id);
        if (peliculaOptional.isPresent()) {
            Pelicula pelicula = peliculaOptional.get();
            List<Genero> generos = generoServ.findAll();
            model.addAttribute("pelicula", pelicula);
            model.addAttribute("generos", generos);
            return "editar";
        } else {
            return "redirect:/peliculas"; 
        }
    }

@PostMapping("/editar/{id}")
public String updatePelicula(@PathVariable("id") Long id, @ModelAttribute("pelicula") Pelicula peliculaActualizada) {
    Optional<Pelicula> peliculaOptional = peliculaServ.findById(id);
    if (peliculaOptional.isPresent()) {
        Pelicula existingPelicula = peliculaOptional.get();
        existingPelicula.setTitulo(peliculaActualizada.getTitulo());
        existingPelicula.setProtagonista(peliculaActualizada.getProtagonista());
        existingPelicula.setAño(peliculaActualizada.getAño());

        // Si el objeto recibido tiene el ID de género, búscalo en el servicio
        if (peliculaActualizada.getGenero() != null && peliculaActualizada.getGenero().getId() != null) {
            Genero genero = generoServ.findById(peliculaActualizada.getGenero().getId()).orElse(null);
            existingPelicula.setGenero(genero);
        }
        peliculaServ.save(existingPelicula);
        return "redirect:/peliculas";
    }
    return "redirect:/peliculas";
}

    @GetMapping("/delete/{id}")
    public String deletePelicula(@PathVariable("id") Long id) {
        peliculaServ.deleteById(id);
        return "redirect:/peliculas";
    }
}
