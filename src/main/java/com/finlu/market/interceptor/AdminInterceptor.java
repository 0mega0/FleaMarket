package com.finlu.market.interceptor;

import com.finlu.market.annotation.RequiredAdmin;
import com.finlu.market.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;


public class AdminInterceptor implements HandlerInterceptor {
    private static final String ADMIN_LOGIN_URL = "/admin/login";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            // 是否为admin才能登陆的视图
            if (method.isAnnotationPresent(RequiredAdmin.class)) {
                User user = (User) request.getSession().getAttribute("user");
                if (user.isAdmin()) {
                    return true;
                } else {
                    response.setStatus(403);
                    return false;
                }
            }
        }

        return true;
    }

//    public boolean hasPermission() {
//
//    }
}
