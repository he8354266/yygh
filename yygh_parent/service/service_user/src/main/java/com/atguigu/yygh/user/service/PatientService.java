package com.atguigu.yygh.user.service;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/6/1517:14
 */

import com.atguigu.yygh.model.user.Patient;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/6/15 17:14
 * @updateDate 2021/6/15 17:14
 **/
public interface PatientService extends IService<Patient> {
    //获取就诊人列表
    List<Patient> findAllUserId(Long userId);

    //根据id获取就诊人信息
    Patient getPatientId(Long id);
}
