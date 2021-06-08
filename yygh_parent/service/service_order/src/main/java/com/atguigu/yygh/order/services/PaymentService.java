package com.atguigu.yygh.order.services;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/6/817:33
 */

import com.atguigu.yygh.model.order.OrderInfo;
import com.atguigu.yygh.model.order.PaymentInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/6/8 17:33
 * @updateDate 2021/6/8 17:33
 **/
public interface PaymentService extends IService<PaymentInfo> {

    //向支付记录表添加信息
    void savePaymentInfo(OrderInfo order, Integer status);

    //更新订单状态
    void paySuccess(String out_trade_no, Map<String, String> resultMap);

    /**
     * 获取支付记录
     *
     * @param orderId
     * @param paymentType
     * @return
     */
    PaymentInfo getPaymentInfo(Long orderId, Integer paymentType);
}
