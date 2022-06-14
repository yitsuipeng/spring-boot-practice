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
    private String colorCode;

    @Column(name = "color_name")
    private String colorName;

    @Column(name = "size")
    private String size;

    @Column(name = "stock")
    private long stock;

    @Column(name = "product_id")
    private long productId;

    public ProductVariant(){}

    public ProductVariant(long id,
                          String colorCode,
                          String colorName,
                          String size,
                          long stock,
                          long productId
    ){
        this.id = id;
        this.colorCode = colorCode;
        this.colorName = colorName;
        this.size = size;
        this.stock = stock;
        this.productId = productId;
    }
}