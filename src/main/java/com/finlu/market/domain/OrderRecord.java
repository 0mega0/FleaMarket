package com.finlu.market.domain;

import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
public class OrderRecord {
    @Data
    public static final class OrderRecordItem {
        private Product product;
        private Integer itemCount;
    }
    private List<OrderRecordItem> items;
    private Date createTime;
    private Long id;
}
