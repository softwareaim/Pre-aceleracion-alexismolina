package com.alkemy.peliculas.auth.service;

import com.alkemy.peliculas.auth.config.SecurityConfiguration;
import com.alkemy.peliculas.auth.dto.UserDTO;
import com.alkemy.peliculas.auth.entity.UserEntity;
import com.alkemy.peliculas.auth.repository.UserRepositiry;
import com.alkemy.peliculas.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class UserDetailsCustomService implements UserDetailsService {

    @Autowired
    private UserRepositiry userRepositiry;
    @Autowired
    private EmailService emailService;
    @Autowired
    private SecurityConfiguration securityConfiguration;

    @Value("${usuario.usernameErrorMsj}")
    private String usernameErrorMsj;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserEntity userEntity = userRepositiry.findByUsername(userName);
        if (userEntity == null) {
            throw new UsernameNotFoundException(usernameErrorMsj);
        }
        return new User(userEntity.getUsername(), userEntity.getPassword(), Collections.emptyList());
    }

    @Transactional()
    public boolean save(UserDTO userDto) throws Exception {
        String encriptada = securityConfiguration.passwordEncoder().encode(userDto.getPassword());
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(encriptada);
        UserEntity userResult;
        userResult = this.userRepositiry.save(userEntity);
        emailService.sendEmail("Bienvenida a Alkemy",userEntity.getUsername(),"Hola, Bienvenid@ a Alkemy");
        return userResult != null;

    }
}