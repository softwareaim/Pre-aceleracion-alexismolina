package com.alkemy.peliculas.controller;

import com.alkemy.peliculas.dto.GeneroDTO;
import com.alkemy.peliculas.controller.service.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/generes")
public class GeneroController {

    @Autowired
    GeneroService generoService;

    @PostMapping
    ResponseEntity<GeneroDTO> save(@Valid @RequestBody GeneroDTO dto){

        GeneroDTO generoDTO = this.generoService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(generoDTO);
    }

}
