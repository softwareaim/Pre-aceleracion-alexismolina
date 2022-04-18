package com.alkemy.peliculas.service.impl;

import com.alkemy.peliculas.dto.PeliculaBasicDTO;
import com.alkemy.peliculas.dto.PeliculaDTO;

import java.util.List;

public interface PeliculaService {

    PeliculaDTO save(PeliculaDTO dto);

    PeliculaDTO findById(Long idPelicula);

    List<PeliculaDTO> getAll();

    PeliculaDTO update(Long idPelicula, PeliculaBasicDTO dto);

    void delete(Long id);

    List<PeliculaBasicDTO> getByFilters(String name, Long genre, String order);

    PeliculaDTO addCharacter(Long idPelicula, Long idPersonaje);

    PeliculaDTO removeCharacter(Long idPelicula, Long idPersonaje);


}
