package com.finlu.market.dao.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.finlu.market.dao.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity(name = "order_record")
@Getter
@Setter
public class OrderRecordEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userEntity;

}
