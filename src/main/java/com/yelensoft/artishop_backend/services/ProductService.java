package com.yelensoft.artishop_backend.services;

import com.yelensoft.artishop_backend.dto.ProductDto;
import com.yelensoft.artishop_backend.entities.Category;
import com.yelensoft.artishop_backend.exceptions.BadRequestException;
import com.yelensoft.artishop_backend.exceptions.NotFoundException;
import com.yelensoft.artishop_backend.entities.Product;
import com.yelensoft.artishop_backend.entities.Store;
import com.yelensoft.artishop_backend.repositories.CategoryRepository;
import com.yelensoft.artishop_backend.repositories.ProductRepository;
import com.yelensoft.artishop_backend.repositories.StoreRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ProductService {

    @Autowired
    private  ValidatService validatService;
    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;
    public static int MAX_LIMIT = 20;

    public ProductService(ProductRepository productRepository, StoreRepository storeRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
        this.categoryRepository = categoryRepository;
    }

    public ResponseEntity<Product> getProductById(Long productId) {
        try {
            Optional<Product> optionalProduct = productRepository.findByIdAndDeletedFalse(productId);
            if(optionalProduct.isPresent()) {
                return ResponseEntity.ok(optionalProduct.get());
            }
            throw new NotFoundException("Ce produit n'existe pas");
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }


    //-------------------------------------------------------------------------------
    public ProductDto addProduct(long storeId , ProductDto productDto) {

        Store store = storeRepository.findById(storeId);
        if(store == null) {
            throw new NotFoundException("Cette boutique  n'existe pas");
        }

        validatService.validateProduct(productDto);

        ModelMapper modelMapper = new ModelMapper();

        // Convertir ProductDto en Product

        Product product = modelMapper.map(productDto, Product.class);
        List<Category> categories = new ArrayList<>();

        for(Category category : product.getCategoryIds()) {
            Category categoryExist = categoryRepository.findById(category.getId());
            categories.add(categoryExist);

        }

        product.setCategoryIds(categories);
        product.setStore(store);
        Product productSaved = productRepository.save(product);

        System.out.println("-------------------pppppppp" + productSaved);
         return  ProductDto.toGetDtoProduct(productSaved);

    }


//    public ResponseEntity<Product> updateProduct(Long userId, Long storeId, Long productId, Map<String, Object> updateDataMap) {
//        try {
//            Optional<Product> productOptional = productRepository.findByIdAndStoreIdAndStoreUserAppIdAndDeletedFalse(productId, storeId, userId);
//            if(productOptional.isPresent()){
//                Product productUpdate = productOptional.get();
//
//                productUpdate.setName((String) updateDataMap.getOrDefault("name", productUpdate.getName()));
//                productUpdate.setDescription((String) updateDataMap.getOrDefault("description", productUpdate.getDescription()));
//                productUpdate.setPrice((Double) updateDataMap.getOrDefault("price", productUpdate.getPrice()));
//                productUpdate.setQuantity((int) updateDataMap.getOrDefault("stockQuantity", productUpdate.getQuantity()));
//                productUpdate.setDeleted((boolean) updateDataMap.getOrDefault("deleted", productUpdate.isDeleted()));
//                productUpdate.setPublished((boolean) updateDataMap.getOrDefault("published", productUpdate.isPublished()));
//                productUpdate.setAvailable((boolean) updateDataMap.getOrDefault("available", productUpdate.isAvailable()));
////                productUpdate.setGlobalSize((String) updateDataMap.getOrDefault("globalSize", productUpdate.getGlobalSize()));
//                productUpdate.setUpdateDate(LocalDateTime.now());
//
//                return ResponseEntity.ok(productRepository.save(productUpdate));
//            }
//            throw new NotFoundException("Vous n'ête pas autorisé à modifier ce produit ou bien le produit n'existe pas dans la base de données");
//        }catch (Exception e){
//            throw new BadRequestException(e.getMessage());
//        }
//    }

    public ResponseEntity<Boolean> deleteProduct(Long userId, Long storeId, Long productId) {
        try {
            Optional<Product> productOptional = productRepository
                    .findByIdAndStoreIdAndStoreUserAppIdAndDeletedFalse(productId, storeId, userId);
            if (productOptional.isPresent()){
                Product productUpdate = productOptional.get();
                productUpdate.setDeleted(true);
                productUpdate.setUpdateDate(LocalDateTime.now());
                return ResponseEntity.ok(productRepository.save(productUpdate).isDeleted());
            }
            throw new NotFoundException("Vous ne disposez pas des permission pour effectuer cette action");
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    public List<Product> getAllProductsByStoreId(Long storeId) {
        return productRepository.findAllByStoreIdAndDeletedFalse(storeId);
    }

    public List<Product> getAllProductsByUser(int fromIndex, int limit) {
        try {
            if (fromIndex>=0 && limit>0) {
                try {
                    Product product = productRepository.findAllByDeletedFalse().get(fromIndex);
                    // on s'assure qu'on ne dépasse pas les limites de la liste
                    int endIndex = Math.min(fromIndex + limit, productRepository.findAllByDeletedFalse().size());

                    return productRepository.findAllByDeletedFalse().subList(fromIndex, endIndex);
                }catch (Exception e) {
                    /*int endIndex = Math.min(limit, productRepository.findAllByDeletedFalse().size());
                    return productRepository.findAllByDeletedFalse().subList(0, endIndex);*/
                    return new ArrayList<>();
                }
            }
            throw new BadRequestException("L'index de depart ou la limit est incorrecte");
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
}
