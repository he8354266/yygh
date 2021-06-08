package com.atguigu.yygh.order.services;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/6/816:55
 */

import com.atguigu.yygh.model.order.PaymentInfo;
import com.atguigu.yygh.model.order.RefundInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/6/8 16:55
 * @updateDate 2021/6/8 16:55
 **/
public interface RefundInfoService extends IService<RefundInfo> {

    /**
     * 保存退款记录
     *
     * @param paymentInfo
     */
    RefundInfo saveRefundInfo(PaymentInfo paymentInfo);
}
