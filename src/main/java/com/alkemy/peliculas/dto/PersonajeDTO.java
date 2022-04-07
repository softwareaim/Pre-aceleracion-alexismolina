package com.alkemy.peliculas.dto;

import com.alkemy.peliculas.entity.Pelicula;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class PersonajeDTO {
    private Long id;
    private String nombre;
    private String imagen;
    private Integer edad;
    private Double peso;
    private String historia;
    private List<PeliculaDTO> peliculas ;
}
