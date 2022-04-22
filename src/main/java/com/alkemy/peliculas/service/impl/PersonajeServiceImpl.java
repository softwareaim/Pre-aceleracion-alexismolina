package com.alkemy.peliculas.service.impl;

import com.alkemy.peliculas.dto.PersonajeBasicDTO;
import com.alkemy.peliculas.dto.PersonajeDTO;
import com.alkemy.peliculas.dto.filters.PersonajeFiltersDTO;
import com.alkemy.peliculas.entity.Personaje;
import com.alkemy.peliculas.error.exception.NotFoundException;
import com.alkemy.peliculas.mapper.PersonajeMapper;
import com.alkemy.peliculas.repository.PersonajeRepository;
import com.alkemy.peliculas.repository.specifications.PersonajeSpecification;
import com.alkemy.peliculas.service.PersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
public class PersonajeServiceImpl implements PersonajeService {

    @Autowired
    private PersonajeRepository personajeRepository;

    @Autowired
    private PersonajeSpecification personajeSpecification;

    @Autowired
    private PersonajeMapper personajeMapper;

    @Transactional
    @Override
    public PersonajeDTO save(PersonajeDTO dto) {
        Personaje entity = this.personajeMapper.personajeDTO2Entity(dto);
        Personaje entitySaved = this.personajeRepository.save(entity);
        PersonajeDTO result = this.personajeMapper.personajeEntity2DTO(entitySaved, true);
        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public PersonajeDTO findById(Long idPersonaje) {
        Optional<Personaje> entity = this.personajeRepository.findById(idPersonaje);
        if (!entity.isPresent()) {
            new NotFoundException("id personaje no valido");
        }
        PersonajeDTO dto = this.personajeMapper.personajeEntity2DTO(entity.get(), true);
        return dto;
    }

    @Transactional(readOnly = true)
    @Override
    public List<PersonajeDTO> getAll() {
        List<Personaje> entities = this.personajeRepository.findAll();
        List<PersonajeDTO> dtos = this.personajeMapper.personajeEntityList2DTOList(entities, true);
        return dtos;
    }

    @Transactional
    @Override
    public PersonajeDTO update(Long idPersonaje, PersonajeBasicDTO basicDTO) {
        Optional<Personaje> entity = this.personajeRepository.findById(idPersonaje);
        if (!entity.isPresent()) {
            new NotFoundException("id personaje no valido");
        }
        this.personajeMapper.personajeEntityRefreshValues(entity.get(), basicDTO);

        Personaje entitySaved = personajeRepository.save(entity.get());
        PersonajeDTO result = this.personajeMapper.personajeEntity2DTO(entitySaved,true);
        return result;
    }

    @Transactional
    @Override
    public void delete(Long idPersonaje) {
        Optional<Personaje> entity = this.personajeRepository.findById(idPersonaje);
        if (!entity.isPresent()) {
            new NotFoundException("id personaje no valido");
        }
        this.personajeRepository.delete(entity.get());
    }

    @Transactional(readOnly = true)
    @Override
    public List<PersonajeBasicDTO> getByFilters(String name, Integer age, Set<Long> movies, String order) {
        PersonajeFiltersDTO filtersDTO = new PersonajeFiltersDTO(name, age, movies, order);
        List<Personaje> entities = this.personajeRepository.findAll(this.personajeSpecification.getByFilters(filtersDTO));
        List<PersonajeBasicDTO> basicDTOS = this.personajeMapper.personajeEntityList2BasicDTOList(entities);
        return basicDTOS;
    }
}
