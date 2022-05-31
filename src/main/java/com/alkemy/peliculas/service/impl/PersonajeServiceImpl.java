package com.alkemy.peliculas.service.impl;

import com.alkemy.peliculas.service.PersonajeService;
import com.alkemy.peliculas.dto.PersonajeBasicDTO;
import com.alkemy.peliculas.dto.PersonajeDTO;
import com.alkemy.peliculas.dto.filters.PersonajeFiltersDTO;
import com.alkemy.peliculas.entity.Personaje;
import com.alkemy.peliculas.error.exception.NotFoundException;
import com.alkemy.peliculas.mapper.PersonajeMapper;
import com.alkemy.peliculas.repository.PersonajeRepository;
import com.alkemy.peliculas.repository.specifications.PersonajeSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class PersonajeServiceImpl implements PersonajeService {

    @Autowired
    private PersonajeRepository personajeRepository;

    @Autowired
    private PersonajeSpecification personajeSpecification;

    @Autowired
    private PersonajeMapper personajeMapper;

    private final Logger log = LoggerFactory.getLogger(PersonajeServiceImpl.class);
    private final static String DIRECTORIO_UPLOAD = "uploads";

    @Value("${personaje.idErrorMsj}")
    private String idErrorMsj;

    @Transactional
    @Override
    public PersonajeDTO save(PersonajeDTO dto) {
        Personaje entity = this.personajeMapper.personajeDTO2Entity(dto);
        Personaje entitySaved = this.personajeRepository.save(entity);
        PersonajeDTO result = this.personajeMapper.personajeEntity2DTO(entitySaved, true);
        return result;
    }

    @Transactional
    @Override
    public PersonajeDTO update(Long idPersonaje, PersonajeBasicDTO basicDTO) {
        Optional<Personaje> entity = this.personajeRepository.findById(idPersonaje);
        if (!entity.isPresent()) {
            new NotFoundException(idErrorMsj + idPersonaje);
        }
        this.personajeMapper.personajeEntityRefreshValues(entity.get(), basicDTO);

        Personaje entitySaved = personajeRepository.save(entity.get());
        PersonajeDTO result = this.personajeMapper.personajeEntity2DTO(entitySaved, true);
        return result;
    }

    @Transactional
    @Override
    public void delete(Long idPersonaje) {
        Optional<Personaje> entity = this.personajeRepository.findById(idPersonaje);
        if (!entity.isPresent()) {
            new NotFoundException(idErrorMsj + idPersonaje);
        }
        eliminarFoto(entity.get()); // se elimina la foto asocia al personaje del directorio
        this.personajeRepository.delete(entity.get());
    }

    @Transactional(readOnly = true)
    @Override
    public List<PersonajeBasicDTO> getByFilters(String name, Integer age, Set<Long> movies, String order) {
        PersonajeFiltersDTO filtersDTO = new PersonajeFiltersDTO(name, age, movies, order);
        List<Personaje> entities = this.personajeRepository.findAll(this.personajeSpecification.getByFilters(filtersDTO));
        List<PersonajeBasicDTO> basicDTOS = this.personajeMapper.personajeEntityList2BasicDTOList(entities);
        return basicDTOS;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Personaje> getByFiltersPage(String name, Integer age, Set<Long> movies, String order, Integer page) {
        PersonajeFiltersDTO filtersDTO = new PersonajeFiltersDTO(name, age, movies, order);
        Page<Personaje> entities = this.personajeRepository.findAll(this.personajeSpecification.getByFilters(filtersDTO), PageRequest.of(page,3));
//        Page<PersonajeBasicDTO> pageBasic ;
        return entities;
    }

    @Transactional
    @Override
    public PersonajeBasicDTO upload(MultipartFile file, Long idPersonaje) throws IOException {
        Optional<Personaje> entity = this.personajeRepository.findById(idPersonaje);
        if (!entity.isPresent()) {
            new NotFoundException(idErrorMsj + idPersonaje);
        }
        PersonajeBasicDTO pBasicDto = null;

        if (!file.isEmpty()) {

            eliminarFoto(entity.get()); // se elimina la foto anterior
            String nombreArchivo = UUID.randomUUID().toString() + "_" + file.getOriginalFilename().replace(" ", "");
            Path rutaArchivo = getPath(nombreArchivo);
            Files.copy(file.getInputStream(), rutaArchivo);
            Personaje personaje = entity.get();
            personaje.setImagen(nombreArchivo);
            Personaje entitySaved = personajeRepository.save(personaje);
            pBasicDto = personajeMapper.personajeEntit2PersonajeBasicDTO(entitySaved);
        }
        return pBasicDto;
    }

    @Transactional(readOnly = true)
    @Override
    public Resource load(String nombreFoto) throws MalformedURLException {
        Path rutaArchivo = getPath(nombreFoto);
        log.info(rutaArchivo.toString());
        Resource recurso = new UrlResource(rutaArchivo.toUri());
        if (!recurso.exists() && !recurso.isReadable()) {
            log.error("error no se pudo cargar la imagen: " + nombreFoto);
            throw new NotFoundException("No se pudo cargar la imagen " + nombreFoto);

        }
        return recurso;
    }

    public Path getPath(String nombreFoto) {
        return Paths.get(DIRECTORIO_UPLOAD).resolve(nombreFoto).toAbsolutePath();
    }

    @Override
    public void eliminarFoto(Personaje personaje) {
        String nombreFrotoAnterior = personaje.getImagen();
        if (nombreFrotoAnterior != null && nombreFrotoAnterior.length() > 0) { // se busca la img q tenia y se elimina
            Path rutaFotoAnterior = getPath(nombreFrotoAnterior);
            File archivoFotoAnterior = rutaFotoAnterior.toFile();
            if (archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {
                archivoFotoAnterior.delete();
            }
        }

    }
}
