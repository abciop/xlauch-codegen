package com.xlauch.generator.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * <p>
 * 类描述 : 静态资源访问注册，处理静态资源不能访问
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2023/5/8 14:37
 */
@Configuration
public class WebMVCConfig extends WebMvcConfigurationSupport {



    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/xlauch-codegen/**")
                .addResourceLocations("classpath:/static/xlauch-codegen/");
    }
}