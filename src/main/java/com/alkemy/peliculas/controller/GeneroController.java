package com.alkemy.peliculas.controller;

import com.alkemy.peliculas.dto.GeneroDTO;
import com.alkemy.peliculas.entity.Genero;
import com.alkemy.peliculas.service.impl.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/generes")
public class GeneroController {

    @Autowired
    GeneroService generoService;

    @GetMapping
    ResponseEntity<List<GeneroDTO>> getAll(){
        List<GeneroDTO> resultGeneroDTOList = this.generoService.getAllGeneros();
        return ResponseEntity.ok().body(resultGeneroDTOList);
    }

    @PostMapping
    ResponseEntity<GeneroDTO> save(@RequestBody GeneroDTO dto){
        GeneroDTO result = this.generoService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{id}")
    ResponseEntity<GeneroDTO> update(@PathVariable Long id, @RequestBody GeneroDTO dto){
        GeneroDTO result = this.generoService.update(id, dto);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<GeneroDTO> delete(@PathVariable Long id){
        this.generoService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
