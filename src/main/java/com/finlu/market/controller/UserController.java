package com.finlu.market.controller;

import com.finlu.market.controller.param.LoginReq;
import com.finlu.market.controller.param.RegisterReq;
import com.finlu.market.domain.ResultData;
import com.finlu.market.domain.User;
import com.finlu.market.exceptions.AuthException;
import com.finlu.market.service.UserService;
import com.finlu.market.utils.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@Slf4j
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 用户注册
     *
     * @param registerReq 用户注册参数
     * @return
     */
    @PostMapping("/register")
    public ResultData register(@RequestBody RegisterReq registerReq) {
        try {
            User user = this.userService.register(registerReq);
            return ResultData.success(user);
        }catch (AuthException e) {
            return ResultData.fail(e.getCodeMeaning());
        }
    }

    /**
     * 用户登录
     *
     * @param loginReq 用户登录参数
     * @return
     */
    @RequestMapping("/login")
    public ResultData login(@RequestBody LoginReq loginReq) {
        try{
            User user = this.userService.login(loginReq);
            String token = JwtTokenUtils.getToken(user);
            Map<String, Object> map = new HashMap<>(16);
            map.put(HttpHeaders.AUTHORIZATION, token);
            map.put("user", user);
            return ResultData.success(map, "登录成功");
        } catch (AuthException e) {
            return ResultData.fail(e.getCodeMeaning());
        }
    }

}