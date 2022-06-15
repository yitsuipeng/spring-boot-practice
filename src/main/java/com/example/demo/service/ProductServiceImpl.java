package com.example.demo.service;

import com.example.demo.Converter;
import com.example.demo.entity.Product;
import com.example.demo.entity.ProductResponse;
import com.example.demo.entity.ProductVariant;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ProductVariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductVariantRepository productVariantRepository;

    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public ProductResponse getProducts(String category, int page, Long id, String keyword) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by("id").ascending());
        Page<Product> products = null;
        long count;

        switch (category) {
            case "all":
                products = productRepository.findAll(pageable);
                count = productRepository.count();
                break;
            case "men":
            case "women":
            case "accessories":
                products = productRepository.findByCategoryEquals(category, pageable);
                count = productRepository.countByCategoryEquals(category);
                break;
            case "search":
                products = productRepository.findByTitleLike(keyword, pageable);
                count = productRepository.countByTitleLike(keyword);
                break;
            default:
                products = productRepository.findAll(pageable);
                count = productRepository.count();
                break;
        }
        System.out.println(products.getContent());
        List<Product> productResult = addVariants(products.getContent());
        return Converter.toProductResponse(productResult);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> findByCategoryEquals(String category, Pageable pageable) {
        return productRepository.findByCategoryEquals(category, pageable);
    }

    @Override
    public Page<Product> findByTitleLike(String keyword, Pageable pageable) {
        return productRepository.findByTitleLike(keyword, pageable);
    }

    public List<Product> addVariants(List<Product> productResult) {
        List<Long> productIds = productResult
                .stream()
                .map(product -> product.getId())
                .collect(Collectors.toList());
        System.out.println(productIds.getClass().getName());
        System.out.println(productIds);

        List<ProductVariant> productVariants = productVariantRepository.findByProductIdIn(productIds);
        Map<Long, List<ProductVariant>> variantMap = new HashMap<>();
        productVariants.forEach(v -> {
            if (!variantMap.containsKey(v.getProductId()))
                variantMap.put(v.getProductId(), new LinkedList<>());
            variantMap.get(v.getProductId()).add(v);
        });
        System.out.println(variantMap);

        productResult.stream().map(p -> {
            if (!variantMap.containsKey(p.getId())) return p;

            List<Map<String, Object>> variants = new LinkedList<>();
            Set<Map<String, String>> colors = new HashSet<>();
            Set<String> sizes = new HashSet<>();

            variantMap.get(p.getId()).forEach(v -> {
                Map<String, Object> vmap = new HashMap<>();
                vmap.put("color_code", v.getColorCode());
                vmap.put("size", v.getSize());
                vmap.put("stock", v.getStock());
                variants.add(vmap);
                Map<String, String> cmap = new HashMap<>();
                cmap.put("color_code", v.getColorCode());
                cmap.put("color_name", v.getColorName());
                colors.add(cmap);
                sizes.add(v.getSize());
            });
            p.setVariants(variants);
            p.setColors(colors.stream().collect(Collectors.toList()));
            p.setSizes(sizes.stream().collect(Collectors.toList()));
            return p;
        }).collect(Collectors.toList());
        return productResult;
    }
}
