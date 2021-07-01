package com.finlu.market.dao.entity;

import com.finlu.market.dao.entity.base.BaseEntity;
import com.finlu.market.domain.Product;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * 如果不加入Getter和Setter注解，在使用BeanUtils进行属性拷贝的时候回出现问题
 * 
 */
@Entity
@Table(name = "t_user")
@Getter
@Setter
public class UserEntity extends BaseEntity {
    /**
     * 用户名
     */
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * 密码
     */
    @Column
    private String password;

    /**
     * 用户状态
     */
    @Column(nullable = false)
    private boolean isAdmin;

    @Column(name = "is_active")
    private boolean isActive;

}
