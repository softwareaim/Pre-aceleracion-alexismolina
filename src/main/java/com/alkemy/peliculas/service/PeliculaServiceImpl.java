package com.alkemy.peliculas.service;

import com.alkemy.peliculas.dto.PeliculaDTO;
import com.alkemy.peliculas.entity.Pelicula;
import com.alkemy.peliculas.entity.Personaje;
import com.alkemy.peliculas.mapper.PeliculaMapper;
import com.alkemy.peliculas.repository.PeliculaRepository;
import com.alkemy.peliculas.repository.PersonajeRepository;
import com.alkemy.peliculas.service.impl.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PeliculaServiceImpl implements PeliculaService {

    @Autowired
    private PeliculaMapper peliculaMapper;

    @Autowired
    private PeliculaRepository peliculaRepository;

    @Transactional
    @Override
    public PeliculaDTO save(PeliculaDTO dto) {
        Pelicula entity = this.peliculaMapper.peliculaDTO2Entity(dto);
        Pelicula entitySaved = this.peliculaRepository.save(entity);
        PeliculaDTO result = this.peliculaMapper.peliculaEntity2DTO(entitySaved, true);
        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public List<PeliculaDTO> getAll() {
        List<Pelicula> entities = this.peliculaRepository.findAll();
        List<PeliculaDTO> dtos = this.peliculaMapper.peliculaEntitySet2DTOList(entities, true);
        return dtos;
    }

    @Override
    public PeliculaDTO update(Long idPelicula, PeliculaDTO dto) {
        Optional<Pelicula> entity = this.peliculaRepository.findById(idPelicula);
        PeliculaDTO result = null;
        if (entity.isPresent()) {
            dto.setId(entity.get().getId());
            result = this.save(dto);
        }
        return result;
    }

    @Override
    public void delete(Long id) {
        Optional<Pelicula> entity = this.peliculaRepository.findById(id);
        if (entity.isPresent()) {
            this.peliculaRepository.delete(entity.get());
        }


    }
}
