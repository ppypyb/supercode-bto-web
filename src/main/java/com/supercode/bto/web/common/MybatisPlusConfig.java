package com.supercode.bto.web.common;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * describe:
 *
 * @author xiaosen
 * @date 2020/9/2 10:45
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.supercode.bto.web.mapper")
public class MybatisPlusConfig
{

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}

