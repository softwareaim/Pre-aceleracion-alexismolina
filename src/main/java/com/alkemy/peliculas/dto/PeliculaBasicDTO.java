package com.alkemy.peliculas.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PeliculaBasicDTO {
    private Long id;
    private String imagen;
    @NotEmpty(message = "Ingrese un titulo")
    private String titulo;
    @NotNull(message = "Ingrese una fecha de creacion")
    private String fechaCreacion;
}
