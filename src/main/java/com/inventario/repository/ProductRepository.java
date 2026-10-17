package com.inventario.repository;

import com.inventario.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCaseOrderByName(String name);
    List<Product> findByCategoryIdOrderByName(Long categoryId);
    List<Product> findByStockLessThanOrderByStock(Integer stock);
}
