package com.yelensoft.artishop_backend.services;

import com.yelensoft.artishop_backend.exceptions.BadRequestException;
import com.yelensoft.artishop_backend.exceptions.NotFoundException;
import com.yelensoft.artishop_backend.entities.Product;
import com.yelensoft.artishop_backend.entities.ProductView;
import com.yelensoft.artishop_backend.repositories.ProductRepository;
import com.yelensoft.artishop_backend.repositories.ProductViewRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductViewService {
    final private ProductViewRepository productViewRepository;
    final private ProductRepository productRepository;

    public ProductViewService(ProductViewRepository productViewRepository, ProductRepository productRepository) {
        this.productViewRepository = productViewRepository;
        this.productRepository = productRepository;
    }

    public ResponseEntity<ProductView> addProductView(Long userId, Long storeId, Long productId,
                                                      ProductView productView) {
        try {
            Optional<Product> productOptional = productRepository
                    .findByIdAndStoreIdAndStoreCustomerIdAndDeletedFalse(productId, storeId, userId);
            if(productOptional.isPresent()) {
                Product product = productOptional.get();
                int sum = product.getProductViews().stream().map(ProductView::getNbAvailable)
                        .reduce(0, Integer::sum);
                if (product.getStockQuantity()>=(sum+productView.getNbAvailable())) {
                    productView.setProduct(product);
                    productView.setCreationDate(LocalDateTime.now());
                    productView.setUpdateDate(LocalDateTime.now());

                    ProductView productViewSaved = productViewRepository.save(productView);
                    URI location = ServletUriComponentsBuilder.
                            fromCurrentContextPath().path("{id}").
                            buildAndExpand(productViewSaved.getId()).toUri();
                    return ResponseEntity.created(location).body(productViewSaved);
                }
                throw new BadRequestException("Error: 652 - La quantité disponible pour ce produit est atteint");
            }
            throw new NotFoundException("Ce produit n'exite pas");
        }catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }


    public ResponseEntity<ProductView> updateProductView(Long userId, Long storeId, Long productId,
                                                         Long productViewId,
                                                         Map<String, Object> updateDataMap) {
        try {
            Optional<ProductView> productViewOptional = productViewRepository
                    .findByIdAndProductIdAndProductStoreIdAndProductStoreCustomerId(
                            productViewId, productId, storeId, userId
                    );
            if(productViewOptional.isPresent()){
                ProductView productViewUpdate = productViewOptional.get();

                productViewUpdate.setSizes((String) updateDataMap.getOrDefault("sizes",
                        productViewUpdate.getSizes()));
                productViewUpdate.setImageUrls((String) updateDataMap.getOrDefault("imageUrls",
                        productViewUpdate.getImageUrls()));
                productViewUpdate.setNbAvailable((int) updateDataMap.getOrDefault("nbAvailable",
                        productViewUpdate.getNbAvailable()));
                productViewUpdate.setColor((String) updateDataMap.getOrDefault("color",
                        productViewUpdate.getColor()));
                productViewUpdate.setUpdateDate(LocalDateTime.now());

                return ResponseEntity.ok(productViewRepository.save(productViewUpdate));
            }
            throw new NotFoundException("Vous n'ête pas autorisé à modifier cette vue ou bien la vue n'existe pas");
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }


    public ResponseEntity<Boolean> deleteProductView(Long userId, Long storeId, Long productId,
                                                     Long productViewId) {
        try {
            Optional<ProductView> productViewOptional = productViewRepository
                    .findByIdAndProductIdAndProductStoreIdAndProductStoreCustomerId(
                            productViewId, productId, storeId, userId
                    );
            if (productViewOptional.isPresent()){
                ProductView productViewUpdate = productViewOptional.get();
                productViewRepository.delete(productViewUpdate);
                return ResponseEntity.ok(true);
            }
            throw new NotFoundException("Vous ne disposez pas des permission pour effectuer cette action");
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    public List<ProductView> getAllProductViewByProductId(Long productId) {
        return productViewRepository.findAllByProductId(productId);
    }

    public ResponseEntity<ProductView> getProductViewById(Long productViewId) {
        try {
            Optional<ProductView> optionalProductView = productViewRepository.findById(productViewId);
            if (optionalProductView.isPresent()){
                return ResponseEntity.ok(optionalProductView.get());
            }
            throw new NotFoundException("Cette vue de produit n'existe pas");
        }catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}
