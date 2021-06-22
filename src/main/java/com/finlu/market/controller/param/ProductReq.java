package com.finlu.market.controller.param;

import lombok.Data;


@Data
public class ProductReq {
    private String name;
    private String desc;
    private Double price;
    private String picUrl;
    private Integer count;
}
