package com.example.demo.service;

import com.example.demo.entity.Product;
import com.example.demo.entity.ProductResponse;

public interface ProductService {
    void saveProduct(Product product);

    ProductResponse getProducts(String category, int page, Long id, String keyword);
}
