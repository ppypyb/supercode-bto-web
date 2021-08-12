package com.supercode.bto.web.common;

import com.supercode.bto.web.interceptor.AuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author pengyongbo
 * @version 1.0
 * @description: TODO 新建token拦截器
 * @date 2021/7/8 19:45
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Value("${imagesPath}")
    private String imagesPath = "D:/image/";
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/**");    // 拦截所有请求，通过判断是否有 @LoginRequired 注解 决定是否需要登录
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /** 和页面有关的静态目录都放在项目的static目录下 **/
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        /** 上传的图片在D盘下的img目录下，访问路径如：http://localhost:8081/image/1.jpg
        其中image表示访问的前缀。"file:D:/img/"是文件真实的存储路径 **/
        registry.addResourceHandler("/images/**").addResourceLocations(imagesPath);

    }

    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();// 自己写的拦截器
    }
}
