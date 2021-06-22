package com.finlu.market.dao.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.finlu.market.dao.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity(name = "order_item")
@Setter
@Getter
public class OrderRecordItemEntity extends BaseEntity {

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private ProductEntity productEntity;

    @Column(name = "item_count")
    private Integer itemCount;

    @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_record_id", referencedColumnName = "id")
    private OrderRecordEntity orderRecordEntity;
}
