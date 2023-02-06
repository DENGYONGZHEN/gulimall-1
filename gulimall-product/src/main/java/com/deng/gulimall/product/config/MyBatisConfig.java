package com.deng.gulimall.product.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Classname MyBatisConfig
 * @Description MyBatis-plus的配置类
 * @Version 1.0.0
 * @Date 2023/2/5 12:38
 * @Created by helloDeng
 */
@Configuration
@EnableTransactionManagement   //开启事务
@MapperScan("com.deng.gulimall.product.dao")
public class MyBatisConfig {
    //引入分页插件
    @Bean
    public PaginationInterceptor paginationInterceptor(){

        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        //设置请求的页面大于最大页后操作，true调回到主页，false继续请求，默认false
        paginationInterceptor.setOverflow(true);
        //设置最大单页限制数量，默认500条，-1不受限制
        paginationInterceptor.setLimit(1000);
        return paginationInterceptor;
    }
}
