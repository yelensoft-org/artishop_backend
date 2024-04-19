package com.yelensoft.artishop_backend.exceptions;

import lombok.Data;

@Data
public class ErrorMessage {
    private int statusCode;
    private String timestamp;
    private String error;
    private String message;
    private String path;
}