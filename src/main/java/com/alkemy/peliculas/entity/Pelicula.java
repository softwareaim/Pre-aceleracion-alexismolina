package com.alkemy.peliculas.entity;

import lombok.Data;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

@Data

@Entity
@Table(name = "peliculas")
@SQLDelete(sql = "UPDATE peliculas SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Pelicula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imagen;

    @NotEmpty(message = "Ingrese un titulo")
    private String titulo;

    @Range(min = 0, max = 5, message = "el rango de calificacion deber ser entre 0 y 5")
    private double calificacion;

    private Boolean deleted = Boolean.FALSE;

    @NotNull(message = "Ingrese una fecha de creacion")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "genero_id",referencedColumnName = "id")
    private Genero genero;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "personaje_pelicula",
              joinColumns = @JoinColumn(name = "pelicula_id",nullable = false),
    inverseJoinColumns = @JoinColumn(name = "personaje_id",nullable = false),
    uniqueConstraints = {@UniqueConstraint(columnNames = {"pelicula_id","personaje_id"})})
    private List<Personaje> personajes = new ArrayList<>();

    public void addPersonaje(Personaje personaje){
        this.personajes.add(personaje);
    }

    public void removePersonaje(Personaje personaje){
        this.personajes.remove(personaje);
    }
}
