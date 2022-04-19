package com.alkemy.peliculas.auth.service;

import com.alkemy.peliculas.auth.dto.UserDTO;
import com.alkemy.peliculas.auth.entity.UserEntity;
import com.alkemy.peliculas.auth.repository.UserRepositiry;
import com.alkemy.peliculas.exception.ParamNotFound;
import com.alkemy.peliculas.service.impl.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class UserDetailsCustomService implements UserDetailsService {

    @Autowired
    private UserRepositiry userRepositiry;
    @Autowired
    private EmailService emailService;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserEntity userEntity = userRepositiry.findByUsername(userName);
        if (userEntity == null) {
            throw new UsernameNotFoundException("Username or password not fount");
        }
        return new User(userEntity.getUsername(), userEntity.getPassword(), Collections.emptyList());
    }

    @Transactional()
    public boolean save(UserDTO userDto) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encriptada = passwordEncoder.encode(userDto.getPassword());

        Boolean flag = passwordEncoder.matches(userDto.getPassword(),encriptada);
        if (!flag){
            throw new ParamNotFound("Error de encriptacion");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(encriptada);
        UserEntity userResult;
        userResult = this.userRepositiry.save(userEntity);
        if (userResult != null) {
            emailService.sendWelcomeEmailTo(userEntity.getUsername());
        }
        return userResult != null;

    }
}