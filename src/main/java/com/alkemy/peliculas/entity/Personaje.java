package com.alkemy.peliculas.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter

@Entity
@Table(name = "personajes")
@SQLDelete(sql = "UPDATE personajes SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Personaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Ingrese un nombre")
    private String nombre;

    private String imagen;

    private Integer edad;

    private Double peso;

    private String historia;

    private Boolean deleted = Boolean.FALSE;

    @ManyToMany(mappedBy = "personajes",cascade ={CascadeType.MERGE,CascadeType.PERSIST})
    private Set<Pelicula> peliculas = new HashSet<>();
}
