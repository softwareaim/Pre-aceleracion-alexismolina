package com.alkemy.peliculas.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorDTO {

    private HttpStatus status;
    private String message;
    private List<String> errors;
}
