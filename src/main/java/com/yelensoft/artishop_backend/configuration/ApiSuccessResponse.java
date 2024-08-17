package com.yelensoft.artishop_backend.configuration;

import lombok.Data;

@Data
public class ApiSuccessResponse {
    private String message;

    public static ApiSuccessResponse successResponse(String message) {
        ApiSuccessResponse successResponse = new ApiSuccessResponse();
        successResponse.setMessage(message);
        return successResponse;
    }
}
