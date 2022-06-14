package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="product")
@EntityListeners(AuditingEntityListener.class)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "category")
    private String category;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private long price;

    @Column(name = "texture")
    private String texture;

    @Column(name = "wash")
    private String wash;

    @Column(name = "place")
    private String place;

    @Column(name = "note")
    private String note;

    @Column(name = "story")
    private String story;

    @Column(name = "main_image")
    private String main_image;

    @Column(name = "images")
    private String images;

    @Transient
    private List<Map<String, Object>> variants;

    @Transient
    private List<Map<String, String>> colors;

    @Transient
    private List<String> sizes;

    public Product(){}

    public Product(Long id,
                   String category,
                   String title,
                   String description,
                   Long price,
                   String texture,
                   String wash,
                   String place,
                   String note,
                   String story,
                   String main_image,
                   String images,
                   List<Map<String, Object>> variants,
                   List<Map<String, String>> colors,
                   List<String> sizes
    ){
        this.id = id;
        this.category = category;
        this.title = title;
        this.description = description;
        this.price = price;
        this.texture = texture;
        this.wash = wash;
        this.place = place;
        this.note = note;
        this.story = story;
        this.main_image = main_image;
        this.images = images;
        this.variants = variants;
        this.colors = colors;
        this.sizes = sizes;
    }

}