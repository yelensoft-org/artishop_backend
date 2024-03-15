package com.yelensoft.artishop_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class ExceptionControllerHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request){
        final String[] messageError = {""};
        ex.getBindingResult().getAllErrors().forEach( (error) -> {
            messageError[0] += "'"+((FieldError) error).getField()+"' : "+error.getDefaultMessage()+", ";
        });
        return new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                messageError[0],
                request.getDescription(false)
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage globalException(Exception ex, WebRequest request){
        return new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );
    }

}
