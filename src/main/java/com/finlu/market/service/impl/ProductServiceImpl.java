package com.finlu.market.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.finlu.market.controller.param.ProductReq;
import com.finlu.market.dao.entity.OrderRecordEntity;
import com.finlu.market.dao.entity.OrderRecordItemEntity;
import com.finlu.market.dao.entity.ProductEntity;
import com.finlu.market.dao.entity.UserEntity;
import com.finlu.market.dao.repo.OrderRecordItemRepository;
import com.finlu.market.dao.repo.OrderRecordRepository;
import com.finlu.market.dao.repo.ProductRepository;
import com.finlu.market.dao.repo.UserRepository;
import com.finlu.market.domain.Product;
import com.finlu.market.domain.User;
import com.finlu.market.service.ProductService;
import com.finlu.market.utils.JwtTokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRecordRepository orderRecordRepository;

    @Autowired
    private OrderRecordItemRepository orderRecordItemRepository;


    /**
     * 获取所有商品
     *
     * @return 商品列表
     */
    @Override
    public List<Product> getAllCheckedProducts() {
        List<ProductEntity> productEntities = this.productRepository.findAllByStatusIs(0);
        return productEntities.stream().map(productEntity -> {
            Product product = new Product();
            BeanUtils.copyProperties(productEntity, product);
            return product;
        }).collect(Collectors.toList());
    }

    /**
     * 创建商品
     *
     * @return
     */
    @Override
    public Product createProduct(ProductReq productReq) {
        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(productReq, productEntity);
        productEntity.setCreateTime(new Date());
        productEntity.setUpdateTime(LocalDateTime.now());
        productEntity.setStatus(ProductEntity.PRODUCT_STATUS_PUBLISH);
        productEntity = this.productRepository.save(productEntity);
        Product product = new Product();
        BeanUtils.copyProperties(productEntity, product);
        return product;
    }

    /**
     * 更新商品
     *
     * @param id
     * @param productReq
     * @return
     */
    @Override
    public Product updateProduct(Long id, ProductReq productReq) {
        Optional<ProductEntity> optionalProductEntity = this.productRepository.findById(id);
        Product product = new Product();
        if (optionalProductEntity.isPresent()) {
            ProductEntity productEntity = optionalProductEntity.get();
            BeanUtils.copyProperties(productReq, productEntity);
            productEntity = this.productRepository.save(productEntity);
            BeanUtils.copyProperties(productEntity, product);
        }
        return product;
    }

    /**
     * 用户购买商品
     *
     * @param params
     * @param userId
     * @return
     */
    public Double buy1Product(JSONObject params, Long userId) {
        double total_price = 0.0;

        Optional<UserEntity> optionalUserEntity = this.userRepository.findById(userId);
        if (!optionalUserEntity.isPresent()) {
            return (double) -1;
        }
        UserEntity userEntity = optionalUserEntity.get();

        JSONArray productInfoList = params.getJSONArray("productInfo");

        OrderRecordEntity orderRecordEntity = new OrderRecordEntity();
        orderRecordEntity.setUserEntity(userEntity);

        List<OrderRecordItemEntity> orderRecordItemEntityList = new ArrayList<>();
        for (int i = 0; i < productInfoList.size(); i++) {
            LinkedHashMap<String, Integer> productInfo = (LinkedHashMap<String, Integer>) productInfoList.get(i);
            Integer productId = productInfo.get("productId");
            Integer count = productInfo.get("count");
            Optional<ProductEntity> optionalProductEntity = productRepository.findById(Long.valueOf(productId));
            if (!optionalProductEntity.isPresent()) {
                return (double) -1;
            }
            ProductEntity productEntity = optionalProductEntity.get();
            OrderRecordItemEntity orderRecordItemEntity = new OrderRecordItemEntity();
            orderRecordItemEntity.setItemCount(count);
            orderRecordItemEntity.setProductEntity(productEntity);
            orderRecordItemEntity.setOrderRecordEntity(orderRecordEntity);
            orderRecordItemEntityList.add(orderRecordItemEntity);
        }
        this.orderRecordRepository.save(orderRecordEntity);
        for (OrderRecordItemEntity orderRecordItemEntity : orderRecordItemEntityList) {
            total_price += orderRecordItemEntity.getItemCount() * orderRecordItemEntity.getProductEntity().getPrice();
            this.orderRecordItemRepository.save(orderRecordItemEntity);
        }
        return total_price;
    }

    /**
     * 购买一件商品
     * @param productId
     * @param userId
     * @param itemCount
     * @return
     */
    @Override
    public Double buyProduct(Long productId, Long userId, Integer itemCount) {
        Optional<ProductEntity> optionalProductEntity =  this.productRepository.findById(productId);
        if (!optionalProductEntity.isPresent()) {
            return (double) -1;
        }
        ProductEntity productEntity = optionalProductEntity.get();

        Optional<UserEntity> optionalUserEntity = this.userRepository.findById(userId);
        if (!optionalUserEntity.isPresent()) {
            return (double) -1;
        }
        UserEntity userEntity = optionalUserEntity.get();

        OrderRecordEntity orderRecordEntity = new OrderRecordEntity();
        orderRecordEntity.setUserEntity(userEntity);
        orderRecordEntity = this.orderRecordRepository.save(orderRecordEntity);

        OrderRecordItemEntity orderRecordItemEntity = new OrderRecordItemEntity();
        productEntity.setCount(productEntity.getCount() - itemCount);
        orderRecordItemEntity.setProductEntity(productEntity);
        orderRecordItemEntity.setItemCount(itemCount);
        orderRecordItemEntity.setOrderRecordEntity(orderRecordEntity);
        orderRecordItemEntity = this.orderRecordItemRepository.save(orderRecordItemEntity);



        return orderRecordItemEntity.getItemCount() * orderRecordItemEntity.getProductEntity().getPrice();
    }


    /**
     * 通过id查找Product
     *
     * @param id
     * @return
     */
    @Override
    public Product findProductById(Long id) {
        Product product = new Product();
        Optional<ProductEntity> optionalProductEntity = this.productRepository.findById(id);
        optionalProductEntity.ifPresent(productEntity -> BeanUtils.copyProperties(productEntity,  product));
        return product;
    }

}
