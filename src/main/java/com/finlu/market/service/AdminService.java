package com.finlu.market.service;

import com.finlu.market.domain.Product;
import org.springframework.stereotype.Service;

import java.util.List;


public interface AdminService {
    boolean check(Long productId, Integer status);
    List<Product> getAll();
}
