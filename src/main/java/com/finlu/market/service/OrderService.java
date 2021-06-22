package com.finlu.market.service;

import com.finlu.market.dao.entity.CartEntity;
import com.finlu.market.domain.CartRecord;
import com.finlu.market.domain.OrderRecord;

import java.util.List;


public interface OrderService {
    /**
     * 将商品加入购物车
     * @param userId
     * @param productId
     * @param count
     * @return
     */
    CartRecord addProductToCart(Long userId, Long productId, Integer count);

    boolean removeAllProductFromCart(Long userId);

    CartRecord removeProductFromCart(Long userId, Long productId, Integer count);
    /**
     * 通过userId获取用户所有的购物车记录
     * @param userId
     * @return
     */
    List<CartRecord> getAllCartRecordByUserId(Long userId);

    List<OrderRecord> getAllOrderRecordByUserId(Long userId);
    boolean changeCartProductCount(Long cartId, Integer count);
    boolean removeOneProductFromCart(Long userId, Long cartId);
}
