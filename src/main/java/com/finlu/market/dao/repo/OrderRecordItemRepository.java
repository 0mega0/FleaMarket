package com.finlu.market.dao.repo;

import com.finlu.market.dao.entity.OrderRecordEntity;
import com.finlu.market.dao.entity.OrderRecordItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRecordItemRepository extends JpaRepository<OrderRecordItemEntity, Long> {
    List<OrderRecordItemEntity> findAllByOrderRecordEntity(OrderRecordEntity orderRecordEntity);
}
