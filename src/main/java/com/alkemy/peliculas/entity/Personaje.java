package com.alkemy.peliculas.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter

@Entity
@Table(name = "personajes")
public class Personaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String imagen;

    private Integer edad;

    private Double peso;

    private String historia;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "personaje_pelicula",
            joinColumns = @JoinColumn(name = "personaje_id"),
           inverseJoinColumns = @JoinColumn(name = "pelicula_id"))
    private Set<Pelicula> peliculas = new HashSet<>();
}
