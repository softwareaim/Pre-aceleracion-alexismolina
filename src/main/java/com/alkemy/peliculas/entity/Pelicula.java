package com.alkemy.peliculas.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name = "genero_id",referencedColumnName = "id")
    private Genero genero;

    @ManyToMany(mappedBy = "peliculas",cascade = CascadeType.ALL)
    private List<Personaje> personajes = new ArrayList<>();

}
