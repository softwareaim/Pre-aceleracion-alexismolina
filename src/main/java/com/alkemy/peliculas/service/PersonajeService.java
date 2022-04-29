package com.alkemy.peliculas.service;

import com.alkemy.peliculas.dto.PersonajeBasicDTO;
import com.alkemy.peliculas.dto.PersonajeDTO;
import com.alkemy.peliculas.mapper.entity.Personaje;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Set;

public interface PersonajeService {

    PersonajeDTO save(PersonajeDTO dto);

    PersonajeDTO update(Long id, PersonajeBasicDTO dto);

    void delete(Long id);

    List<PersonajeBasicDTO> getByFilters(String name, Integer age, Set<Long> movies, String order);

    Page<Personaje> getByFiltersPage(String name, Integer age, Set<Long> movies, String order, Integer page);

    PersonajeBasicDTO upload(MultipartFile file, Long idPersonaje) throws IOException;

    Resource load(String nombreFoto) throws MalformedURLException;

    void eliminarFoto(Personaje personaje);
}
