package com.atguigu.hospital.config;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/5/2110:25
 */

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/5/21 10:25
 * @updateDate 2021/5/21 10:25
 **/
@EnableTransactionManagement
@Configuration
@MapperScan("com.atguigu.hospital.mapper")
public class MybatisPlusConfig {
    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
