package com.yelensoft.artishop_backend.services;

import com.yelensoft.artishop_backend.Repository.ProductItemRepository;
import com.yelensoft.artishop_backend.Repository.ProductOrderRepository;
import com.yelensoft.artishop_backend.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yelensoft.artishop_backend.Repository.UsersRepository;
import com.yelensoft.artishop_backend.enumClass.OrderStatus;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ProductOrderService {
     final private UsersRepository usersRepository;
    final private ProductOrderRepository productOrderRepository;
    final private ProductItemRepository productItemRepository;

    public ProductOrderService(UsersRepository uRepository,ProductItemRepository IRepository,ProductOrderRepository pRepository){
        this.usersRepository = uRepository;
        this.productOrderRepository = pRepository;
        this.productItemRepository = IRepository;
     }
    public ResponseEntity<ProductOrder>  AddProductOrder(int nbProductItem, double totalAmount, Long id_user, Address address, PaymentMethod paymentMethod) {
       
        Optional<User> user = usersRepository.findById(id_user);
        if (!user.isPresent()){
           return ResponseEntity.notFound().build();
        }
        ProductOrder productOrder = new ProductOrder();
        productOrder.setStatus(OrderStatus.IN_PROGRESS);
        productOrder.setNbProductItem(nbProductItem);
        productOrder.setTotalAmount(totalAmount);
        productOrder.setUser(user.get());
        productOrder.setAddress(address);
        productOrder.setPaymentMethod(paymentMethod);
        return ResponseEntity.ok(productOrder);
    }

    public ResponseEntity<ProductOrder> readProductOrder(Long id,Long id_user){

        Optional<ProductOrder> productOrder = productOrderRepository.findByIdAndUserId(id,id_user);
        if (productOrder.isPresent()){
            return ResponseEntity.ok(productOrder.get()) ;
        }else {
            return ResponseEntity.notFound().build();
        }

    }

    public ResponseEntity<String> deleteProductOrder(Long id,Long id_user){

            if (!productOrderRepository.findByIdAndUserId(id,id_user).isPresent()){
                return ResponseEntity.ok("Not found");
            }
            //ici si le productorder contient des produititems faisant parti du panier il ne sauront supprimer
            productItemRepository.deletecartnull(id);
            productItemRepository.changeproductordertonull(id);
            productOrderRepository.deleteByIdAndUserId(id,id_user);

        return ResponseEntity.ok("Done") ;
    }
    //ici c'est lorsque tout les processus de la commande est fini c'est a dire l'argent est tranfere
    public ResponseEntity<ProductOrder> orderDone(Long id,Long id_user){
        Optional<ProductOrder> optionalProductOrder = productOrderRepository.findByIdAndUserId(id, id_user);
        if (!optionalProductOrder.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        ProductOrder existingOrder = optionalProductOrder.get();
        existingOrder.setStatus(OrderStatus.END);
        productItemRepository.changecarttonull(optionalProductOrder.get().getId());
        ProductOrder savedOrder = productOrderRepository.save(existingOrder);
        return ResponseEntity.ok(savedOrder);

    }
    public ResponseEntity<ProductOrder> updateProductOrder(Long id, Long id_user, ProductOrder updatedOrder) {
        Optional<ProductOrder> optionalProductOrder = productOrderRepository.findByIdAndUserId(id, id_user);
        if (!optionalProductOrder.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        ProductOrder existingOrder = optionalProductOrder.get();
        existingOrder.setStatus(updatedOrder.getStatus());
        existingOrder.setNbProductItem(updatedOrder.getNbProductItem());
        existingOrder.setTotalAmount(updatedOrder.getTotalAmount());
        existingOrder.setAddress(updatedOrder.getAddress());
        existingOrder.setUpdateDate(LocalDateTime.now());
        existingOrder.setPaymentMethod(updatedOrder.getPaymentMethod());
        ProductOrder savedOrder = productOrderRepository.save(existingOrder);
        return ResponseEntity.ok(savedOrder);
    }
}
