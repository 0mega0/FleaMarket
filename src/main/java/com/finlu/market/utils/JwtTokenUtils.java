package com.finlu.market.utils;

import com.auth0.jwt.algorithms.Algorithm;
import com.finlu.market.domain.User;
import com.auth0.jwt.JWT;

import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;


public class JwtTokenUtils {
    public static String getToken(User user) {
        String token = "";
        token = JWT.create().withAudience(user.getId().toString())
                .sign(Algorithm.HMAC256(user.getUsername()));
        return token;
    }

    public static Integer getUserId(HttpServletRequest request) {
        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        return Integer.valueOf(JWT.decode(jwtToken).getAudience().get(0));
    }
}
