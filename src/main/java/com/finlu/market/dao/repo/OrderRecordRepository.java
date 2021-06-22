package com.finlu.market.dao.repo;

import com.finlu.market.dao.entity.OrderRecordEntity;
import com.finlu.market.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRecordRepository extends JpaRepository<OrderRecordEntity, Long> {
    List<OrderRecordEntity> findAllByUserEntity(UserEntity userEntity);
}
