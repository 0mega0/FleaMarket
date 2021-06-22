package com.finlu.market.service.impl;

import com.finlu.market.dao.entity.ProductEntity;
import com.finlu.market.dao.repo.ProductRepository;
import com.finlu.market.domain.Product;
import com.finlu.market.service.AdminService;
import com.finlu.market.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public boolean check(Long productId, Integer status) {
        Optional<ProductEntity> optionalProductEntity = this.productRepository.findById(productId);
        if (optionalProductEntity.isPresent()) {
            ProductEntity productEntity = optionalProductEntity.get();
            productEntity.setStatus(status);
            this.productRepository.save(productEntity);
            return true;
        }
        return false;
    }

    @Override
    public List<Product> getAll() {
        List<ProductEntity> productEntities = this.productRepository.findAll();
        List<Product> products = productEntities.stream().map(productEntity -> {
            Product product = new Product();
            BeanUtils.copyProperties(productEntity, product);
            return product;
        }).collect(Collectors.toList());
        return products;
    }
}
