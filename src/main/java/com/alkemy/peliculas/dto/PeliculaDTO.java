package com.alkemy.peliculas.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class PeliculaDTO {
    private Long id;
    private String imagen;
    @NotEmpty(message = "Ingrese un titulo")
    private String titulo;
    @NotNull(message = "Ingrese una fecha de creacion")
    private String fechaCreacion;
    @Range(min = 0, max = 5, message = "el rango de calificacion deber ser entre 0 y 5")
    private double calificacion;
    @NotNull(message ="Error al traer el genero")
    private GeneroDTO genero;
    private List<PersonajeDTO> personajes;
}
