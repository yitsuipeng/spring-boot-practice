package com.example.demo;

import com.example.demo.entity.Product;
import com.example.demo.entity.ProductResponse;

import java.util.List;
import java.util.Map;

public class Converter {
    private Converter() {

    }

    public static ProductResponse toProductResponse(List<Product> product) {
        ProductResponse response = new ProductResponse();
        response.setData(product);

        return response;
    }
}
