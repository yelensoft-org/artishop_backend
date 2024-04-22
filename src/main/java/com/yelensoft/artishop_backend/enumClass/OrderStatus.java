package com.yelensoft.artishop_backend.enumClass;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;


public enum OrderStatus {
    IN_PROGRESS,
    CANCELED,
    END
}
