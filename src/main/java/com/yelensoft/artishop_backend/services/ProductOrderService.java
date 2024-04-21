package com.yelensoft.artishop_backend.services;

import com.yelensoft.artishop_backend.Repository.ProductOrderRepository;
import com.yelensoft.artishop_backend.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yelensoft.artishop_backend.Repository.UsersRepository;
import com.yelensoft.artishop_backend.enumClass.OrderStatus;

import java.util.Optional;

@Service
public class ProductOrderService {
     final private UsersRepository usersRepository;
    final private ProductOrderRepository productOrderRepository;

    public ProductOrderService(UsersRepository uRepository,ProductOrderRepository pRepository){
        this.usersRepository = uRepository;
        this.productOrderRepository = pRepository;
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

        return ResponseEntity.ok(productOrderRepository.findByIdAndUserId(id,id_user).get()) ;
    }

    public ResponseEntity<String> deleteProductOrder(Long id,Long id_user){

            if (!productOrderRepository.findByIdAndUserId(id,id_user).isPresent()){
                return ResponseEntity.ok("Not found");
            }
            productOrderRepository.deleteByIdAndUserId(id,id_user);
        return ResponseEntity.ok("Done") ;
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
        existingOrder.setPaymentMethod(updatedOrder.getPaymentMethod());
        ProductOrder savedOrder = productOrderRepository.save(existingOrder);
        return ResponseEntity.ok(savedOrder);
    }
}
