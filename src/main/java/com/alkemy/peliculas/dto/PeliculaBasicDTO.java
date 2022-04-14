package com.alkemy.peliculas.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PeliculaBasicDTO {
    private Long id;
    private String imagen;
    private String titulo;
    private String fechaCreacion;
}
