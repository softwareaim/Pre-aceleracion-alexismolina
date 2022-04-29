package com.alkemy.peliculas.controller;

import com.alkemy.peliculas.dto.PersonajeBasicDTO;
import com.alkemy.peliculas.dto.PersonajeDTO;
import com.alkemy.peliculas.mapper.entity.Personaje;
import com.alkemy.peliculas.service.PersonajeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/characters")
public class PersonajeController {

    @Autowired
    private PersonajeService personajeService;

    @GetMapping
    ResponseEntity<List<PersonajeBasicDTO>> getDetailsByFilters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) Set<Long> movies,
            @RequestParam(required = false, defaultValue = "ASC") String order
    ) {
        List<PersonajeBasicDTO> basicDTOS = this.personajeService.getByFilters(name, age, movies, order);
        return ResponseEntity.ok().body(basicDTOS);
    }

    @GetMapping("page/{page}")
    ResponseEntity<Page<Personaje>> getDetailsByFilters(
            @PathVariable("page") Integer page,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) Set<Long> movies,
            @RequestParam(required = false, defaultValue = "ASC") String order
    ) {
        Page<Personaje> entityPage = this.personajeService.getByFiltersPage(name, age, movies, order,page);
        return ResponseEntity.ok().body(entityPage);
    }


    @PostMapping
    ResponseEntity<PersonajeDTO> save(@Valid @RequestBody PersonajeDTO dto) {

        PersonajeDTO personajeDTO = this.personajeService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(personajeDTO);
    }

    @PutMapping("/{id}")
    ResponseEntity<PersonajeDTO> update(@Valid @RequestBody PersonajeBasicDTO basicDTO, @PathVariable Long id) {

        PersonajeDTO personajeDTO = this.personajeService.update(id, basicDTO);
        return ResponseEntity.ok().body(personajeDTO);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<PersonajeDTO> delete(@PathVariable Long id) {
        this.personajeService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/upload/{id}")
    public ResponseEntity<PersonajeBasicDTO> upload(@RequestParam(name="archivo",required = false) MultipartFile file,
                                                    @PathVariable Long id) throws IOException {
        PersonajeBasicDTO personajeBasicDTO = this.personajeService.upload(file, id);
        return ResponseEntity.ok().body(personajeBasicDTO);
    }

    @GetMapping("/uploads/img/{nombreFoto:.+}")
    public ResponseEntity<Resource> load(@PathVariable String nombreFoto) throws MalformedURLException {

        Resource recurso = personajeService.load(nombreFoto);
        HttpHeaders cabecera = new HttpHeaders();
        cabecera.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + recurso.getFilename() + "\"");
        return ResponseEntity.ok().headers(cabecera).body(recurso);

    }


}
