package com.yelensoft.artishop_backend.controllers;

import com.yelensoft.artishop_backend.model.Address;
import com.yelensoft.artishop_backend.model.ProductOrder;
import com.yelensoft.artishop_backend.services.ProductOrderService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/")
public class ProductOrderController {

    private final ProductOrderService productOrderService;

    @Autowired
    public ProductOrderController(ProductOrderService productOrderService) {
        this.productOrderService = productOrderService;
    }

    /*@PostMapping("/productorders")
    @Operation(summary = "Pour commander un panier complet")
    public ResponseEntity<ProductOrder> createProductOrder(@RequestParam Long id_cart,
                                                           @RequestParam Long id_user,
                                                           @RequestParam Long id_paymentMethod,
                                                           @RequestParam (required = false) Boolean option,
                                                           @RequestBody Address address) {
        if (option){
            return productOrderService.AddProductOrder();

        }else {

            return productOrderService.AddProductOrder();
        }
    }*/

    @GetMapping("/productorders")
    @Operation(summary = "Pour afficher une commande effectue par un utilisateur")
    public ResponseEntity<ProductOrder> readProductOrder(@RequestParam Long id,@RequestParam Long id_user) {
        return productOrderService.readProductOrder(id,id_user);
    }

    @PutMapping("/productorders/{id}/{id_user}")
    public ResponseEntity<ProductOrder> updateProductOrder(@PathVariable Long id,@PathVariable Long id_user, @RequestBody ProductOrder updatedOrder) {
        return productOrderService.updateProductOrder(id, id_user,updatedOrder);
    }

    @DeleteMapping("/productorders")
    @Operation(summary = "Supprimer toute commande lancé")
    public ResponseEntity<String> deleteProductOrder(@RequestParam Long id,@RequestParam Long id_user) {
        return productOrderService.deleteProductOrder(id,id_user);
    }
}
