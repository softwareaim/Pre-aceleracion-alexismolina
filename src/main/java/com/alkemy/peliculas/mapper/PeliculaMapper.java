package com.alkemy.peliculas.mapper;

import com.alkemy.peliculas.dto.PeliculaDTO;
import com.alkemy.peliculas.dto.PersonajeDTO;
import com.alkemy.peliculas.entity.Pelicula;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PeliculaMapper {

    @Autowired
    private PersonajeMapper personajeMapper;

    public Pelicula peliculaDTO2Entity(PeliculaDTO dto) {
        Pelicula entity = new Pelicula();
        entity.setId(dto.getId());
        entity.setCalificacion(dto.getCalificacion());
        entity.setImagen(dto.getImagen());
        entity.setTitulo(dto.getTitulo());
        entity.setFechaCreacion(this.string2LocalDate(dto.getFechaCreacion()));
        return entity;

    }

    //se carga el genero?
    public PeliculaDTO peliculaEntity2DTO(Pelicula entity, Boolean loadPersonajes){
        PeliculaDTO dto = new PeliculaDTO();
        dto.setId(dto.getId());
        dto.setCalificacion(entity.getCalificacion());
        dto.setFechaCreacion(entity.getFechaCreacion().toString());
        dto.setImagen(dto.getImagen());
        if(loadPersonajes){
            List<PersonajeDTO> personajesDTO = this.personajeMapper.personajeEntityList2DTOList(entity.getPersonajes(),false);
            dto.setPersonajes(personajesDTO);
        }
        return dto;
    }

    public LocalDate string2LocalDate(String stringDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(stringDate,formatter);
        return date;
    }
}
