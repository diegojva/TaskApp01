package com.app.task.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProduct;

    @Column(length = 80, nullable = false)
    private String name;

    @Column(length = 60, nullable = false)
    private String brand;

    private Double price;

    private String photoUrl;
}
