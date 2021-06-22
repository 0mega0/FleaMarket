package com.finlu.market.service.impl;

import com.finlu.market.dao.entity.*;
import com.finlu.market.dao.repo.*;
import com.finlu.market.domain.CartRecord;
import com.finlu.market.domain.OrderRecord;
import com.finlu.market.domain.Product;
import com.finlu.market.service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRecordRepository orderRecordRepository;

    @Autowired
    private OrderRecordItemRepository orderRecordItemRepository;

    /**
     * 将商品加入购物车
     *
     * @param userId
     * @param productId
     * @param count
     * @return
     */
    @Override
    public CartRecord addProductToCart(Long userId, Long productId, Integer count) {
        CartRecord cartRecord = null;
        Optional<UserEntity> optionalUserEntity = this.userRepository.findById(userId);
        Optional<ProductEntity> optionalProductEntity = this.productRepository.findById(productId);
        if (optionalProductEntity.isPresent() && optionalUserEntity.isPresent()) {
            // 购物车中是否已经存在记录
            Optional<CartEntity> optionalCartEntity = this.cartRepository.findCartEntityByProductEntityAndUserEntity(optionalProductEntity.get(), optionalUserEntity.get());
            CartEntity cartEntity = new CartEntity();
            if (optionalCartEntity.isPresent()) {
                cartEntity = optionalCartEntity.get();
                cartEntity.setCount(cartEntity.getCount() + count);
            } else {
                cartEntity.setUserEntity(optionalUserEntity.get());
                cartEntity.setProductEntity(optionalProductEntity.get());
                cartEntity.setCount(count);
            }
            this.cartRepository.save(cartEntity);
            cartRecord = new CartRecord();
            cartRecord.setProduct(getProduct(cartEntity));
            cartRecord.setCount(cartEntity.getCount());
        }
        return cartRecord;
    }

    @Override
    public boolean removeAllProductFromCart(Long userId) {
        Optional<UserEntity> optionalUserEntity = this.userRepository.findById(userId);
        if (optionalUserEntity.isPresent()) {
            List<CartEntity> allByUserEntity = this.cartRepository.getAllByUserEntity(optionalUserEntity.get());
            for (CartEntity cartEntity: allByUserEntity) {
                this.cartRepository.delete(cartEntity);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public CartRecord removeProductFromCart(Long userId, Long productId, Integer count) {
        CartRecord cartRecord = null;
        Optional<UserEntity> optionalUserEntity = this.userRepository.findById(userId);
        Optional<ProductEntity> optionalProductEntity = this.productRepository.findById(productId);
        if (optionalProductEntity.isPresent() && optionalUserEntity.isPresent()) {
            // 购物车中是否已经存在记录
            Optional<CartEntity> optionalCartEntity = this.cartRepository.findCartEntityByProductEntityAndUserEntity(optionalProductEntity.get(), optionalUserEntity.get());
            CartEntity cartEntity = new CartEntity();
            if (optionalCartEntity.isPresent()) {
                cartEntity = optionalCartEntity.get();
                if (count > 0) {
                    cartEntity.setCount(cartEntity.getCount() - count);
                    this.cartRepository.save(cartEntity);
                }else {
                    this.cartRepository.delete(cartEntity);
                }
            }
            cartRecord = new CartRecord();
            cartRecord.setProduct(getProduct(cartEntity));
            cartRecord.setCount(cartEntity.getCount());
        }
        return cartRecord;
    }

    private Product getProduct(CartEntity cartEntity) {
        Product product = new Product();
        BeanUtils.copyProperties(cartEntity.getProductEntity(), product);
        return product;
    }

    /**
     * 通过userId获取用户所有的购物车记录
     *
     * @param userId
     * @return
     */
    @Override
    public List<CartRecord> getAllCartRecordByUserId(Long userId) {
        List<CartRecord> cartRecords = new ArrayList<>();
        Optional<UserEntity> optionalUserEntity = this.userRepository.findById(userId);
        if (optionalUserEntity.isPresent()) {
            List<CartEntity> allByUserEntity = this.cartRepository.getAllByUserEntity(optionalUserEntity.get());
            for (CartEntity cartEntity: allByUserEntity) {
                CartRecord cartRecord = new CartRecord();
                cartRecord.setCount(cartEntity.getCount());
                cartRecord.setId(cartEntity.getId());
                cartRecord.setProduct(getProduct(cartEntity));
                cartRecords.add(cartRecord);
            }
        }
        return cartRecords;
    }

    @Override
    public List<OrderRecord> getAllOrderRecordByUserId(Long userId) {
        List<OrderRecord> orderRecords = new ArrayList<>();
        Optional<UserEntity> optionalUserEntity = this.userRepository.findById(userId);
        if (optionalUserEntity.isPresent()) {
            List<OrderRecordEntity> allByUserEntity = this.orderRecordRepository.findAllByUserEntity(optionalUserEntity.get());
            for (OrderRecordEntity orderRecordEntity: allByUserEntity) {
                List<OrderRecordItemEntity> allByOrderRecordEntity = orderRecordItemRepository.findAllByOrderRecordEntity(orderRecordEntity);
                OrderRecord orderRecord = new OrderRecord();
                List<OrderRecord.OrderRecordItem> orderRecordItemList = new ArrayList<>();
                OrderRecord.OrderRecordItem orderRecordItem = new OrderRecord.OrderRecordItem();
                for (OrderRecordItemEntity orderRecordItemEntity:allByOrderRecordEntity) {
                    orderRecordItem.setItemCount(orderRecordItemEntity.getItemCount());
                    Product product = new Product();
                    BeanUtils.copyProperties(orderRecordItemEntity.getProductEntity(), product);
                    orderRecordItem.setProduct(product);
                    orderRecordItem.setItemCount(orderRecordItemEntity.getItemCount());
                    orderRecordItemList.add(orderRecordItem);
                }
                orderRecord.setId(orderRecordEntity.getId());
                orderRecord.setItems(orderRecordItemList);
                orderRecord.setCreateTime(orderRecordEntity.getCreateTime());
                orderRecords.add(orderRecord);
            }
        }
        return orderRecords;
    }

    @Override
    public boolean changeCartProductCount(Long cartId, Integer count) {
        CartEntity cartEntity = this.cartRepository.getOne(cartId);
        cartEntity.setCount(count);
        this.cartRepository.save(cartEntity);
        return true;
    }

    @Override
    public boolean removeOneProductFromCart(Long userId, Long cartId) {
        CartEntity one = this.cartRepository.getOne(cartId);
        this.cartRepository.delete(one);
        return true;
    }
}
