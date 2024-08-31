package com.yelensoft.artishop_backend.dto;

import com.yelensoft.artishop_backend.entities.Category;
import com.yelensoft.artishop_backend.entities.Product;
import com.yelensoft.artishop_backend.entities.ProductView;
import com.yelensoft.artishop_backend.enumClass.MoneyUnit;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class ProductDto {
    //    Attribute to add
    private List<Category> categoryIds;

    //    commun Attribute get/ add
    private String name;
    private double price;
    private String description;
    private int quantity;
    private String quantityUnit;
    private List<String> sizes;
    private MoneyUnit priceUnit;

    //     Attribute to get product
    private int nbLike;
    private Long id;
    private List<String> colors;
    private List<ProductView> productViews;


//    ----------------------------------------------------------------
    public static ProductDto toGetDtoProduct(Product product) {
System.out.println("-------------------------------------------toGetDtoProduct");
        ModelMapper modelMapper = new ModelMapper();
        ProductDto getDto = modelMapper.map(product, ProductDto.class);

        if(product.getId() > 0){
            getDto.setId(product.getId());
        }else {
            getDto.setId(null);
        }

        if(!product.getCategoryIds().isEmpty()){
            getDto.setCategoryIds(product.getCategoryIds());
        }else {
            getDto.setCategoryIds(new ArrayList<>());
        }

        if(product.getProductViews() != null && !product.getProductViews().isEmpty()){
            for (ProductView productView : product.getProductViews()) {
                if(productView != null) {
                    getDto.getProductViews().add(productView);

                    List<String> colors = Arrays.asList(productView.getColors().split(","));
                    if(!productView.getColors().isEmpty()){

                        getDto.setColors(colors);
                    }else {
                        getDto.setColors(new ArrayList<>());
                    }


                    List<String> sizes = Arrays.asList(productView.getSizes().split(","));
                    if(!productView.getSizes().isEmpty()){

                        getDto.setSizes(sizes);
                    }else {
                        getDto.setSizes(new ArrayList<>());
                    }

                }

            }
        }else {
            System.out.println("Product is empty");
            getDto.setSizes(new ArrayList<>());
            getDto.setColors(new ArrayList<>());
            getDto.setProductViews(new ArrayList<>());
        }

        return getDto;
    }

}

