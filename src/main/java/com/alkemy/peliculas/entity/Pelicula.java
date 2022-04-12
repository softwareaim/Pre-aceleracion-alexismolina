package com.alkemy.peliculas.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter

@Entity
@Table(name = "peliculas")
public class Pelicula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imagen;

    private String titulo;

    private double calificacion;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "genero_id",referencedColumnName = "id")
    private Genero genero;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "personaje_pelicula",
              joinColumns = @JoinColumn(name = "personaje_id",nullable = false),
    inverseJoinColumns = @JoinColumn(name = "pelicula_id",nullable = false),
    uniqueConstraints = {@UniqueConstraint(columnNames = {"personaje_id","pelicula_id"})})
    private List<Personaje> personajes = new ArrayList<>();


    public void addPersonaje(Personaje personaje){
        this.personajes.add(personaje);
    }

    public void removePersonaje(Personaje personaje){
        this.personajes.remove(personaje);
    }
}
