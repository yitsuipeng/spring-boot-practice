package com.example.demo;

import com.example.demo.entity.Product;
import com.example.demo.entity.ProductResponse;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{category}")
    public ResponseEntity<List<Product>> getProduct(@PathVariable(value = "category", required = true) String category,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(required = false) Long id,
                                              @RequestParam(required = false) String keyword) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by("id").ascending());
        try {
            Page<Product> products = null;
            if (category == "search") {
                if (Objects.isNull(keyword))
                    throw new Exception();
                else
                    products = productService.findByTitleLike(keyword, pageable);
            }
//            else if (category == "details")
//                if (Objects.isNull(id))
//                    throw new Exception();
//                else
//                    products = productService.findById();
            else if (Objects.isNull(category))
                products = productService.findAll(pageable);
            else
                products = productService.findByCategoryEquals(category, pageable);

            System.out.println(products.getContent());
            List<Product> result = productService.addVariants(products.getContent());

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @PostMapping
//    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
//        ProductResponse product = productService.createProduct(request);
//
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(product.getId())
//                .toUri();
//
//        return ResponseEntity.created(location).body(product);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<ProductResponse> replaceProduct(
//            @PathVariable("id") String id,
//            @Valid @RequestBody ProductRequest request) {
//        ProductResponse product = productService.replaceProduct(id, request);
//        return ResponseEntity.ok(product);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity deleteProduct(@PathVariable("id") String id) {
//        productService.deleteProduct(id);
//        return ResponseEntity.noContent().build();
//    }

}