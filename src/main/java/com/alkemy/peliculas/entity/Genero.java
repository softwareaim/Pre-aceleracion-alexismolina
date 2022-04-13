package com.alkemy.peliculas.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter

@Entity
@Table(name = "generos")
@SQLDelete(sql = "UPDATE generos SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Genero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String imagen;

    private Boolean deleted = Boolean.FALSE;
}
