package com.atguigu.yygh.cmn.config;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/6/117:34
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
 * @createDate 2021/6/1 17:34
 * @updateDate 2021/6/1 17:34
 **/
@Configuration
@MapperScan("com.atguigu.yygh.cmn.mapper")
public class CmnConfig {
    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
