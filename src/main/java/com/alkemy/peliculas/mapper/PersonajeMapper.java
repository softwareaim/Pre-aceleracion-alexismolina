package com.alkemy.peliculas.mapper;

import com.alkemy.peliculas.dto.PeliculaBasicDTO;
import com.alkemy.peliculas.dto.PeliculaDTO;
import com.alkemy.peliculas.dto.PersonajeDTO;
import com.alkemy.peliculas.entity.Pelicula;
import com.alkemy.peliculas.entity.Personaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PersonajeMapper {

    @Autowired
    private PeliculaMapper peliculaMapper;

    public Personaje personajeDTO2Entity(PersonajeDTO dto) {
        Personaje entity = new Personaje();
        entity.setId(dto.getId());
        entity.setNombre(dto.getNombre());
        entity.setImagen(dto.getImagen());
        entity.setEdad(dto.getEdad());
        entity.setHistoria(dto.getHistoria());
        entity.setPeso(dto.getPeso());
        // peliculas
        Set<Pelicula> peliculas;
        peliculas = this.peliculaMapper.peliculaDTOList2Entity(dto.getPeliculas());
        entity.setPeliculas(peliculas);
        return entity;
    }

    public PersonajeDTO personajeEntity2DTO(Personaje entity, boolean loadPeliculas) {
        PersonajeDTO dto = new PersonajeDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setImagen(entity.getImagen());
        dto.setEdad(entity.getEdad());
        dto.setHistoria(entity.getHistoria());
        dto.setPeso(entity.getPeso());
        if (loadPeliculas) {
            List<PeliculaDTO> peliculaDTOS = this.peliculaMapper.peliculaEntitySet2DTOList(entity.getPeliculas(), false);
            dto.setPeliculas(peliculaDTOS);
        }
        return dto;
    }

    public List<PersonajeDTO> personajeEntityList2DTOList(List<Personaje> entities, boolean loadPeliculas) {
        List<PersonajeDTO> dtos = new ArrayList<>();
        for (Personaje entity : entities) {
            dtos.add(this.personajeEntity2DTO(entity, loadPeliculas));
        }
        return dtos;
    }

    public List<Personaje> personajeDTOList2EntityList(List<PersonajeDTO> dtos) {
        List<Personaje> entities = new ArrayList<>();
        Personaje p;
        for (PersonajeDTO dto : dtos) {
            p = new Personaje();
            p.setId(dto.getId());
            p.setNombre(dto.getNombre());
            p.setPeso(dto.getPeso());
            p.setHistoria(dto.getHistoria());
            p.setEdad(dto.getEdad());
            p.setImagen(dto.getImagen());
            entities.add(p);
        }
        return entities;
    }

}
