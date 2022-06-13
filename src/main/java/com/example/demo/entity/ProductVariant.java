package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="variant")
@EntityListeners(AuditingEntityListener.class)
public class ProductVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "color_code")
    private String color_code;

    @Column(name = "color_name")
    private String color_name;

    @Column(name = "size")
    private String size;

    @Column(name = "stock")
    private long stock;

    @Column(name = "product_id")
    private long productId;

    public ProductVariant(){}

    public ProductVariant(long id,
                          String color_code,
                          String color_name,
                          String size,
                          long stock,
                          long productId
    ){
        this.id = id;
        this.color_code = color_code;
        this.color_name = color_name;
        this.size = size;
        this.stock = stock;
        this.productId = productId;
    }
}