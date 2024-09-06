package com.yelensoft.artishop_backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerCreatedDto {
    private Long id;
    private String fullName;
    private String numTel;
}
