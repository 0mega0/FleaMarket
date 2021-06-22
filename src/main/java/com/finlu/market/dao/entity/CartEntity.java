package com.finlu.market.dao.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Entity(name = "cart")
@Data
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private ProductEntity productEntity;


    //购买数量

    @Column(nullable = false)
    private Integer count;
}
