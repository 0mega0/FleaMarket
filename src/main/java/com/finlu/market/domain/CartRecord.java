package com.finlu.market.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartRecord {
    private Long id;
    private Product product;
    private Integer count;
}
