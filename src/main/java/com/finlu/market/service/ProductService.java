package com.finlu.market.service;

import com.alibaba.fastjson.JSONObject;
import com.finlu.market.controller.param.ProductReq;
import com.finlu.market.domain.Product;

import java.util.List;


public interface ProductService {
    /**
     * 获取所有商品
     * @return 商品列表
     */
    List<Product> getAllCheckedProducts();

    /**
     * 创建商品
     * @param productReq
     * @return
     */
    Product createProduct(ProductReq productReq);

    /**
     * 更新商品
     * @param id
     * @param productReq
     * @return
     */
    Product updateProduct(Long id, ProductReq productReq);


    /**
     * 用户购买商品
     * @param params
     * @param userId
     * @return
     */
//    Double buy1Product(JSONObject params, Long userId);


    /**
     * 购买一件商品
     * @param productId
     * @param userId
     * @param itemCount
     * @return
     */
    Double buyProduct(Long productId, Long userId, Integer itemCount);

    /**
     * 通过id查找Product
     * @param id
     * @return
     */
    Product findProductById(Long id);
}
