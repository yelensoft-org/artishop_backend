package com.yelensoft.artishop_backend.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.yelensoft.artishop_backend.Repository.*;
import com.yelensoft.artishop_backend.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yelensoft.artishop_backend.enumClass.OrderStatus;

@Service
public class ProductItemServive {
    private final ProductItemRepository productItemRepository;
    private final ProductOrderRepository productOrderRepository;
    private final ProductViewRepository productViewRepository;
    private final UsersRepository usersRepository;
    private final PaymentMethodRepository paymentMethodRepository;

    ProductItemServive(ProductItemRepository ItemRepository,PaymentMethodRepository pRepository ,UsersRepository uRepository, ProductViewRepository ViewRepository, ProductOrderRepository OrderRepository) {
        this.productItemRepository = ItemRepository;
        this.productViewRepository = ViewRepository;
        this.productOrderRepository = OrderRepository;
        this.usersRepository = uRepository;
        this.paymentMethodRepository = pRepository;
    }

    public ResponseEntity<ProductItem> addProductItem(int nbExemplaire, Long id_productView, Long id_productOrder) {
        ProductItem productItem = new ProductItem();
        Optional<ProductView> productView = productViewRepository.findById(id_productView);
        Optional<ProductOrder> productOrder = productOrderRepository.findById(id_productOrder);
        if (productView.isPresent() && nbExemplaire >= 1) {
            productItem.setProductView(productView.get());
            productItem.setProductOrder(productOrder.get());
            productItem.setNbExemplaire(nbExemplaire);

        } else {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productItemRepository.save(productItem));
    }

    public ResponseEntity<ProductItem> readProductItem(Long id,Long id_user) {
        Optional<User> user =   usersRepository.findById(id_user);
        Optional<ProductItem> productItem = productItemRepository.findById(id);
        if (user.isPresent() && productItem.isPresent() && user.get().getCart().getId() == productItem.get().getCart().getId()){
            return ResponseEntity.ok(productItemRepository.findById(id).get());
        }else {
            return  ResponseEntity.notFound().build();
        }




    }

    public ResponseEntity<ProductItem> updateProductItem(Long Id, Long id_user, int Nbexemplaire) {
        Optional<User> user =   usersRepository.findById(id_user);
        Optional<ProductItem> optionalProductItem = productItemRepository.findById(Id);
        if (user.isPresent() && optionalProductItem.isPresent() && user.get().getCart().getId() == optionalProductItem.get().getCart().getId()){
            ProductItem existingProductItem = optionalProductItem.get();

            existingProductItem.setNbExemplaire(Nbexemplaire) ;
            existingProductItem.setUpdateDate(LocalDateTime.now());

            ProductItem savedProductItem = productItemRepository.save(existingProductItem);
            return ResponseEntity.ok(savedProductItem);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<String> deleteProductItem(Long id,Long id_user) {
        Optional<User> user =   usersRepository.findById(id_user);
        Optional<ProductItem> productItem = productItemRepository.findById(id);
        if (user.isPresent() && productItem.isPresent() && user.get().getCart().getId() == productItem.get().getCart().getId()){
            productItemRepository.deleteById(id);
            return ResponseEntity.ok("Done");
        }else {
            return  ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<List<ProductItem>> listallitem(Long id_user) {
        Optional<User> user = usersRepository.findById(id_user);
        if (!user.isPresent() && user.get().getCart()==null) {
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.ok(productItemRepository.findByCart_Id(user.get().getCart().getId()));
    }

    public ResponseEntity<ProductOrder> ordering(int nbExemplaire, Long id_productView, Long id_user, Long id_paymentMethod, Address address) {
        ProductOrder productOrder = new ProductOrder();

        Optional<User> user = usersRepository.findById(id_user);
        Optional<ProductView> productView = productViewRepository.findById(id_productView);
        Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findById(id_paymentMethod);
        if (user.isPresent() && productView.isPresent() && nbExemplaire >=1 && paymentMethod.isPresent()) {
            productOrder.setUser(user.get());
            productOrder.setTotalAmount(productView.get().getProduct().getPrice() * nbExemplaire);
            productOrder.setNbProductItem(nbExemplaire);
            productOrder.setStatus(OrderStatus.IN_PROGRESS);
            productOrder.setAddress(address);
            productOrder.setPaymentMethod(paymentMethod.get());
            addProductItem(nbExemplaire, id_productView, productOrderRepository.save(productOrder).getId()).ok();
            return ResponseEntity.ok(productOrder);
        }else {
                        return ResponseEntity.badRequest().build();
                    }

    }
    public ResponseEntity<ProductOrder> ordering2(int nbExemplaire, Long id_productView, Long id_user, Long id_paymentMethod) {
        ProductOrder productOrder = new ProductOrder();

        Optional<User> user = usersRepository.findById(id_user);
        Optional<ProductView> productView = productViewRepository.findById(id_productView);
        Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findById(id_paymentMethod);
        if (user.isPresent() && productView.isPresent() && nbExemplaire >=1 && paymentMethod.isPresent()) {
            productOrder.setUser(user.get());
            productOrder.setTotalAmount(productView.get().getProduct().getPrice() * nbExemplaire);
            productOrder.setNbProductItem(nbExemplaire);
            productOrder.setStatus(OrderStatus.IN_PROGRESS);
            productOrder.setAddress(user.get().getAddress());
            productOrder.setPaymentMethod(paymentMethod.get());
            addProductItem(nbExemplaire, id_productView, productOrderRepository.save(productOrder).getId()).ok();
            return ResponseEntity.ok(productOrder);
        }else {
            return ResponseEntity.badRequest().build();
        }

    }

    public ResponseEntity<ProductItem> addtoCart(Long id_user, Long id_productView, int nbExemplaire) {
        ProductItem productItem = new ProductItem();
        Optional<ProductView> productView = productViewRepository.findById(id_productView);
        Optional<User> user = usersRepository.findById(id_user);
        if (productView.isPresent() && nbExemplaire >= 1 && user.isPresent() && user.get().getCart()!=null) {
            productItem.setProductView(productView.get());
            productItem.setCart(user.get().getCart());
            productItem.setNbExemplaire(nbExemplaire);

        } else {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productItemRepository.save(productItem));
    }
}