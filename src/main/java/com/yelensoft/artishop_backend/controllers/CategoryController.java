package com.yelensoft.artishop_backend.controllers;

import com.yelensoft.artishop_backend.model.Category;
import com.yelensoft.artishop_backend.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("categories")
    @Operation(summary = "Ajout d'une nouvelle catégorie dans la base de donnée")
    public ResponseEntity<Category> addCategory(@RequestBody @Valid Category category){
        return categoryService.addCategory(category);
    }

    @PostMapping("users/{userId}/stores/{storeId}/products/{productId}/categories/add")
    @Operation(summary = "Ajout d'une liste de catégorie à un produit")
    public ResponseEntity<String> addCategoryToProduct(@PathVariable Long userId, @PathVariable Long storeId,
                                                         @PathVariable Long productId,
                                                         @RequestBody Map<String, List<Long>> categoryIds){
        /*
            L'objet map doit être de la forme:
            {
                "categoryIds" : [1,2,...]
            }
        */
        return categoryService.addCategoryToProduct(userId, storeId, productId, categoryIds);
    }

    @DeleteMapping("users/{userId}/stores/{storeId}/products/{productId}/categories/{categoryId}/delete")
    @Operation(summary = "Supprimer une catégorie dans un produit")
    public ResponseEntity<String> deleteCategoryFromProduct(@PathVariable Long userId,
                                                            @PathVariable Long storeId,
                                                            @PathVariable Long productId,
                                                            @PathVariable Long categoryId){
        return categoryService.deleteCategoryFromProduct(userId, storeId, productId, categoryId);
    }

    @PatchMapping("categories/{categoryId}/update")
    @Operation(summary = "Modifier une catégorie")
    public ResponseEntity<Category> updateCategory(@PathVariable Long categoryId,
                                                   @RequestBody Map<String, Object> updateDataMap) {
        return categoryService.updateCategory(categoryId, updateDataMap);
    }

    @GetMapping("categories")
    @Operation(summary = "Obtenir toutes les categories")
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @GetMapping("categories/{categoryId}")
    @Operation(summary = "Obtenir une catégorie")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long categoryId){
        return categoryService.getCategoryById(categoryId);
    }

    @DeleteMapping("categories/{categoryId}/delete")
    @Operation(summary = "Supprimer une catégorie dans la base de donnée")
    public ResponseEntity<Boolean> deleteCategory(@PathVariable Long categoryId){
        return categoryService.deleteCategory(categoryId);
    }
}
