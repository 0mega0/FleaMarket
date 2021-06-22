package com.finlu.market.dao.entity;

import com.finlu.market.dao.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity(name = "t_product")
@Getter
@Setter
public class ProductEntity extends BaseEntity {

    public static final int PRODUCT_STATUS_PUBLISH = 1;

    /**
     * 商品名称
     */
    @Column(nullable = false)
    private String name;

    /**
     * 商品描述
     * 这里不能直接使用desc作为字段名，否则会直接报错
     */
    @Column(name = "c_desc")
    private String desc;

    /**
     * 商品价格
     */
    @Column(nullable = false)
    private Double price;

    /**
     * 商品状态
     */
    @Column(nullable = false)
    private Integer status;

    private String picUrl;

    private Integer count;

}
