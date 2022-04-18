package com.alkemy.peliculas.service;

import com.alkemy.peliculas.dto.PeliculaBasicDTO;
import com.alkemy.peliculas.dto.PeliculaDTO;
import com.alkemy.peliculas.dto.PersonajeDTO;
import com.alkemy.peliculas.dto.filters.PeliculaFiltersDTO;
import com.alkemy.peliculas.entity.Pelicula;
import com.alkemy.peliculas.exception.ParamNotFound;
import com.alkemy.peliculas.mapper.PeliculaMapper;
import com.alkemy.peliculas.repository.PeliculaRepository;
import com.alkemy.peliculas.repository.specifications.PeliculaSpecification;
import com.alkemy.peliculas.service.impl.PeliculaService;
import com.alkemy.peliculas.service.impl.PersonajeService;
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

    @Transactional
    @Override
    public PeliculaDTO save(PeliculaDTO dto) {
        if (dto.getCalificacion() > 5 && dto.getCalificacion() < 0) {
            throw new ParamNotFound("Ingrese una calificacion valida 0 a 5");
        }
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
        Pelicula entity = this.peliculaMapper.peliculaDTO2Entity(this.findById(idPelicula));
        this.peliculaMapper.peliculaEntityRefreshValues(entity, basicDTO);
        PeliculaDTO result = this.save(this.peliculaMapper.peliculaEntity2DTO(entity, true));
        return result;
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Pelicula entity = this.peliculaMapper.peliculaDTO2Entity(this.findById(id));
        this.peliculaRepository.delete(entity);
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

        PeliculaDTO peliculaDto = this.findById(idPelicula);
        PersonajeDTO personajeDto = this.personajeService.findById(idPersonaje);
        if(peliculaDto.getPersonajes().contains(personajeDto)){
            throw new ParamNotFound("Ya contiene este personaje");
        }
        peliculaDto.getPersonajes().add(personajeDto);
        PeliculaDTO result = this.save(peliculaDto);
        return result;
    }

    @Override
    public PeliculaDTO removeCharacter(Long idPelicula, Long idPersonaje) {
        PeliculaDTO peliculaDto = this.findById(idPelicula);
        PersonajeDTO personajeDto = this.personajeService.findById(idPersonaje);
        List<PersonajeDTO> personajeDTOList = peliculaDto.getPersonajes();
        Iterator<PersonajeDTO> it = personajeDTOList.iterator();
        while (it.hasNext()){
            if(it.next().getId() == personajeDto.getId()){
                it.remove();
            }
        }
        PeliculaDTO result = this.save(peliculaDto);

        return result;
    }
}
