package com.yelensoft.artishop_backend.configuration;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
