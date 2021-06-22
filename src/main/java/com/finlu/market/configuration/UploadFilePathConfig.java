package com.finlu.market.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

//参考： https://blog.csdn.net/weixin_43817709/article/details/100859132

public class UploadFilePathConfig implements WebMvcConfigurer {
    @Value("${uploadFile.path}")
    private String uploadFilePath;

    @Value("${uploadFile.staticAccessPath")
    private String staticAccessPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(staticAccessPath).addResourceLocations("file:" + uploadFilePath);
    }
}
