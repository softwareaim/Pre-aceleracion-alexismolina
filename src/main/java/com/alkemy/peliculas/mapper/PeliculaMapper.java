package com.alkemy.peliculas.mapper;

import com.alkemy.peliculas.dto.PeliculaBasicDTO;
import com.alkemy.peliculas.dto.PeliculaDTO;
import com.alkemy.peliculas.dto.PersonajeDTO;
import com.alkemy.peliculas.entity.Pelicula;
import com.alkemy.peliculas.entity.Personaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
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
        entity.setGenero(dto.getGenero());
        entity.setPersonajes(this.personajeMapper.personajeDTOList2EntityList(dto.getPersonajes()));
//        for (PersonajeDTO pdto : dto.getPersonajes()) {
//           entity.addPersonaje(this.personajeMapper.personajeDTO2Entity(pdto));
//            System.out.println("HOLAAAAA");
//        }
        return entity;

    }

    //se carga el genero?
    public PeliculaDTO peliculaEntity2DTO(Pelicula entity, Boolean loadPersonajes){
        PeliculaDTO dto = new PeliculaDTO();
        dto.setId(entity.getId());
        dto.setCalificacion(entity.getCalificacion());
        dto.setFechaCreacion(entity.getFechaCreacion().toString());
        dto.setImagen(entity.getImagen());
        dto.setTitulo(entity.getTitulo());
        dto.setGenero(entity.getGenero());/**/
        if(loadPersonajes){
            List<PersonajeDTO> personajesDTO = this.personajeMapper.personajeEntityList2DTOList(entity.getPersonajes(),false);
            dto.setPersonajes(personajesDTO);
        }
        return dto;
    }

    public Set<Pelicula> peliculaDTOList2Entity(List<PeliculaDTO> dtos) {
        Set<Pelicula> entities = new HashSet<>();
        for (PeliculaDTO dto : dtos) {
            entities.add(this.peliculaDTO2Entity(dto));
        }
        return entities;
    }

    public List<PeliculaDTO> peliculaEntitySet2DTOList(Collection<Pelicula> entities, boolean loadPersonajes) {
        List<PeliculaDTO> dtos = new ArrayList<>();
        for (Pelicula entity : entities) {
            dtos.add(this.peliculaEntity2DTO(entity, loadPersonajes));
        }
        return dtos;
    }

    public List<PeliculaBasicDTO> peliculaEntitySet2BasicDTOList(Collection<Pelicula> entities) {
        List<PeliculaBasicDTO> dtos = new ArrayList<>();
        PeliculaBasicDTO basicDTO;
        for (Pelicula entity : entities) {
            basicDTO = new PeliculaBasicDTO();
            basicDTO.setId(entity.getId());
            basicDTO.setImagen(entity.getImagen());
            basicDTO.setFechaCreacion(entity.getFechaCreacion().toString());
            basicDTO.setTiulo(entity.getTitulo());
            dtos.add(basicDTO);
        }
        return dtos;
    }

    public LocalDate string2LocalDate(String stringDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(stringDate,formatter);
        return date;
    }
    public void peliculaEntityRefreshValues(Pelicula entity, PeliculaDTO dto){
        entity.setImagen(dto.getImagen());
        entity.setCalificacion(dto.getCalificacion());
        entity.setTitulo(dto.getTitulo());
        entity.setFechaCreacion(
                this.string2LocalDate(dto.getFechaCreacion())
        );

    }

}
