package com.yelensoft.artishop_backend.services;

import com.yelensoft.artishop_backend.exceptions.BadRequestException;
import com.yelensoft.artishop_backend.exceptions.NotFoundException;
import com.yelensoft.artishop_backend.model.Product;
import com.yelensoft.artishop_backend.model.Store;
import com.yelensoft.artishop_backend.repositories.ProductRepository;
import com.yelensoft.artishop_backend.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StoreRepository storeRepository;

    public ResponseEntity<Product> getProductById(Long productId) {
        try {
            Optional<Product> optionalProduct = productRepository.findById(productId);
            if(optionalProduct.isPresent()) {
                return ResponseEntity.ok(optionalProduct.get());
            }
            throw new NotFoundException("Ce produit n'existe pas");
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    public ResponseEntity<Product> addProduct(Long userId, Long storeId, Product product){
        try {
            Optional<Store> optionalStore = storeRepository.findByIdAndUserId(storeId, userId);
            if (optionalStore.isPresent()) {
                product.setStore(optionalStore.get());
                product.setAvailable(true);
                product.setDeleted(false);
                product.setPublished(false);
                product.setCreationDate(LocalDateTime.now());
                product.setUpdateDate(LocalDateTime.now());

                Product productSaved = productRepository.save(product);
                URI location = ServletUriComponentsBuilder.
                        fromCurrentContextPath().path("{id}").
                        buildAndExpand(productSaved.getId()).toUri();
                return ResponseEntity.created(location).body(productSaved);

            }
            throw new NotFoundException("Vous n'ête pas autoriser à effectuer une tel action");
        }catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }


    public ResponseEntity<Product> updateProduct(Long userId, Long storeId, Long productId, Map<String, Object> updateDataMap) {
        try {
            Optional<Product> productOptional = productRepository.findByIdAndStoreIdAndStoreUserId(productId, storeId, userId);
            if(productOptional.isPresent()){
                Product productUpdate = productOptional.get();

                productUpdate.setName((String) updateDataMap.getOrDefault("name", productUpdate.getName()));
                productUpdate.setDescription((String) updateDataMap.getOrDefault("description", productUpdate.getDescription()));
                productUpdate.setPrice((Double) updateDataMap.getOrDefault("price", productUpdate.getPrice()));
                productUpdate.setStockQuantity((int) updateDataMap.getOrDefault("stockQuantity", productUpdate.getStockQuantity()));
                productUpdate.setDeleted((boolean) updateDataMap.getOrDefault("deleted", productUpdate.isDeleted()));
                productUpdate.setPublished((boolean) updateDataMap.getOrDefault("published", productUpdate.isPublished()));
                productUpdate.setAvailable((boolean) updateDataMap.getOrDefault("available", productUpdate.isAvailable()));
                productUpdate.setGlobalSize((String) updateDataMap.getOrDefault("globalSize", productUpdate.getGlobalSize()));
                productUpdate.setUpdateDate(LocalDateTime.now());
            }
            throw new NotFoundException("Vous n'ête pas autorisé à modifier ce produit ou bien le produit n'existe pas dans la base de données");
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    public ResponseEntity<Boolean> deleteProduct(Long userId, Long storeId, Long productId) {
        try {
            Optional<Product> productOptional = productRepository
                    .findByIdAndStoreIdAndStoreUserId(productId, storeId, userId);
            if (productOptional.isPresent()){
                Product productUpdate = productOptional.get();
                productUpdate.setDeleted(true);
                productUpdate.setUpdateDate(LocalDateTime.now());
                return ResponseEntity.ok(productRepository.save(productUpdate).isDeleted());
            }
            throw new NotFoundException("Vous ne disposez pas des permission pour effectuer cette action");
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    public List<Product> getAllProductsByStoreId(Long storeId) {
        return productRepository.findAllByStoreId(storeId);
    }

    public List<Product> getAllProductsByUser(int fromIndex, int toIndex) {
        try {
            return productRepository.findAll().subList(fromIndex, toIndex);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
}
