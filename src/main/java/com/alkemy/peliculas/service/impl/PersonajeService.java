package com.alkemy.peliculas.service.impl;

import com.alkemy.peliculas.dto.PersonajeDTO;

import java.util.List;

public interface PersonajeService {

    PersonajeDTO save(PersonajeDTO dto);

    List<PersonajeDTO> getAll();

    PersonajeDTO update(Long id ,PersonajeDTO dto);

    void delete(Long id);
}
