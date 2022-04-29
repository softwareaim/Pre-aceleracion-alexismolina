package com.alkemy.peliculas.mapper;

import com.alkemy.peliculas.dto.GeneroDTO;
import com.alkemy.peliculas.mapper.entity.Genero;
import org.springframework.stereotype.Component;

@Component
public class GeneroMapper {

    public Genero generoDTO2Entity(GeneroDTO dto){
        Genero entity = new Genero();
        entity.setId(dto.getId());
        entity.setImagen(dto.getImagen());
        entity.setNombre(dto.getNombre());
        return entity;
    }

    public GeneroDTO generoEntity2DTO(Genero entity){
        GeneroDTO dto = new GeneroDTO();
        dto.setId(entity.getId());
        dto.setImagen(entity.getImagen());
        dto.setNombre(entity.getNombre());
        return dto;
    }

}
