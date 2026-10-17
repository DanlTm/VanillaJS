package com.inventario.service;

import com.inventario.model.Product;
import com.inventario.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public ProductService(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    public List<Product> findAll(String search, Long categoryId) {
        if (search != null && !search.trim().isEmpty()) {
            return productRepository.findByNameContainingIgnoreCaseOrderByName(search.trim());
        }
        if (categoryId != null) {
            return productRepository.findByCategoryIdOrderByName(categoryId);
        }
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product save(Product product, Long categoryId) {
        if (categoryId != null) {
            product.setCategory(categoryService.findById(categoryId));
        } else {
            product.setCategory(null);
        }
        return productRepository.save(product);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    public long count() {
        return productRepository.count();
    }

    public List<Product> findLowStock() {
        return productRepository.findByStockLessThanOrderByStock(5);
    }

    public BigDecimal getInventoryValue() {
        BigDecimal total = BigDecimal.ZERO;
        List<Product> products = productRepository.findAll();
        for (Product p : products) {
            if (p.getPrice() != null && p.getStock() != null) {
                BigDecimal subtotal = p.getPrice().multiply(BigDecimal.valueOf(p.getStock()));
                total = total.add(subtotal);
            }
        }
        return total;
    }
}
