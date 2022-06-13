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

    public List<Product> addVariants(List<Product> productResult) {
        List<Long> productIds = (List<Long>) productResult
                .stream()
                .map(product -> product.getId());
        List<ProductVariant> productVariants = productVariantRepository.findByProductIdIn(productIds);
        Map<Long, List<ProductVariant>> variantMap = new HashMap<>();
        productVariants.forEach(v -> {
            if (!variantMap.containsKey(v.getProductId()))
                variantMap.put(v.getProductId(), new LinkedList<>());
            variantMap.get(v.getProductId()).add(v);
        });
        productResult.stream().map(p -> {
            if (!variantMap.containsKey(p.getId())) return p;

            List<Map<String, Object>> variants = new LinkedList<>();
            Set<Map<String, String>> colors = new HashSet<>();
            Set<String> sizes = new HashSet<>();

            variantMap.get(p.getId()).forEach(v -> {
                Map<String, Object> vmap = new HashMap<>();
                vmap.put("color_code", v.getColor_code());
                vmap.put("size", v.getSize());
                vmap.put("stock", v.getStock());
                variants.add(vmap);
                Map<String, String> cmap = new HashMap<>();
                cmap.put("color_code", v.getColor_code());
                cmap.put("color_name", v.getColor_name());
                colors.add(cmap);
                sizes.add(v.getSize());
            });
            p.setVariants(variants);
            p.setColors(colors.stream().collect(Collectors.toList()));
            p.setSizes(sizes.stream().collect(Collectors.toList()));
            return p;
        });
        return productResult;
    }
}
