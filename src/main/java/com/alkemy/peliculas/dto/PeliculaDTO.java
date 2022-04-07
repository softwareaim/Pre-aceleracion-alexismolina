package com.alkemy.peliculas.dto;

import com.alkemy.peliculas.entity.Genero;
import com.alkemy.peliculas.entity.Personaje;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
