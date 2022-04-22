package com.alkemy.peliculas.service;

import com.alkemy.peliculas.dto.PeliculaDTO;
import com.alkemy.peliculas.dto.PersonajeBasicDTO;
import com.alkemy.peliculas.dto.PersonajeDTO;

import java.util.List;
import java.util.Set;

public interface PersonajeService {

    PersonajeDTO save(PersonajeDTO dto);

    PersonajeDTO findById(Long idPersonaje);

    List<PersonajeDTO> getAll();

    PersonajeDTO update(Long id ,PersonajeBasicDTO dto);

    void delete(Long id);

    List<PersonajeBasicDTO> getByFilters(String name, Integer age, Set<Long> movies, String order);
}
