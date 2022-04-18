package com.alkemy.peliculas.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class GeneroDTO {
    private Long id;
    @NotEmpty(message = "Ingrese un nombre del genero")
    private String nombre;
    private String imagen;
}
