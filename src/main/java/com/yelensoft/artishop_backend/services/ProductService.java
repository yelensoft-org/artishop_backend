package com.yelensoft.artishop_backend.services;

import com.yelensoft.artishop_backend.configuration.ResponseHandler;
import com.yelensoft.artishop_backend.dto.AddProductDto;
import com.yelensoft.artishop_backend.dto.ProductPresentationDto;
import com.yelensoft.artishop_backend.entities.Customer;
import com.yelensoft.artishop_backend.exceptions.BadRequestException;
import com.yelensoft.artishop_backend.exceptions.NotFoundException;
import com.yelensoft.artishop_backend.entities.Product;
import com.yelensoft.artishop_backend.entities.Store;
import com.yelensoft.artishop_backend.repositories.CategoryRepository;
import com.yelensoft.artishop_backend.repositories.ProductRepository;
import com.yelensoft.artishop_backend.repositories.StoreRepository;
import com.yelensoft.artishop_backend.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;
    public static int MAX_LIMIT = 20;

    /*public ProductService(ProductRepository productRepository, StoreRepository storeRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
        this.categoryRepository = categoryRepository;
    }*/

    public ResponseEntity<Product> getProductById(Long productId) {
        try {
            Optional<Product> optionalProduct = productRepository.findByIdAndDeletedFalse(productId);
            if(optionalProduct.isPresent()) {
                return ResponseEntity.ok(optionalProduct.get());
            }
            throw new NotFoundException("Ce produit n'existe pas");
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    public ResponseEntity<Product> addProduct(Long userId, Long storeId, AddProductDto request){
        try {
            Optional<Store> optionalStore = storeRepository.findByIdAndCustomerId(storeId, userId);
            if (optionalStore.isPresent()) {
                Product product = request.getProduct();
                product.setCategories(categoryRepository.findAllById(request.getIdsCategories()));
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
            throw new NotFoundException("Vous n'ête pas autoriser à effectuer une telle action");
        }catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }


    public ResponseEntity<Product> updateProduct(Long userId, Long storeId, Long productId, Map<String, Object> updateDataMap) {
        try {
            Optional<Product> productOptional = productRepository.findByIdAndStoreIdAndStoreCustomerIdAndDeletedFalse(productId, storeId, userId);
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

                return ResponseEntity.ok(productRepository.save(productUpdate));
            }
            throw new NotFoundException("Vous n'ête pas autorisé à modifier ce produit ou bien le produit n'existe pas dans la base de données");
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    public ResponseEntity<Boolean> deleteProduct(Long userId, Long storeId, Long productId) {
        try {
            Optional<Product> productOptional = productRepository
                    .findByIdAndStoreIdAndStoreCustomerIdAndDeletedFalse(productId, storeId, userId);
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
        return productRepository.findAllByStoreIdAndDeletedFalse(storeId);
    }

    public ResponseEntity<?> getAllProductPerPage(Long idUser, int page, int size){
        try {
            Customer customer = customerRepository.findById(idUser).orElseThrow(()-> new NotFoundException("utilisateur invalide"));
            Pageable pageable = PageRequest.of(page, size);
            Page<Product> productPage = productRepository.findAll(pageable);
            List<Product> productList = productPage.getContent();
            List<ProductPresentationDto> presentationDtos = productList.stream().map(
                    product -> {
                        return new ProductPresentationDto(
                                product.getId(),
                                product.getName(),
                                product.getPrice(),
                                product.getProductViews().get(0).getImageUrls(),
                                customer.getCart().getProductItems().stream().anyMatch(productItem ->
                                        productItem.getProductView().getProduct().getId() == product.getId())
                                );
                    }
            ).toList();
            return ResponseHandler.generateResponse("success", HttpStatus.OK,presentationDtos);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    public List<Product> getAllProductsByUser(int fromIndex, int limit) {
        try {
            if (fromIndex>=0 && limit>0) {
                try {
                    Product product = productRepository.findAllByDeletedFalse().get(fromIndex);
                    // on s'assure qu'on ne dépasse pas les limites de la liste
                    int endIndex = Math.min(fromIndex + limit, productRepository.findAllByDeletedFalse().size());

                    return productRepository.findAllByDeletedFalse().subList(fromIndex, endIndex);
                }catch (Exception e) {
                    /*int endIndex = Math.min(limit, productRepository.findAllByDeletedFalse().size());
                    return productRepository.findAllByDeletedFalse().subList(0, endIndex);*/
                    return new ArrayList<>();
                }
            }
            throw new BadRequestException("L'index de depart ou la limit est incorrecte");
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
}
