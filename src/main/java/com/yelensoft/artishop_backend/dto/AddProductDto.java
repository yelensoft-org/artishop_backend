package com.yelensoft.artishop_backend.dto;

import com.yelensoft.artishop_backend.model.Product;
import lombok.Data;
import java.util.List;

@Data
public class AddProductDto {
    private Product product;
    private List<Long> idsCategories;
}
