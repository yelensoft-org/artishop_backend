package com.yelensoft.artishop_backend.Repository;

import com.yelensoft.artishop_backend.model.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductItemRepository extends JpaRepository<ProductItem,Long> {

  List<ProductItem> findByCart_Id(Long id);

    @Query(value = "DELETE FROM product_item \n" +
            "WHERE product_order_id = :id_produit_order \n" +
            "AND (cart_id IS NULL)\n", nativeQuery = true)
    void deletecartnull(Long id_produit_order);

    @Query(value = "UPDATE product_item\n" +
            "SET product_order_id = Null \n" +
            "WHERE product_order_id = :id_produit_order AND cart_id IS NOT NULL;\n", nativeQuery = true)
    void changeproductordertonull(Long id_produit_order);

  @Query(value = "UPDATE product_item\n" +
          "SET cart_id = Null \n" +
          "WHERE product_order_id = :id_produit_order ;\n", nativeQuery = true)
    void changecarttonull(Long id_produit_order);
}
