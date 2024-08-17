package com.yelensoft.artishop_backend.controllers;

import com.yelensoft.artishop_backend.entities.Address;
import com.yelensoft.artishop_backend.entities.ProductItem;
import com.yelensoft.artishop_backend.entities.ProductOrder;
import com.yelensoft.artishop_backend.services.ProductOrderService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class ProductOrderController {

    private final ProductOrderService productOrderService;

    @Autowired
    public ProductOrderController(ProductOrderService productOrderService) {
        this.productOrderService = productOrderService;
    }

    @PostMapping("/productorders")
    @Operation(summary = "Pour commander un panier complet")
    public ResponseEntity<ProductOrder> createProductOrder(@RequestParam Long id_user,
                                                           @RequestParam Long id_paymentMethod,
                                                           @RequestParam (required = false) Boolean option,
                                                           @RequestBody Address address) {
        if (option){
            return productOrderService.AddProductOrder2(id_user,id_paymentMethod);

        }else {

            return productOrderService.AddProductOrder(id_user,id_paymentMethod,address);
        }
    }

    @GetMapping("/productorders")
    @Operation(summary = "Pour afficher une commande effectue par un utilisateur")
    public ResponseEntity<ProductOrder> readProductOrder(@RequestParam Long id,@RequestParam Long id_user) {
        return productOrderService.readProductOrder(id,id_user);
    }

    @GetMapping("/productorders/items")
    @Operation(summary = "Pour afficher les produit items contenant une commande effectue par un utilisateur")
    public ResponseEntity<List<ProductItem>> readProduct(@RequestParam Long id, @RequestParam Long id_user) {
        return productOrderService.readProductOrderProductItems(id,id_user);
    }

    @PutMapping("/productorders")
    public ResponseEntity<ProductOrder> updateProductOrder(@RequestParam Long id,@RequestParam Long id_user, @RequestBody ProductOrder updatedOrder) {
        return productOrderService.updateProductOrder(id, id_user,updatedOrder);
    }

    /* ici je ne pense pas si l'utilisateur pourra modifier la commande car lorque la commade est lance elle peut seulement
    l'annuler et recommande le panier j'ai fait en sorte que meme si elle annule la commande les element du panier resterons
    pour que les element du panier disparaisse il faudra appele la methode orderDone pour dire la commande est
    confirme le payment est effectue tout est bon
    @DeleteMapping("/productorders")
    @Operation(summary = "Supprimer toute commande lancé")
    public ResponseEntity<String> deleteProductOrder(@RequestParam Long id,@RequestParam Long id_user) {
        return productOrderService.deleteProductOrder(id,id_user);
    }*/
}
