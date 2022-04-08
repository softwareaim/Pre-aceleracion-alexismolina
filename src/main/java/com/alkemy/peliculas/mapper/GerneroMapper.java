package com.alkemy.peliculas.mapper;

import com.alkemy.peliculas.dto.GeneroDTO;
import com.alkemy.peliculas.entity.Genero;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GerneroMapper {

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

    public List<GeneroDTO> generoEntityList2DTOList(List<Genero> entities){
        List<GeneroDTO> dtos = new ArrayList<>();
        for(Genero entity : entities){
            dtos.add(this.generoEntity2DTO(entity));
        }
        return dtos;
    }

    public List<Genero> generoDTOList2Entity(List<GeneroDTO> dtos){
        List<Genero> entities = new ArrayList<>();
        for(GeneroDTO dto : dtos){
            entities.add(this.generoDTO2Entity(dto));
        }
        return entities;
    }

}
