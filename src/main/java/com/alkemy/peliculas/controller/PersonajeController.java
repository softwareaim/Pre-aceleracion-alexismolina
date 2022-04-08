package com.alkemy.peliculas.controller;

import com.alkemy.peliculas.dto.PersonajeDTO;
import com.alkemy.peliculas.service.impl.PersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/characters")
public class PersonajeController {

    @Autowired
    private PersonajeService personajeService;

    @GetMapping
    ResponseEntity<List<PersonajeDTO>> getAll(){
        List<PersonajeDTO> dtos = this.personajeService.getAll();
        return ResponseEntity.ok().body(dtos);
    }

    @PostMapping
    ResponseEntity<PersonajeDTO> save(@RequestBody PersonajeDTO dto){
        PersonajeDTO result = this.personajeService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{id}")
    ResponseEntity<PersonajeDTO> update(@PathVariable Long id, @RequestBody PersonajeDTO dto){
        PersonajeDTO result = this.personajeService.update(id, dto);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<PersonajeDTO> delete(@PathVariable Long id){
        this.personajeService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
