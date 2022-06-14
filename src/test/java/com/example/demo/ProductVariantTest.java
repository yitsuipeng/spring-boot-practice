package com.example.demo;

import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ProductVariantRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductVariantTest {

    @Autowired
    private ProductVariantRepository productVariantRepository;

    @Test
    public void test() throws Exception {
        List<Long> productIdList = new ArrayList<>();
        productIdList.add(Long.parseLong("201902191210"));

        Pageable pageable = PageRequest.of(0, 5, Sort.by("id").ascending());
        Assert.assertEquals(76, productVariantRepository.findAll().size());
        Assert.assertEquals(4, productVariantRepository.findByProductIdIn(productIdList).size());

    }
}
