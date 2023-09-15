package com.example.AutoSpotter.config;

import org.springframework.security.core.AuthenticationException;

public class EmailNotVerifiedException extends AuthenticationException {

    public EmailNotVerifiedException(String message) {
        super(message);
    }
}