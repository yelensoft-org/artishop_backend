package com.yelensoft.artishop_backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceExistException extends RuntimeException{
    public ResourceExistException(String message) {
        super(message);
    }
}
