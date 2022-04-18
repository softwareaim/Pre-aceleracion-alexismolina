package com.alkemy.peliculas.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class PeliculaDTO {
    private Long id;
    private String imagen;
    @NotEmpty(message = "Ingrese un titulo")
    private String titulo;
    @NotNull(message = "Ingrese una fecha de creacion")
    private String fechaCreacion;
    private double calificacion;
    private GeneroDTO generoDTO;
    private List<PersonajeDTO> personajes ; //porque lista DTO y no entidad ?
}
