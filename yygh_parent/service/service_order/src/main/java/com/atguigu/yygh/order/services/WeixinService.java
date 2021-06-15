package com.atguigu.yygh.order.services;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/6/910:30
 */

import java.util.Map;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/6/9 10:30
 * @updateDate 2021/6/9 10:30
 **/
public interface WeixinService {
    //生成微信支付二维码
    Map createNative(Long orderId) throws Exception;

    //调用微信接口实现支付状态查询
    Map<String, String> queryPayStatus(Long orderId) throws Exception;

    /***
     * 退款
     */
    Boolean refund(Long orderId);
}
