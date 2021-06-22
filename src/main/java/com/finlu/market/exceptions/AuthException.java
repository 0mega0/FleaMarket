package com.finlu.market.exceptions;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthException extends Exception {
    public static final int USER_NOT_EXISTS = 1;
    public static final int PASSWORD_NOT_MATCH_USERNAME = 2;
    public static final int USER_IS_NOT_ACTIVE = 3;
    public static final int USER_IS_EXISTS = 4;
    private int code;

    public String getCodeMeaning() {
        switch (code) {
            case USER_NOT_EXISTS:
                return "用户不存在";
            case PASSWORD_NOT_MATCH_USERNAME:
                return "用户名和密码不匹配";
            case USER_IS_NOT_ACTIVE:
                return "用户未激活";
            case USER_IS_EXISTS:
                return "用户名已存在";
            default:
                return "";

        }
    }
}
