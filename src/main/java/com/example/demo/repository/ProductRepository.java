package com.example.demo.repository;

import java.util.List;

import com.example.demo.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findByCategoryEquals(String category, Pageable pageable);
    Page<Product> findByTitleLike(String keyword, Pageable pageable);
    long countByCategoryEquals(String category);
    long countByTitleLike(String keyword);
    List<Product> findByCategoryIn(List<String> category);
}
