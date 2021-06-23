package com.atguigu.yygh.user.client;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/6/2110:59
 */

import com.atguigu.yygh.model.user.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/6/21 10:59
 * @updateDate 2021/6/21 10:59
 **/
@FeignClient(value = "service-user")
@Repository
public interface PatientFeignClient {
    //根据就诊人id获取就诊人信息
    @GetMapping("/api/user/patient/inner/get/{id}")
    public Patient getPatientOrder(@PathVariable("id") Long id);
}
