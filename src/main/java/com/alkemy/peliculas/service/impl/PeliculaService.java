package com.alkemy.peliculas.service.impl;

import com.alkemy.peliculas.dto.PeliculaDTO;

import java.util.List;

public interface PeliculaService {

    PeliculaDTO save(PeliculaDTO dto);

    List<PeliculaDTO> getAll();

    PeliculaDTO update(Long idPelicula, PeliculaDTO dto);

    void delete(Long id);
}
