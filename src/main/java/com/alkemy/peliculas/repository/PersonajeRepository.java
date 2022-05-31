package com.alkemy.peliculas.repository;

import com.alkemy.peliculas.entity.Personaje;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PersonajeRepository extends JpaRepository<Personaje,Long>, JpaSpecificationExecutor<Personaje> {

    List<Personaje> findAll(Specification<Personaje> spec);

    Page<Personaje> findAll(Specification<Personaje> spec, Pageable pageable);
}
