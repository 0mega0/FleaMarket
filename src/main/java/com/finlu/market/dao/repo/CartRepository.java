package com.finlu.market.dao.repo;

import com.finlu.market.dao.entity.CartEntity;
import com.finlu.market.dao.entity.ProductEntity;
import com.finlu.market.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CartRepository extends JpaRepository<CartEntity, Long> {
    /**
     * 查找购物车表中是否已经存在记录
     * @param productEntity
     * @param userEntity
     * @return
     */
    Optional<CartEntity> findCartEntityByProductEntityAndUserEntity(ProductEntity productEntity, UserEntity userEntity);

    List<CartEntity> getAllByUserEntity(UserEntity userEntity);

}
