package com.atguigu.yygh.cmn;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/6/117:24
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @description zkjy
 * @updateRemark
 * @author zkjyCoding
 * @updateUser
 * @createDate 2021/6/1 17:24
 * @updateDate 2021/6/1 17:24     
 * @version 1.0
 **/
@SpringBootApplication
@ComponentScan(basePackages = {"com.atguigu.yygh.cmn"})
@EnableDiscoveryClient
public class ServiceCmnApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceCmnApplication.class, args);
    }
}
