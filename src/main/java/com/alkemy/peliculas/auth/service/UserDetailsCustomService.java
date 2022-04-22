package com.alkemy.peliculas.auth.service;

import com.alkemy.peliculas.auth.dto.UserDTO;
import com.alkemy.peliculas.auth.entity.UserEntity;
import com.alkemy.peliculas.auth.repository.UserRepositiry;
import com.alkemy.peliculas.service.EmailService;
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
    public boolean save(UserDTO userDto) throws Exception {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encriptada = passwordEncoder.encode(userDto.getPassword());

        boolean flag = passwordEncoder.matches(userDto.getPassword(),encriptada);
        if (!flag){
            throw new Exception("Error de encryption");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(encriptada);
        UserEntity userResult;
        userResult = this.userRepositiry.save(userEntity);
        emailService.sendWelcomeEmailTo(userEntity.getUsername());
        return userResult != null;

    }
}