package com.finlu.market.dao.repo;

import com.finlu.market.dao.entity.CartEntity;
import com.finlu.market.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    /**
     * 通过username查找用户
     * @param username 用户名
     * @return 用户实体
     */
    Optional<UserEntity> findByUsernameEquals(String username);

    /**
     * @param userId
     * @return
     */
    @Query(value = "select c.user_id, c.product_id, c.count from cart c where c.user_id=(:user_id)", nativeQuery = true)
    List<Map<String, CartEntity>> getAllCartRecord(@Param("user_id") Long userId);
}
