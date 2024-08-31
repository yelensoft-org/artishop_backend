package com.yelensoft.artishop_backend.services;

import com.yelensoft.artishop_backend.dto.ProductDto;
import com.yelensoft.artishop_backend.entities.Category;
import com.yelensoft.artishop_backend.entities.Product;
import com.yelensoft.artishop_backend.exceptions.NotFoundException;
import com.yelensoft.artishop_backend.repositories.CategoryRepository;
import com.yelensoft.artishop_backend.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidatService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    public void validateProduct(ProductDto product) {
        boolean hasProduct = false;
        for (Category category : product.getCategoryIds()) {
           if(category == null) {
               throw new NotFoundException(" la catégorie  n'existe pas ");
           }

            Product productExist = productRepository.getByNameAndCategoryIdsId(product.getName(), category.getId());
            if(productExist != null){
                   hasProduct = true;
                   break;
            }
        }


        if(hasProduct){
            throw new NotFoundException("Ce produit" + product.getName() + " existe déjà");
        }
    }
}
