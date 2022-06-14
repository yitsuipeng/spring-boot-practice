package com.example.demo.repository;

import com.example.demo.entity.Product;
import com.example.demo.entity.ProductVariant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Integer> {
    List<ProductVariant> findByProductIdIn(Collection<Long> size);
    List<ProductVariant> findBySizeIn(List<String> size);
}
