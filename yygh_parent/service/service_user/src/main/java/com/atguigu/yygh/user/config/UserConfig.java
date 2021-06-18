package com.atguigu.yygh.user.config;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/6/169:14
 */

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/6/16 9:14
 * @updateDate 2021/6/16 9:14
 **/
@Configuration
@MapperScan("com.atguigu.yygh.user.mapper")
public class UserConfig {
}
