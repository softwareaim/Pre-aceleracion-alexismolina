package com.alkemy.peliculas.service;

import com.alkemy.peliculas.dto.PersonajeDTO;
import com.alkemy.peliculas.entity.Personaje;
import com.alkemy.peliculas.mapper.PersonajeMapper;
import com.alkemy.peliculas.repository.PersonajeRepository;
import com.alkemy.peliculas.service.impl.PersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonajeServiceImpl implements PersonajeService {

    @Autowired
    private PersonajeRepository personajeRepository;

    @Autowired
    private PersonajeMapper personajeMapper;

    @Override
    public PersonajeDTO save(PersonajeDTO dto) {
        Personaje entity = this.personajeMapper.personajeDTO2Entity(dto);
        Personaje entitySaved = this.personajeRepository.save(entity);
        PersonajeDTO result = this.personajeMapper.personajeEntity2DTO(entitySaved, true);
        return result;
    }

    @Override
    public List<PersonajeDTO> getAll() {
        List<Personaje> entities = this.personajeRepository.findAll();
        List<PersonajeDTO> dtos = this.personajeMapper.personajeEntityList2DTOList(entities, true);
        return dtos;
    }

    @Override
    public PersonajeDTO update(Long id, PersonajeDTO dto) {
        PersonajeDTO result = null;
        Optional<Personaje> entity = this.personajeRepository.findById(id);
        if (entity.isPresent()) {
            dto.setId(entity.get().getId());
            result = this.save(dto);
        }
        return result;
    }

    @Override
    public void delete(Long id) {
        Optional<Personaje> entity = this.personajeRepository.findById(id);
        if(entity.isPresent()){
            this.personajeRepository.delete(entity.get());
        }

    }
}
