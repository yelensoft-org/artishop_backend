package com.yelensoft.artishop_backend.controllers;
import com.yelensoft.artishop_backend.entities.Address;
import com.yelensoft.artishop_backend.entities.ProductItem;
import com.yelensoft.artishop_backend.entities.ProductOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.yelensoft.artishop_backend.services.ProductItemServive;

import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class ProductItemController {

    @Autowired
    private ProductItemServive productItemService;

     // Endpoint pour passer une commande
     @PostMapping("/productItems")
     @Operation(summary = "Pour commander directement un Produit Item")
     public ResponseEntity<ProductOrder> order(@RequestParam int nbExemplaire,
                                               @RequestParam Long id_productView,
                                               @RequestParam Long id_user,
                                               @RequestParam Long id_paymentMethod,
                                               @RequestParam (required = false) Boolean option,
                                               @RequestBody Address address) {
         //l'option c'est pour savoir si l'utilisateur veut etre livre a son domicile
         if (option){
             return productItemService.ordering2(nbExemplaire, id_productView, id_user, id_paymentMethod);
         }else {
             return productItemService.ordering(nbExemplaire, id_productView, id_user, id_paymentMethod, address);
         }
        }

    @GetMapping("/productItems")
    @Operation(summary = "Afficher un productItem du panier d'un user")
    public ResponseEntity<ProductItem> readProductItem(@RequestParam Long id, @RequestParam Long id_user) {
        return productItemService.readProductItem(id,id_user);
    }

    @DeleteMapping("/productItems")
    @Operation(summary = "Supprimer un productItem du panier d'un user")
    public ResponseEntity<String> delProductItem(@RequestParam Long id,@RequestParam Long id_user) {
        return productItemService.deleteProductItem(id,id_user);
    }

    @GetMapping("/productItems/readall")
    @Operation(summary = "Afficher la liste des productItem se trouvant dans le panier")
    public ResponseEntity<List<ProductItem>> readallProductItem(@RequestParam Long id_user) {
        return productItemService.listallitem(id_user);
    }


    @PostMapping("/productItems/addtocart")
    @Operation(summary = "Ajout d'un nouveau produitItem dans un panier")

    public ResponseEntity<ProductItem> addProductItemtocart(@RequestParam int nbExemplaire,
                                                            @RequestParam Long id_productView,
                                                            @RequestParam Long id_user) {
        return productItemService.addtoCart(id_user,id_productView,nbExemplaire);
    }


    @PutMapping("/productItems")
    @Operation(summary = "Modifier un product item")
    public ResponseEntity<ProductItem> updateProductItem(@RequestParam Long id,
                                                         @RequestParam Long id_user,
                                                         @RequestParam int nbexemplaire) {
        return productItemService.updateProductItem(id,id_user ,nbexemplaire);
    }

}
