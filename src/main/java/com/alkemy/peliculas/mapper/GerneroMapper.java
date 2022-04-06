package com.alkemy.peliculas.mapper;

import com.alkemy.peliculas.dto.GeneroDTO;
import com.alkemy.peliculas.entity.Genero;

import java.util.ArrayList;
import java.util.List;

public class GerneroMapper {

    public Genero generoDTO2Entity(GeneroDTO dto){
        Genero entity = new Genero();
        entity.setId(dto.getId());
        entity.setImagen(dto.getImagen());
        return entity;
    }

    public GeneroDTO generoEntity2DTO(Genero entity){
        GeneroDTO dto = new GeneroDTO();
        dto.setId(entity.getId());
        dto.setImagen(entity.getImagen());
        return dto;
    }

    public List<GeneroDTO> generoEntityList2DTOList(List<Genero> entities){
        List<GeneroDTO> dtos = new ArrayList<>();
        for(Genero entity : entities){
            dtos.add(this.generoEntity2DTO(entity));
        }
        return dtos;
    }

}
