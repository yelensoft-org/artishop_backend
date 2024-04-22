package com.yelensoft.artishop_backend.controllers;

import com.yelensoft.artishop_backend.model.ProductOrder;
import com.yelensoft.artishop_backend.services.ProductOrderService;
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

    @PostMapping("/productorders/add")
    public ResponseEntity<ProductOrder> createProductOrder(@RequestBody ProductOrder productOrder) {
        return productOrderService.AddProductOrder(productOrder.getNbProductItem(), productOrder.getTotalAmount(),
                productOrder.getUser().getId(), productOrder.getAddress(), productOrder.getPaymentMethod());
    }

    @GetMapping("/productorders/{id}/{id_user}")
    public ResponseEntity<ProductOrder> readProductOrder(@PathVariable Long id,@PathVariable Long id_user) {
        return productOrderService.readProductOrder(id,id_user);
    }

    @PutMapping("/productorders/{id}/{id_user}")
    public ResponseEntity<ProductOrder> updateProductOrder(@PathVariable Long id,@PathVariable Long id_user, @RequestBody ProductOrder updatedOrder) {
        return productOrderService.updateProductOrder(id, id_user,updatedOrder);
    }

    @DeleteMapping("/productorders/{id}/{id_user}")
    public ResponseEntity<String> deleteProductOrder(@PathVariable Long id,@PathVariable Long id_user) {
        return productOrderService.deleteProductOrder(id,id_user);
    }
}
