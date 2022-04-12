package com.alkemy.peliculas.dto;

import com.alkemy.peliculas.entity.Genero;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PeliculaBasicDTO {
    private Long id;
    private String imagen;
    private String titulo;
    private String fechaCreacion;
    private double calificacion;
    private Genero genero;
}
