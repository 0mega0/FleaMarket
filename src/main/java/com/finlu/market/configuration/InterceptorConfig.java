package com.finlu.market.configuration;

import com.finlu.market.interceptor.AdminInterceptor;
import com.finlu.market.interceptor.AuthenticationInterceptor;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 为请求添加拦截器
        registry.addInterceptor(authenticationInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(adminInterceptor()).addPathPatterns("/admin/**");
    }

    /**
     * 将 AuthenticationInterceptor 对象注入到IOC容器中
     * @return
     */
    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }


    @Bean
    public AdminInterceptor adminInterceptor() {
        return new AdminInterceptor();
    }
}
