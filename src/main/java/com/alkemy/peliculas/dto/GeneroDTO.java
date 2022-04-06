package com.alkemy.peliculas.dto;

import com.alkemy.peliculas.entity.Pelicula;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GeneroDTO {
    private Long id;
    private String nombre;
    private String imagen;
    private List<Pelicula> peliculas = new ArrayList<>();
}
