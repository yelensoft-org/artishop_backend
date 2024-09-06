package com.yelensoft.artishop_backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
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
    public ResponseEntity<ErrorMessage> handleBadRequestException(WebRequest webRequest, BadRequestException ex) {
        ErrorMessage errorResponse = new ErrorMessage();
        errorResponse.setTimestamp(new Date());
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setPath( webRequest.getDescription(false));

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessage> handleNotFoundException(WebRequest webRequest, NotFoundException ex) {
        ErrorMessage errorResponse = new ErrorMessage();
        errorResponse.setTimestamp(new Date());
        errorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorResponse.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setPath( webRequest.getDescription(false));

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceExistException.class)
    //@ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorMessage> handleResourceExistException(WebRequest webRequest, ResourceExistException ex) {
        System.out.println("::: ResourceExistException :::");
        ErrorMessage errorResponse = new ErrorMessage();
        errorResponse.setTimestamp(new Date());
        errorResponse.setStatusCode(HttpStatus.CONFLICT.value());
        errorResponse.setError(HttpStatus.CONFLICT.getReasonPhrase());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setPath( webRequest.getDescription(false));

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        //return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InternalServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorMessage> handleInternalServerException(WebRequest webRequest, InternalServerException ex) {
        ErrorMessage errorResponse = new ErrorMessage();
        errorResponse.setTimestamp(new Date());
        errorResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setPath( webRequest.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
