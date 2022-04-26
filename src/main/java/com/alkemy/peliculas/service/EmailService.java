package com.alkemy.peliculas.service;

public interface EmailService {

    void sendEmail(String subject, String to, String body);
}
