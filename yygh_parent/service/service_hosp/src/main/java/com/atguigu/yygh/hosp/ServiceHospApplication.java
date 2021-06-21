package com.atguigu.yygh.hosp;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/5/259:49
 */

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/5/25 9:49
 * @updateDate 2021/5/25 9:49
 **/
@SpringBootApplication
@ComponentScan(basePackages = "com.atguigu.yygh.hosp")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.atguigu")
public class ServiceHospApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceHospApplication.class, args);
    }
}
