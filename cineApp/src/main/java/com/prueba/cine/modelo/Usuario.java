package com.prueba.cine.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String username;

    @Column(nullable = false)
    private String password;

   @Column(name = "intentos_fallidos")
    private Integer intentosFallidos = 0;

    @Column(name = "cuenta_bloqueada")
    private Boolean cuentaBloqueada = false;
    // Constructores, Getters y Setters
    public Usuario() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
public int getIntentosFallidos() { 
        // Si la base de datos devuelve nulo, asumimos que tiene 0 intentos
        return intentosFallidos == null ? 0 : intentosFallidos; 
    }
    
    public void setIntentosFallidos(Integer intentosFallidos) { 
        this.intentosFallidos = intentosFallidos; 
    }
    
    public boolean isCuentaBloqueada() { 
        // Si la base de datos devuelve nulo, asumimos que NO está bloqueada (false)
        return cuentaBloqueada != null && cuentaBloqueada; 
    }
    
    public void setCuentaBloqueada(Boolean cuentaBloqueada) { 
        this.cuentaBloqueada = cuentaBloqueada; 
    }
}
