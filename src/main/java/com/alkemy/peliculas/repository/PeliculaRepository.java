package com.alkemy.peliculas.repository;

import com.alkemy.peliculas.entity.Pelicula;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, Long>, JpaSpecificationExecutor<Pelicula> {

    List<Pelicula> findAll(Specification<Pelicula> spec);
}
