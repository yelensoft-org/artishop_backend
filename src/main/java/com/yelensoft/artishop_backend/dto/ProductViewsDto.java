package com.yelensoft.artishop_backend.dto;

import com.yelensoft.artishop_backend.entities.ProductView;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class ProductViewsDto {
    private long productId;
    private List<String> imageUrls;
    private long id;
    private int nbAvailable;
    private List<String> colors;
    private List<String> sizes;

    public static ProductViewsDto toDto(ProductView productView) {
//        ProductViewsDto Dto = new ProductViewsDto();
//        Dto.setProductId(productView.getProductId().getId());
//        Dto.setId(productView.getId());
//        Dto.setNbAvailable(productView.getNbAvailable());
//
//        return Dto;
        ModelMapper modelMapper = new ModelMapper();
        ProductViewsDto getDto = modelMapper.map(productView, ProductViewsDto.class);

            getDto.setId(productView.getId());

            List<String> colors = Arrays.asList(productView.getColors().split(","));
            if(!productView.getColors().isEmpty()){

                getDto.setColors(colors);
            }else {
                getDto.setColors(new ArrayList<>());
            }

            List<String> imgUrls = Arrays.asList(productView.getImageUrls().split(","));
            if(!productView.getImageUrls().isEmpty()){
                getDto.setImageUrls(imgUrls);
            }else {
                getDto.setImageUrls(new ArrayList<>());
            }

            List<String> sizes = Arrays.asList(productView.getSizes().split(","));
            if(!productView.getSizes().isEmpty()){

                getDto.setSizes(sizes);
            }else {
                getDto.setSizes(new ArrayList<>());
            }
        return getDto;
    }
}
