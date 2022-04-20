package com.alkemy.peliculas.service.impl;

import com.alkemy.peliculas.dto.PeliculaBasicDTO;
import com.alkemy.peliculas.dto.PeliculaDTO;
import com.alkemy.peliculas.dto.PersonajeDTO;
import com.alkemy.peliculas.dto.filters.PeliculaFiltersDTO;
import com.alkemy.peliculas.entity.Pelicula;
import com.alkemy.peliculas.entity.Personaje;
import com.alkemy.peliculas.exception.ParamNotFound;
import com.alkemy.peliculas.mapper.PeliculaMapper;
import com.alkemy.peliculas.repository.PeliculaRepository;
import com.alkemy.peliculas.repository.PersonajeRepository;
import com.alkemy.peliculas.repository.specifications.PeliculaSpecification;
import com.alkemy.peliculas.service.PeliculaService;
import com.alkemy.peliculas.service.PersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class PeliculaServiceImpl implements PeliculaService {

    @Autowired
    private PeliculaMapper peliculaMapper;

    @Autowired
    private PeliculaRepository peliculaRepository;

    @Autowired
    private PeliculaSpecification peliculaSpecification;

    @Autowired
    private PersonajeService personajeService;

    @Autowired
    private PersonajeRepository personajeRepository;

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
    public PeliculaDTO findById(Long idPelicula) {
        Optional<Pelicula> entity = this.peliculaRepository.findById(idPelicula);
        if (!entity.isPresent()) {
            throw new ParamNotFound("id pelicula no valido");
        }
        PeliculaDTO dto = this.peliculaMapper.peliculaEntity2DTO(entity.get(), true);
        return dto;
    }

    @Transactional(readOnly = true)
    @Override
    public List<PeliculaDTO> getAll() {
        List<Pelicula> entities = this.peliculaRepository.findAll();
        List<PeliculaDTO> dtos = this.peliculaMapper.peliculaEntitySet2DTOList(entities, true);
        return dtos;
    }

    @Transactional
    @Override
    public PeliculaDTO update(Long idPelicula, PeliculaBasicDTO basicDTO) {
        Optional<Pelicula> entity = this.peliculaRepository.findById(idPelicula);
        if (entity.isPresent()) {
            throw new ParamNotFound("Pelicula no encontrada");
        }
        this.peliculaMapper.peliculaEntityRefreshValues(entity.get(), basicDTO);
        Pelicula entitySaved = this.peliculaRepository.save(entity.get());
        PeliculaDTO result = this.peliculaMapper.peliculaEntity2DTO(entitySaved, true);
        return result;
    }

    @Transactional
    @Override
    public void delete(Long idPelicula) {
        Optional<Pelicula> entity = this.peliculaRepository.findById(idPelicula);
        if (entity.isPresent()) {
            throw new ParamNotFound("Pelicula no encontrada");
        }
        this.peliculaRepository.delete(entity.get());

    }

    @Transactional(readOnly = true)
    @Override
    public List<PeliculaBasicDTO> getByFilters(String name, Long genre, String order) {
        PeliculaFiltersDTO filtersDTO = new PeliculaFiltersDTO(name, genre, order);
        List<Pelicula> entities = this.peliculaRepository.findAll(this.peliculaSpecification.getByFilters(filtersDTO));
        List<PeliculaBasicDTO> basicDTOS = this.peliculaMapper.peliculaEntitySet2BasicDTOList(entities);
        return basicDTOS;
    }

    @Override
    public PeliculaDTO addCharacter(Long idPelicula, Long idPersonaje) {

        Personaje entityPersonaje = personajeRepository.findById(idPersonaje).orElse(null);
        Pelicula entityPelicula = peliculaRepository.findById(idPelicula).orElse(null);

        if (entityPersonaje == null || entityPelicula == null || entityPelicula.getPersonajes().contains(entityPersonaje) ) {
            throw new ParamNotFound("Error al agregar personaje");
        }
        entityPelicula.getPersonajes().add(entityPersonaje);

        Pelicula entitySaved = peliculaRepository.save(entityPelicula);
        PeliculaDTO result = peliculaMapper.peliculaEntity2DTO(entitySaved, true);

        return result;
    }

    @Override
    public PeliculaDTO removeCharacter(Long idPelicula, Long idPersonaje) {

        Personaje entityPersonaje = personajeRepository.findById(idPersonaje).orElse(null);
        Pelicula entityPelicula = peliculaRepository.findById(idPelicula).orElse(null);

        if (entityPersonaje == null || entityPelicula == null) {
            throw new ParamNotFound("Error al remover personaje");
        }

        Iterator<Personaje> it = entityPelicula.getPersonajes().iterator();
        while (it.hasNext()) {
            if (it.next().equals(entityPersonaje)) {
                it.remove();
            }
        }
        Pelicula entitySaved = peliculaRepository.save(entityPelicula);
        PeliculaDTO result = peliculaMapper.peliculaEntity2DTO(entitySaved, true);
        return result;

    }
}
