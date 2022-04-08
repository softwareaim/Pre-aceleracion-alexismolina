package com.alkemy.peliculas.repository;

import com.alkemy.peliculas.entity.Personaje;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PersonajeRepository extends JpaRepository<Personaje,Long> {

}
