package com.finlu.market.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long id;
    private String name;
    private String desc;
    private Double price;
    private Integer status;
    private Date createTime;
    private LocalDateTime updateTime;
    private String picUrl;
    private Integer count;
}
