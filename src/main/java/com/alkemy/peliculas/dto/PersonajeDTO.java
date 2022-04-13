package com.alkemy.peliculas.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
