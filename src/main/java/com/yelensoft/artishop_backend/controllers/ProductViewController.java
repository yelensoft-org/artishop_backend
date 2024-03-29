package com.yelensoft.artishop_backend.controllers;

import com.yelensoft.artishop_backend.model.ProductView;
import com.yelensoft.artishop_backend.services.ProductViewService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/")
public class ProductViewController {
    final private ProductViewService productViewService;

    public ProductViewController(ProductViewService productViewService) {
        this.productViewService = productViewService;
    }

    @PostMapping("users/{userId}/store/{storeId}/products/{productId}/addView")
    @Operation(summary = "Ajouter une nouvelle vue à un produit")
    public ResponseEntity<ProductView> addProductView(@PathVariable Long userId,
                                                      @PathVariable Long storeId,
                                                      @PathVariable Long productId,
                                                      @Valid @RequestBody ProductView productView) {
        return productViewService.addProductView(userId, storeId, productId, productView);
    }

    @Operation(summary = "Modifier une vue de  produit")
    @PatchMapping("users/{userId}/stores/{storeId}/products/{productId}/productViews/{productViewId}/update")
    public ResponseEntity<ProductView> updateProductView(@PathVariable Long userId, @PathVariable Long storeId,
                                                 @PathVariable Long productId, @PathVariable Long productViewId,
                                                 @RequestBody Map<String, Object> updateDataMap) {
        return productViewService.updateProductView(userId, storeId, productId, productViewId, updateDataMap);
    }

    @Operation(summary = "Supprimer une vue de  produit")
    @DeleteMapping("users/{userId}/stores/{storeId}/products/{productId}/productViews/{productViewId}/delete")
    public ResponseEntity<Boolean> deleteProductView(@PathVariable Long userId, @PathVariable Long storeId,
                                                 @PathVariable Long productId, @PathVariable Long productViewId) {
        return productViewService.deleteProductView(userId, storeId, productId, productViewId);
    }

    @GetMapping("products/{productId}/productViews")
    @Operation(summary = "Récuperer la liste des vues d'un produit pour un produit donné")
    public List<ProductView> getAllProductViewByProductId(@PathVariable Long productId) {
        return productViewService.getAllProductViewByProductId(productId);
    }

    @GetMapping("productViews/{productViewId}")
    @Operation(summary = "Récuperer une vue par son ID")
    public ResponseEntity<ProductView> getProductViewById(@PathVariable Long productViewId) {
        return productViewService.getProductViewById(productViewId);
    }
}
