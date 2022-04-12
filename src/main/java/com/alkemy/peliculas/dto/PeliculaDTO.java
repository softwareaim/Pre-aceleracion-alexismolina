package com.alkemy.peliculas.dto;

import com.alkemy.peliculas.entity.Genero;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class PeliculaDTO {
    private Long id;
    private String imagen;
    private String titulo;
    private String fechaCreacion;
    private double calificacion;
    private Genero genero;
    private List<PersonajeDTO> personajes ; //porque lista DTO y no entidad ?
}
