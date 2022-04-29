package com.alkemy.peliculas.repository;

import com.alkemy.peliculas.mapper.entity.Genero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneroRepository extends JpaRepository<Genero, Long> {
}
