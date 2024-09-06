package com.yelensoft.artishop_backend.services;

import com.yelensoft.artishop_backend.configuration.ApiSuccessResponse;
import com.yelensoft.artishop_backend.configuration.NotFoundException;
import com.yelensoft.artishop_backend.entities.*;
import com.yelensoft.artishop_backend.repositories.CartRepository;
import com.yelensoft.artishop_backend.repositories.PaymentMethodRepository;
import com.yelensoft.artishop_backend.repositories.ProductItemRepository;
import com.yelensoft.artishop_backend.repositories.ProductOrderRepository;
import com.yelensoft.artishop_backend.repositories.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yelensoft.artishop_backend.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductOrderService {
     final private CustomerRepository customerRepository;
    final private ProductOrderRepository productOrderRepository;
    final private ProductItemRepository productItemRepository;
    final private CartRepository cartRepository;
    final private PaymentMethodRepository paymentMethodRepository;



    public ProductOrderService(CustomerRepository uRepository, PaymentMethodRepository pMethod , CartRepository cRepository, ProductItemRepository IRepository, ProductOrderRepository pRepository){
        this.customerRepository = uRepository;
        this.productOrderRepository = pRepository;
        this.productItemRepository = IRepository;
        this.cartRepository = cRepository;
        this.paymentMethodRepository = pMethod;
     }
    public ResponseEntity<ProductOrder>  AddProductOrder(Long id_user, Long id_Pmetod, Address address) {
       
        Optional<Customer> user = customerRepository.findById(id_user);
        Optional<Cart> cart = cartRepository.findById(user.get().getCart().getId());
        Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findById(id_Pmetod);
        double totalAmount = 0.0 ;
        if (!user.isPresent() || !cart.isPresent() || cart.get().getProductItems().isEmpty() || !paymentMethod.isPresent() || address ==null){
           return ResponseEntity.notFound().build();
        }
        for (ProductItem item : cart.get().getProductItems()) {
           totalAmount = totalAmount + item.getProductView().getProduct().getPrice()*item.getNbExemplaire() ;
        } ;
        ProductOrder productOrder = new ProductOrder();
        productOrder.setStatus(OrderStatus.IN_PROGRESS);
        productOrder.setNbProductItem(cart.get().getProductItems().size());
        productOrder.setTotalAmount(totalAmount);
        productOrder.setCustomer(user.get());
        productOrder.setAddress(address);
        productOrder.setPaymentMethod(paymentMethod.get());
        return ResponseEntity.ok(productOrder);
    }

    public ResponseEntity<ProductOrder>  AddProductOrder2(Long id_user,Long id_Pmetod) {

        Optional<Customer> user = customerRepository.findById(id_user);
        Optional<Cart> cart = cartRepository.findById(user.get().getCart().getId());
        Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findById(id_Pmetod);
        double totalAmount = 0.0 ;
        if (!user.isPresent() || !cart.isPresent() || cart.get().getProductItems().isEmpty() || !paymentMethod.isPresent() || user.get().getAddress() == null){
            return ResponseEntity.notFound().build();
        }
        for (ProductItem item : cart.get().getProductItems()) {
            totalAmount = totalAmount + item.getProductView().getProduct().getPrice()*item.getNbExemplaire() ;
        } ;
        ProductOrder productOrder = new ProductOrder();
        productOrder.setStatus(OrderStatus.IN_PROGRESS);
        productOrder.setNbProductItem(cart.get().getProductItems().size());
        productOrder.setTotalAmount(totalAmount);
        productOrder.setCustomer(user.get());
        productOrder.setAddress(user.get().getAddress());
        productOrder.setPaymentMethod(paymentMethod.get());
        return ResponseEntity.ok(productOrder);
    }

    public ResponseEntity<ProductOrder> readProductOrder(Long id,Long id_user){

        Optional<ProductOrder> productOrder = productOrderRepository.findByIdAndCustomerId(id,id_user);
        if (productOrder.isPresent()){
            return ResponseEntity.ok(productOrder.get()) ;
        }else {
            return ResponseEntity.notFound().build();
        }

    }

    public ResponseEntity<List<ProductItem>> readProductOrderProductItems(Long id, Long id_user){

        Optional<ProductOrder> productOrder = productOrderRepository.findByIdAndCustomerId(id,id_user);
        if (productOrder.isPresent()){
            return ResponseEntity.ok(productItemRepository.findByProductOrderId(id)) ;
        }else {
            return ResponseEntity.notFound().build();
        }

    }

    public ResponseEntity<String> deleteProductOrder(Long id,Long id_user){

            if (!productOrderRepository.findByIdAndCustomerId(id,id_user).isPresent()){
                return ResponseEntity.ok("Not found");
            }
            //ici si le productorder contient des produititems faisant parti du panier il ne sauront supprimer
            productItemRepository.deletecartnull(id);
            productItemRepository.changeproductordertonull(id);
            productOrderRepository.deleteByIdAndCustomerId(id,id_user);

        return ResponseEntity.ok("Done") ;
    }
    //ici c'est lorsque tout les processus de la commande est fini c'est a dire l'argent est tranfere
    public ResponseEntity<ProductOrder> orderDone(Long id,Long id_user){
        Optional<ProductOrder> optionalProductOrder = productOrderRepository.findByIdAndCustomerId(id, id_user);
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
        Optional<ProductOrder> optionalProductOrder = productOrderRepository.findByIdAndCustomerId(id, id_user);
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

    //    -----------------------------------------------------------------------------
    public Object addOrder(List<ProductOrder> productOrders) {

        for (ProductOrder pOrder : productOrders) {
            Optional<Customer> userExist = customerRepository.findById(pOrder.getCustomer().getId());
            if (userExist.isEmpty()) {
                throw new NotFoundException("Le compte utilisateur n'existe pas !");
            }

            validateProductOrder(pOrder);

            pOrder.setStatus(OrderStatus.IN_PROGRESS);
            pOrder.setCustomer(userExist.get());
            productOrderRepository.save(pOrder);
        }

        return ApiSuccessResponse.successResponse("Commande effectuée avec succès!");
    }

        private void validateProductOrder(ProductOrder pOrder) {
            if (pOrder.getAddress() == null) {
                throw new NotFoundException("Adresse non valide.");
            }
            if (pOrder.getPaymentMethod() == null) {
                throw new NotFoundException("Mode de paiement non valide.");
            }
        }
    //------------------------------------get productOrder by id
    public ProductOrder getProductOrderDetailById(Long id, Long id_user) {
       return productOrderRepository.getByIdAndCustomerId(id, id_user);

    }

    //    -------------------------------------get list productOrderActif
    public List<ProductOrder> getListProductOrder(Long id_user) {
        LocalDateTime dateEnd = LocalDateTime.now();
        LocalDateTime startDate = dateEnd.minusDays(15);
        List<ProductOrder> list = productOrderRepository.findByCustomerIdAndCreationDateBetween(id_user,startDate,dateEnd);
        if (list.isEmpty()) {
            return new ArrayList<>();
        }
        return list;
    }
}
