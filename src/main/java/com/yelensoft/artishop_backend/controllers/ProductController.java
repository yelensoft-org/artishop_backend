package com.yelensoft.artishop_backend.controllers;

import com.yelensoft.artishop_backend.dto.AddProductDto;
import com.yelensoft.artishop_backend.entities.Product;
import com.yelensoft.artishop_backend.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/")
public class ProductController {
    final private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("users/{userId}/stores/{storeId}/products/add")
    @Operation(summary = "Ajout d'un nouveau produit par un artisan")
    public ResponseEntity<Product> addProduct(@PathVariable Long userId, @PathVariable Long storeId,
                                              @Valid @RequestBody AddProductDto request) {
        return productService.addProduct(userId, storeId, request);
    }

    @Operation(summary = "Récuperer les produits stocker dans une boutique")
    @GetMapping("stores/{storeId}/products")
    public List<Product> getAllProductsByStoreId(@PathVariable Long storeId){
        return productService.getAllProductsByStoreId(storeId);
    }

    @Operation(summary = "Recupère une liste de produits")
    @GetMapping(value = "products", params = {"from", "limit"})
    public List<Product> getAllProductsByUser(@RequestParam("from") int fromIndex,
                                              @RequestParam("limit") int limit){
        return productService.getAllProductsByUser(fromIndex, limit);
    }

    @Operation(summary = "Recupère une liste de produits par page")
    @GetMapping(value = "productsPerPage/{idUser}")
    public ResponseEntity<?> getAllProductsPerPage(@PathVariable Long idUser, @RequestParam("page") int page,
                                              @RequestParam("size") int size){
        return productService.getAllProductPerPage(idUser, page, size);
    }

    @GetMapping("products/{productId}")
    @Operation(summary = "Récuperer un produit par son ID")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        return productService.getProductById(productId);
    }

    @Operation(summary = "Modifier un produit dans une boutique")
    @PatchMapping("users/{userId}/stores/{storeId}/products/{productId}/update")
    public ResponseEntity<Product> updateProduct(@PathVariable Long userId, @PathVariable Long storeId, @PathVariable Long productId,
                                                 @RequestBody Map<String, Object> updateDataMap) {
        return productService.updateProduct(userId, storeId, productId, updateDataMap);
    }

    @Operation(summary = "Supprimer un produit")
    @DeleteMapping("users/{userId}/stores/{storeId}/products/{productId}/delete")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable Long userId, @PathVariable Long storeId,
                                                 @PathVariable Long productId) {
        return productService.deleteProduct(userId, storeId, productId);
    }
}
