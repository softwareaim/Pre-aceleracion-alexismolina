package com.alkemy.peliculas.repository;

import com.alkemy.peliculas.entity.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {
}
