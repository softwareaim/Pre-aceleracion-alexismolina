package com.alkemy.peliculas.controller;

import com.alkemy.peliculas.dto.PeliculaBasicDTO;
import com.alkemy.peliculas.dto.PeliculaDTO;
import com.alkemy.peliculas.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class PeliculaController {

    @Autowired
    private PeliculaService peliculaService;

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
    public ResponseEntity<PeliculaDTO> save(@Valid @RequestBody PeliculaDTO dto) throws Exception {
        PeliculaDTO result = this.peliculaService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping("/{idMovie}/characters/{idCharacter}")
    public ResponseEntity<PeliculaDTO> addCharacter(@PathVariable Long idMovie ,
                                                    @PathVariable Long idCharacter) {
        PeliculaDTO result = this.peliculaService.addCharacter(idMovie,idCharacter);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PeliculaDTO> update(@PathVariable Long id,@Valid @RequestBody PeliculaBasicDTO dto)throws Exception {
        PeliculaDTO result = this.peliculaService.update(id, dto);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PeliculaDTO> delete(@PathVariable Long id) {
        this.peliculaService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @DeleteMapping("/{idMovie}/characters/{idCharacter}")
    public ResponseEntity<PeliculaDTO> removeCharacter(@PathVariable Long idMovie ,
                                                       @PathVariable Long idCharacter) {
        PeliculaDTO result = this.peliculaService.removeCharacter(idMovie,idCharacter);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
