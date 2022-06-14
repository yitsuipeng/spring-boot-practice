package com.example.demo.service;

import com.example.demo.entity.Product;
import com.example.demo.entity.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    void saveProduct(Product product);

    ProductResponse getProducts(String category, int page, Long id, String keyword);

    Page<Product> findAll(Pageable pageable);

    Page<Product> findByCategoryEquals(String category, Pageable pageable);

    Page<Product> findByTitleLike(String keyword, Pageable pageable);

    List<Product> addVariants(List<Product> productResult);
}
