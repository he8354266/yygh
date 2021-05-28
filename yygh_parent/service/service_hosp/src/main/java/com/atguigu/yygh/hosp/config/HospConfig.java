package com.atguigu.yygh.hosp.config;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/5/2510:32
 */

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/5/25 10:32
 * @updateDate 2021/5/25 10:32
 **/
@Configuration
@MapperScan("com.atguigu.yygh.hosp.mapper")
public class HospConfig {
    /**
     * 分页插件
     */
//    @Bean
//    public PaginationInterceptor paginationInterceptor() {
//        return new PaginationInterceptor();
//    }
}
