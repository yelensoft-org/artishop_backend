package com.yelensoft.artishop_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StorePresentationDto {
    private Long id;
    private String name;
    private String image;
    private String ownerName;
    private boolean isFollowed;
    private int nbFollower;
}
