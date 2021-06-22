package com.finlu.market.service;

import com.finlu.market.controller.admin.param.UserReq;
import com.finlu.market.controller.param.LoginReq;
import com.finlu.market.controller.param.RegisterReq;
import com.finlu.market.dao.entity.UserEntity;
import com.finlu.market.domain.CartRecord;
import com.finlu.market.domain.User;
import com.finlu.market.exceptions.AuthException;

import java.util.List;



public interface UserService {
    User register(RegisterReq registerReq) throws AuthException;

    User login(LoginReq loginReq) throws AuthException;

    List<User> getAllUser();

    User updateUser(UserReq userReq);

    boolean delUserById(Long userId);
}
