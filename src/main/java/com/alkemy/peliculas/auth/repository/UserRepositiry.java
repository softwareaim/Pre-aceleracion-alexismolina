package com.alkemy.peliculas.auth.repository;

import com.alkemy.peliculas.auth.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositiry extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);
}
