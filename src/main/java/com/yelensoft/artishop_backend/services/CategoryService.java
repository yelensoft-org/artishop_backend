package com.yelensoft.artishop_backend.services;

import com.yelensoft.artishop_backend.enumClass.SizeType;
import com.yelensoft.artishop_backend.exceptions.BadRequestException;
import com.yelensoft.artishop_backend.exceptions.NotFoundException;
import com.yelensoft.artishop_backend.entities.Category;
import com.yelensoft.artishop_backend.entities.Product;
import com.yelensoft.artishop_backend.repositories.CategoryRepository;
import com.yelensoft.artishop_backend.repositories.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public ResponseEntity<Category> addCategory(Category category){
        category.setCreationDate(LocalDateTime.now());
        category.setUpdateDate(LocalDateTime.now());
        category.setDeleted(false);
        return ResponseEntity.ok(categoryRepository.save(category));
    }

    public ResponseEntity<String> addCategoryToProduct(Long userId, Long storeId, Long productId,
                                                       Map<String, List<Long>> categoryIds) {
        try {
            Optional<Product> productOptional = productRepository.findByIdAndStoreIdAndStoreUserAppIdAndDeletedFalse(
                    productId, storeId, userId
            );
            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                if (categoryIds.containsKey("categoryIds")) {
                    List<Long> ids = categoryIds.get("categoryIds");
                    List<Category> categories = categoryRepository.findAllById(ids);
                    return addCategoryToProduct(product, categories);
                }
                throw new NotFoundException("Cette catégorie n'existe pas");
            }
            throw new NotFoundException("Ce produit n'existe pas");
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    private ResponseEntity<String> addCategoryToProduct(Product product, List<Category> categories) {
        product.setCategories(categories);
        productRepository.save(product);
        return ResponseEntity.ok("Ajout reussie");
    }

    public ResponseEntity<String> deleteCategoryFromProduct(Long userId, Long storeId, Long productId,
                                                            Long categoryId) {
        try {
            Optional<Product> productOptional = productRepository.findByIdAndStoreIdAndStoreUserAppIdAndDeletedFalse(
                    productId, storeId, userId
            );
            if (productOptional.isPresent()) {
                Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
                if (categoryOptional.isPresent()) {
                    return deleteCategoryFromProduct(productOptional.get(), categoryOptional.get());
                }
                throw new NotFoundException("Cette catégorie n'existe pas");
            }
            throw new NotFoundException("Ce produit n'existe pas");
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    private ResponseEntity<String> deleteCategoryFromProduct(Product product, Category category) {
        product.getCategories().remove(category);
        productRepository.save(product);
        return ResponseEntity.ok("Suppression reussie");
    }

    public ResponseEntity<Category> updateCategory(Long categoryId, Map<String, Object> updateDataMap) {
        try {
            Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
            if (categoryOptional.isPresent()) {
                Category categoryUpdate = categoryOptional.get();
                categoryUpdate.setName((String) updateDataMap.getOrDefault("name", categoryUpdate.getName()));
                categoryUpdate.setDescription((String) updateDataMap.getOrDefault("description", categoryUpdate.getDescription()));
                categoryUpdate.setImageUrl((String) updateDataMap.getOrDefault("imageUrl", categoryUpdate.getImageUrl()));
                categoryUpdate.setSizeType((SizeType) updateDataMap.getOrDefault("sizeType", categoryUpdate.getSizeType()));
                categoryUpdate.setUpdateDate(LocalDateTime.now());

                return ResponseEntity.ok(categoryRepository.save(categoryUpdate));
            }
            throw new NotFoundException("Cette catégorie n'existe pas");
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    public ResponseEntity<Category> getCategoryById(Long categoryId) {
        try {
            Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
            if (categoryOptional.isPresent()) {
                return ResponseEntity.ok(categoryOptional.get());
            }
            throw new NotFoundException("Cette catégorie n'existe pas");
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    public ResponseEntity<Boolean> deleteCategory(Long categoryId) {
        try {
            Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
            if (categoryOptional.isPresent()) {
                Category category = categoryOptional.get();
                category.setDeleted(true);
                categoryRepository.save(category);
                return ResponseEntity.ok(true);
            }
            throw new NotFoundException("Cette catégorie n'existe pas");
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
}
