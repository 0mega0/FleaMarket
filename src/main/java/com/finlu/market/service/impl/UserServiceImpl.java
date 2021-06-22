package com.finlu.market.service.impl;

import com.finlu.market.controller.admin.param.UserReq;
import com.finlu.market.controller.param.LoginReq;
import com.finlu.market.controller.param.RegisterReq;
import com.finlu.market.dao.entity.UserEntity;
import com.finlu.market.dao.repo.UserRepository;
import com.finlu.market.domain.User;
import com.finlu.market.exceptions.AuthException;
import com.finlu.market.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    /**
     * 创建一个User对象
     *
     * @param registerReq 请求表单参数对象
     * @return {User}
     */
    @Override
    public User register(RegisterReq registerReq) throws AuthException {
        Optional<UserEntity> optionalUserEntity = this.userRepository.findByUsernameEquals(registerReq.getUsername());
        if (optionalUserEntity.isPresent()) {
            throw new AuthException(AuthException.USER_IS_EXISTS);
        }
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(registerReq, userEntity);
        // 普通用户
        userEntity.setAdmin(false);
        // 未激活
        userEntity.setActive(false);
        userEntity.setPassword(encoder.encode(userEntity.getPassword()));
        userEntity = this.userRepository.save(userEntity);
        User user = new User();
        BeanUtils.copyProperties(userEntity, user);
        return user;
    }


    @Override
    public User login(LoginReq loginReq) throws AuthException {
        Optional<UserEntity> optionalUserEntity = this.userRepository.findByUsernameEquals(loginReq.getUsername());
        if (optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();
            if (!encoder.matches(loginReq.getPassword(), userEntity.getPassword())) {
                // 密码不正确
                throw new AuthException(AuthException.PASSWORD_NOT_MATCH_USERNAME);
            }
            if (!userEntity.isActive()) {
                throw new AuthException(AuthException.USER_IS_NOT_ACTIVE);
            }
            User user = new User();
            BeanUtils.copyProperties(userEntity, user);
            return user;
        } else {
            // 用户不存在
            throw new AuthException(AuthException.USER_NOT_EXISTS);
        }
    }

    @Override
    public List<User> getAllUser() {
        List<User> users = new ArrayList<>();
        List<UserEntity> userEntities = this.userRepository.findAll();
        for (UserEntity userEntity: userEntities) {
            User user = new User();
            BeanUtils.copyProperties(userEntity, user);
            users.add(user);
        }
        return users;
    }

    @Override
    public User updateUser(UserReq userReq) {
        Optional<UserEntity> optionalUserEntity = this.userRepository.findByUsernameEquals(userReq.getUsername());
        if (optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();
            BeanUtils.copyProperties(userReq, userEntity);
            this.userRepository.save(userEntity);
            User user = new User();
            BeanUtils.copyProperties(userEntity, user);
            return user;
        } else {
            return null;
        }
    }

    @Override
    public boolean delUserById(Long userId) {
        this.userRepository.deleteById(userId);
        return true;
    }

}

