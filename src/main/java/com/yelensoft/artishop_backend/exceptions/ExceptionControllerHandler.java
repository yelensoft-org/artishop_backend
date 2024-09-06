package com.yelensoft.artishop_backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import java.util.Date;

@RestControllerAdvice
public class ExceptionControllerHandler {

    @ExceptionHandler(NotAuthorizedException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorMessage handleNotAuthorizedException (NotAuthorizedException ex, WebRequest request) {
        return new ErrorMessage(
                HttpStatus.UNAUTHORIZED.value(),
                new Date(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false)
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request){
        final String[] messageError = {""};
        ex.getBindingResult().getAllErrors().forEach( (error) -> {
            messageError[0] += "'"+((FieldError) error).getField()+"' : "+error.getDefaultMessage()+", ";
        });
        return new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                messageError[0],
                request.getDescription(false)
        );
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleBadRequestException(WebRequest request, BadRequestException ex) {
        return new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),          // Code de statut HTTP 404
                new Date(),                            // Timestamp actuel
                HttpStatus.BAD_REQUEST.getReasonPhrase(), // Message "Not Found"
                ex.getMessage(),                       // Message de l'exception
                request.getDescription(false)          // Chemin de la requête
        );
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleNotFoundException(NotFoundException ex, WebRequest request) {
        return new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),          // Code de statut HTTP 404
                new Date(),                            // Timestamp actuel
                HttpStatus.NOT_FOUND.getReasonPhrase(), // Message "Not Found"
                ex.getMessage(),                       // Message de l'exception
                request.getDescription(false)          // Chemin de la requête
        );
    }

    @ExceptionHandler(ResourceExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessage handleResourceExistException(ResourceExistException ex, WebRequest request) {
        return new ErrorMessage(
                HttpStatus.CONFLICT.value(),
                new Date(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false)
        );
    }

    @ExceptionHandler(InternalServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleInternalServerException(WebRequest request, InternalServerException ex) {
        return new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),          // Code de statut HTTP 404
                new Date(),                            // Timestamp actuel
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), // Message "Not Found"
                ex.getMessage(),                       // Message de l'exception
                request.getDescription(false)          // Chemin de la requête
        );
    }

}
