package com.alkemy.peliculas.controller;

import com.alkemy.peliculas.dto.PersonajeBasicDTO;
import com.alkemy.peliculas.dto.PersonajeDTO;
import com.alkemy.peliculas.controller.service.PersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
            ){
        List<PersonajeBasicDTO> basicDTOS = this.personajeService.getByFilters(name, age, movies, order);
        return ResponseEntity.ok().body(basicDTOS);
    }

    @PostMapping
    ResponseEntity<PersonajeDTO> save(@Valid @RequestBody PersonajeDTO dto){

        PersonajeDTO personajeDTO = this.personajeService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(personajeDTO);
    }

    @PutMapping("/{id}")
    ResponseEntity<PersonajeDTO> update(@Valid  @RequestBody PersonajeBasicDTO basicDTO, @PathVariable Long id){

        PersonajeDTO personajeDTO = this.personajeService.update(id, basicDTO);
        return ResponseEntity.ok().body(personajeDTO);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<PersonajeDTO> delete(@PathVariable Long id){
        this.personajeService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
