package com.alkemy.peliculas.mapper.entity;

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
import java.util.Objects;

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

    @NotEmpty
    private String titulo;

    @Range(min = 0, max = 5)
    private double calificacion;

    private Boolean deleted = Boolean.FALSE;

    @NotNull
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pelicula pelicula = (Pelicula) o;

        if (Double.compare(pelicula.calificacion, calificacion) != 0) return false;
        if (!Objects.equals(id, pelicula.id)) return false;
        return Objects.equals(personajes, pelicula.personajes);
    }


    public void removePersonaje(Personaje personaje){
        this.personajes.remove(personaje);

    }
}
