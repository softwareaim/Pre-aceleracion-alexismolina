package com.alkemy.peliculas.dto;

import com.alkemy.peliculas.entity.Genero;
import lombok.Getter;
import lombok.Setter;

//TODO: chekear si se aplica para usar en la respuesta del endpoint con filtrado

@Getter
@Setter
public class PeliculaBasicDTO {
    private Long id;
    private String imagen;
    private String titulo;
    private String fechaCreacion;
}
