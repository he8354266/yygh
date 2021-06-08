package com.atguigu.yygh.msm.service;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/6/710:01
 */

import com.atguigu.yygh.vo.msm.MsmVo;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/6/7 10:01
 * @updateDate 2021/6/7 10:01
 **/
public interface MsmService {
    //发送手机验证码
    boolean send(String phone, String code);

    //mq使用发送短信
    boolean send(MsmVo msmVo);
}
