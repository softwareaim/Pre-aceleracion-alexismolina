package com.alkemy.peliculas.controller;

import com.alkemy.peliculas.dto.PeliculaBasicDTO;
import com.alkemy.peliculas.dto.PeliculaDTO;
import com.alkemy.peliculas.service.impl.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class PeliculaController {

    @Autowired
    private PeliculaService peliculaService;

    @GetMapping("/all")
    public ResponseEntity<List<PeliculaDTO>> getAll() {
        List<PeliculaDTO> dtos = this.peliculaService.getAll();
        return ResponseEntity.ok().body(dtos);
    }

    @GetMapping
    ResponseEntity<List<PeliculaBasicDTO>> getDetailsByFilters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long genre,
            @RequestParam(required = false, defaultValue = "ASC") String order
    ){
        List<PeliculaBasicDTO> basicDTOS = this.peliculaService.getByFilters(name, genre, order);
        return ResponseEntity.ok().body(basicDTOS);
    }

    @PostMapping
    public ResponseEntity<PeliculaDTO> save(@RequestBody PeliculaDTO dto) {
        PeliculaDTO result = this.peliculaService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PeliculaDTO> update(@PathVariable Long id, @RequestBody PeliculaBasicDTO dto) {
        PeliculaDTO result = this.peliculaService.update(id, dto);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PeliculaDTO> delete(@PathVariable Long id) {
        this.peliculaService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
