package com.yelensoft.artishop_backend.controllers;
import com.yelensoft.artishop_backend.model.ProductOrder;
import com.yelensoft.artishop_backend.services.ProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.yelensoft.artishop_backend.model.Address;
import com.yelensoft.artishop_backend.model.PaymentMethod;
import com.yelensoft.artishop_backend.model.ProductItem;
import com.yelensoft.artishop_backend.services.ProductItemServive;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("api/v1/")
public class ProductItemController {

    @Autowired
    private ProductItemServive productItemService;

     // Endpoint pour passer une commande
     @PostMapping("/")
     public String order(@RequestParam int nbExemplaire,
                                         @RequestParam Long id_productView,
                                         @RequestParam Long id_productOrder,
                                         @RequestParam Long id_user,
                                         @RequestParam PaymentMethod paymentMethod,
                                         @RequestBody Address address) {
         return productItemService.ordering(nbExemplaire, id_productView, id_productOrder, id_user, paymentMethod, address);
     }

    @PostMapping("/productItems/add")
    @Operation(summary = "Ajout d'un nouveau produitItem")

    public ResponseEntity<ProductItem> addProductItem(@RequestParam int nbExemplaire,
                                                      @RequestParam Long id_productView,
                                                      @RequestParam Long id_productOrder) {
        return productItemService.addProductItem(nbExemplaire, id_productView, id_productOrder);
    }

    @GetMapping("/productItems/read/{id}")
    @Operation(summary = "Afficher un productItem")
    public ResponseEntity<ProductItem> readProductItem(@PathVariable Long id) {
        return productItemService.readProductItem(id);
    }

    @PutMapping("/productItems/update/{id}")
    @Operation(summary = "Modifier un product item")
    public ResponseEntity<ProductItem> updateProductItem(@PathVariable long id,
                                                         @RequestBody ProductItem updatedProductItem) {
        return productItemService.updateProductItem(id, updatedProductItem);
    }

    @DeleteMapping("/productItems/del/{id}")
    @Operation(summary = "Supprimer un ProductItem")
    public ResponseEntity<String> deleteProductItem(@PathVariable Long id) {
        return productItemService.deleteProductItem(id);
    }

}
