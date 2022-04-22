package com.alkemy.peliculas.service.impl;

import com.alkemy.peliculas.dto.GeneroDTO;
import com.alkemy.peliculas.entity.Genero;
import com.alkemy.peliculas.mapper.GeneroMapper;
import com.alkemy.peliculas.repository.GeneroRepository;
import com.alkemy.peliculas.service.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;

public class GeneroServiceImpl implements GeneroService {

    @Autowired
    private GeneroMapper generoMapper;

    @Autowired
    private GeneroRepository generoRepository;

    @Override
    public GeneroDTO save(GeneroDTO dto) {

        Genero entity = this.generoMapper.generoDTO2Entity(dto);
        Genero entitySaved = this.generoRepository.save(entity);
        GeneroDTO result = this.generoMapper.generoEntity2DTO(entitySaved);
        return result;
    }

}
