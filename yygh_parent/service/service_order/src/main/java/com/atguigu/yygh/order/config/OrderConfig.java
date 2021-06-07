package com.atguigu.yygh.order.config;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/6/49:10
 */

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/6/4 9:10
 * @updateDate 2021/6/4 9:10
 **/
@Configuration
@MapperScan("com.atguigu.yygh.order.mapper")
public class OrderConfig {
}
