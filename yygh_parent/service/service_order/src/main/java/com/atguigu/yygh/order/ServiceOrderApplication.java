package com.atguigu.yygh.order;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/6/315:52
 */

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
 * @createDate 2021/6/3 15:52
 * @updateDate 2021/6/3 15:52
 **/
@SpringBootApplication
@ComponentScan(basePackages = {"com.atguigu.yygh.order"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.atguigu"})
public class ServiceOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceOrderApplication.class, args);
    }
}
