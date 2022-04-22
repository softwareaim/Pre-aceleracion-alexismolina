package com.alkemy.peliculas.builder;

import com.alkemy.peliculas.dto.GeneroDTO;
import com.alkemy.peliculas.entity.Genero;
import com.alkemy.peliculas.mapper.GeneroMapper;
import com.alkemy.peliculas.repository.GeneroRepository;
import com.alkemy.peliculas.controller.service.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GerneroServiceImpl implements GeneroService {

    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private GeneroMapper gerneroMapper;

    @Transactional
    @Override
    public GeneroDTO save(GeneroDTO dto) {
        Genero entity = this.gerneroMapper.generoDTO2Entity(dto);
        Genero entitySaved = this.generoRepository.save(entity);
        GeneroDTO result = this.gerneroMapper.generoEntity2DTO(entitySaved);
        return result;
    }
}
