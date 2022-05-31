package com.alkemy.peliculas.auth.service;

import com.alkemy.peliculas.auth.config.SecurityConfiguration;
import com.alkemy.peliculas.auth.dto.UserDTO;
import com.alkemy.peliculas.auth.entity.RoleEntity;
import com.alkemy.peliculas.auth.entity.UserEntity;
import com.alkemy.peliculas.auth.repository.RoleRepository;
import com.alkemy.peliculas.auth.repository.UserRepositiry;
import com.alkemy.peliculas.error.exception.ForbiddenException;
import com.alkemy.peliculas.error.exception.NotFoundException;
import com.alkemy.peliculas.error.exception.UnauthorizedException;
import com.alkemy.peliculas.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserDetailsCustomService implements UserDetailsService {

    @Autowired
    private UserRepositiry userRepositiry;
    @Autowired
    private EmailService emailService;
    @Autowired
    private SecurityConfiguration securityConfiguration;
    @Autowired
    private RoleRepository roleRepository;

    @Value("${usuario.usernameErrorMsj}")
    private String usernameErrorMsj;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserEntity user = userRepositiry.findByUsername(userName);
        if (user == null) {
            throw new UsernameNotFoundException(usernameErrorMsj);
        }

        List<GrantedAuthority> roles = new ArrayList<>();

        for (RoleEntity rol: user.getRoles()) {
            roles.add(new SimpleGrantedAuthority(rol.getNombre()));
        }

        if(!user.isEnabled()){
            throw new UsernameNotFoundException(" User is disabled");
        }

        return new User(user.getUsername(), user.getPassword(),user.isEnabled(),user.isAccountNonExpired(),user.isCredentialsNonExpired(),user.isAccountNonLocked(), roles);
    }

    @Transactional()
    public boolean save(UserDTO userDto) throws Exception {
        String encriptada = securityConfiguration.passwordEncoder().encode(userDto.getPassword());
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(encriptada);
        RoleEntity rol = roleRepository.findByNombre("ROLE_USER").orElse(null);
        if(rol != null){
            userEntity.setRoles(Arrays.asList(rol));
        }else{
            throw new NotFoundException("No se encontro el rol");
        }
        UserEntity result = null;
        result = this.userRepositiry.save(userEntity);
        emailService.sendEmail("Bienvenida a Alkemy",userEntity.getUsername(),"Hola, Bienvenid@ a Alkemy");
        return result != null;

    }
}