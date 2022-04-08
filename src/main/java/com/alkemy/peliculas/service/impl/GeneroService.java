package com.alkemy.peliculas.service.impl;

import com.alkemy.peliculas.dto.GeneroDTO;

import java.util.List;

public interface GeneroService {

     GeneroDTO save(GeneroDTO dto);

     List<GeneroDTO> getAllGeneros();

     GeneroDTO update(Long id, GeneroDTO dto);

     void delete(Long id);

}
