package com.atguigu.yygh.msm;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/6/79:47
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

import javax.sql.DataSource;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/6/7 9:47
 * @updateDate 2021/6/7 9:47
 **/
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
})
@EnableDiscoveryClient
//@ComponentScan(basePackages = {"com.atguigu"})
public class ServiceMsmApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceMsmApplication.class, args);
    }
}
