package com.alkemy.peliculas.error.exception;

import org.springframework.validation.BindingResult;

public class BadRequestException extends RuntimeException {
    private static final String DESCRIPTION = "Bad Request Exception";

    private final transient BindingResult result;

    public BadRequestException(String detail, BindingResult result) {
        super(DESCRIPTION + ". " + detail);
        this.result = result;
    }

    public BadRequestException(BindingResult result) {
        this.result = result;
    }

    public BindingResult getResult() {
        return result;
    }
}
