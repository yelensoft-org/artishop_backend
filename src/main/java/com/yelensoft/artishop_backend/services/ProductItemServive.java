package com.yelensoft.artishop_backend.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yelensoft.artishop_backend.Repository.ProductItemRepository;
import com.yelensoft.artishop_backend.Repository.ProductOrderRepository;
import com.yelensoft.artishop_backend.Repository.UsersRepository;
import com.yelensoft.artishop_backend.enumClass.OrderStatus;
import com.yelensoft.artishop_backend.model.Address;
import com.yelensoft.artishop_backend.model.PaymentMethod;
import com.yelensoft.artishop_backend.model.ProductItem;
import com.yelensoft.artishop_backend.model.ProductOrder;
import com.yelensoft.artishop_backend.model.ProductView;
import com.yelensoft.artishop_backend.model.User;
import com.yelensoft.artishop_backend.Repository.ProductViewRepository;

@Service
public class ProductItemServive {
     private final ProductItemRepository productItemRepository;
     private final ProductOrderRepository productOrderRepository;
     private final ProductViewRepository productViewRepository;
     private final UsersRepository usersRepository;
    ProductItemServive(ProductItemRepository ItemRepository,UsersRepository uRepository, ProductViewRepository ViewRepository,ProductOrderRepository OrderRepository){
        this.productItemRepository = ItemRepository;
        this.productViewRepository = ViewRepository;
        this.productOrderRepository = OrderRepository;
        this.usersRepository = uRepository;
    }  
    public ResponseEntity<ProductItem> addProductItem(int nbExemplaire, Long id_productView, Long id_productOrder) {
        ProductItem productItem = new ProductItem();
        Optional<ProductView> productView = productViewRepository.findById(id_productView);
        Optional<ProductOrder> productOrder = productOrderRepository.findById(id_productOrder);
        if (productView.isPresent() && productOrder.isPresent() && nbExemplaire >= 1) {
            productItem.setProductView(productView.get());
            productItem.setProductOrder(productOrder.get());
            productItem.setNbExemplaire(nbExemplaire);

        } else {
            return ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(productItemRepository.save(productItem));
    }
    public ResponseEntity<ProductItem> readProductItem(Long id){

        return ResponseEntity.ok(productItemRepository.findById(id).get()) ;
    }
    public ResponseEntity<ProductItem> updateProductItem(long Id, ProductItem updatedProductItem) {
        Optional<ProductItem> optionalProductItem = productItemRepository.findById(Id);
        
        if (optionalProductItem.isPresent()) {
            ProductItem existingProductItem = optionalProductItem.get();
            
            existingProductItem.setNbExemplaire(updatedProductItem.getNbExemplaire());
            existingProductItem.setProductView(updatedProductItem.getProductView());
            existingProductItem.setProductOrder(updatedProductItem.getProductOrder());
            existingProductItem.setUpdateDate(LocalDateTime.now());
            
            ProductItem savedProductItem = productItemRepository.save(existingProductItem);
            return ResponseEntity.ok(savedProductItem);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    public ResponseEntity<String> deleteProductItem(Long id){
        Optional<ProductItem> productItem = productItemRepository.findById(id);
        if (productItem.isPresent()) {
            // pas totalement fini
            productItemRepository.deleteById(id);
        } else {
            return ResponseEntity.ok("Not found");
        }
        return ResponseEntity.ok("Done") ;
    }
    public String ordering(int nbExemplaire,Long id_productView,Long id_productOrder,Long id_user,PaymentMethod paymentMethod,Address address){
        ProductOrder productOrder = new ProductOrder();

        Optional<User> user = usersRepository.findById(id_user);
        Optional<ProductView> productView = productViewRepository.findById(id_productView);
        if (user.isPresent() && productView.isPresent()) {
            
            productOrder.setStatus(OrderStatus.IN_PROGRESS);
            productOrder.setNbProductItem(nbExemplaire);
            productOrder.setTotalAmount(productView.get().getProduct().getPrice()*nbExemplaire);
            productOrder.setUser(user.get());
            productOrder.setAddress(address);
            productOrder.setPaymentMethod(paymentMethod);
            
            addProductItem(nbExemplaire, id_productView, productOrderRepository.save(productOrder).getId()).ok();

        }else{
            ResponseEntity.notFound().build();
        }

        return "En cours";

    }
}
