package com.atguigu.hospital;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/5/2010:22
 */

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/5/20 10:22
 * @updateDate 2021/5/20 10:22
 **/
@SpringBootApplication
public class ManageApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageApplication.class, args);
    }
}
