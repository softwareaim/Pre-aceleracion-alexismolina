package com.alkemy.peliculas.mapper;

import com.alkemy.peliculas.dto.PeliculaDTO;
import com.alkemy.peliculas.dto.PersonajeBasicDTO;
import com.alkemy.peliculas.dto.PersonajeDTO;
import com.alkemy.peliculas.mapper.entity.Pelicula;
import com.alkemy.peliculas.mapper.entity.Personaje;
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

    public List<PersonajeBasicDTO> personajeEntityList2BasicDTOList(List<Personaje> dtos) {
        List<PersonajeBasicDTO> basicDTOS = new ArrayList<>();
        PersonajeBasicDTO basicDTO;
        for (Personaje entity : dtos) {
            basicDTO = new PersonajeBasicDTO();
            basicDTO.setId(entity.getId());
            basicDTO.setNombre(entity.getNombre());
            basicDTO.setImagen(entity.getImagen());
            basicDTOS.add(basicDTO);
        }
        return basicDTOS;
    }
    public void personajeEntityRefreshValues(Personaje entity, PersonajeBasicDTO dto){
        entity.setImagen(dto.getImagen());
        entity.setNombre(dto.getNombre());
        entity.setId(dto.getId());
        entity.setEdad(dto.getEdad());
        entity.setHistoria(dto.getHistoria());
        entity.setPeso(dto.getPeso());

    }

    public PersonajeBasicDTO personajeEntit2PersonajeBasicDTO(Personaje entity){
        PersonajeBasicDTO result = new PersonajeBasicDTO();
        result.setImagen(entity.getImagen());
        result.setNombre(entity.getNombre());
        result.setId(entity.getId());
        result.setEdad(entity.getEdad());
        result.setHistoria(entity.getHistoria());
        result.setPeso(entity.getPeso());
        return result;
    }
}
