package com.alkemy.peliculas.service;

import com.alkemy.peliculas.dto.GeneroDTO;
import com.alkemy.peliculas.entity.Genero;
import com.alkemy.peliculas.mapper.GerneroMapper;
import com.alkemy.peliculas.repository.GeneroRepository;
import com.alkemy.peliculas.service.impl.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GerneroServiceImpl implements GeneroService {

    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private GerneroMapper gerneroMapper;

    @Transactional
    @Override
    public GeneroDTO save(GeneroDTO dto) {
        Genero entity = this.gerneroMapper.generoDTO2Entity(dto);
        Genero entitySaved = this.generoRepository.save(entity);
        GeneroDTO result = this.gerneroMapper.generoEntity2DTO(entitySaved);
        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public List<GeneroDTO> getAllGeneros() {
        List<Genero> entities = this.generoRepository.findAll();
        List<GeneroDTO> dtos = this.gerneroMapper.generoEntityList2DTOList(entities);
        return dtos;
    }

    @Transactional
    @Override
    public GeneroDTO update(Long id, GeneroDTO dto) {
        Optional<Genero> entity = this.generoRepository.findById(id);
        GeneroDTO result = null;
        if(entity.isPresent()){
            dto.setId(entity.get().getId());
            result = this.save(dto);
        }
        return result;
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Optional<Genero> entity = this.generoRepository.findById(id);
        if(entity.isPresent()){
            this.generoRepository.delete(entity.get());
        }

    }
}
