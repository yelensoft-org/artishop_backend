package com.yelensoft.artishop_backend.exceptions;

import lombok.Data;

@Data
public class ErrorMessage {
    private String timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}