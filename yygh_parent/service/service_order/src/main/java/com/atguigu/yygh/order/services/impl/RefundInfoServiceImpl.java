package com.atguigu.yygh.order.services.impl;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/6/817:00
 */

import com.atguigu.yygh.model.order.PaymentInfo;
import com.atguigu.yygh.model.order.RefundInfo;
import com.atguigu.yygh.order.mapper.RefundInfoMapper;
import com.atguigu.yygh.order.services.RefundInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/6/8 17:00
 * @updateDate 2021/6/8 17:00
 **/
@Service
public class RefundInfoServiceImpl extends ServiceImpl<RefundInfoMapper, RefundInfo> implements RefundInfoService {
    //保存退款记录
    @Override
    public RefundInfo saveRefundInfo(PaymentInfo paymentInfo) {
        QueryWrapper<RefundInfo> wrapper = new QueryWrapper<>();
        if (paymentInfo.getOrderId() != null) {
            wrapper.eq("order_id", paymentInfo.getOrderId());
        }
        if (paymentInfo.getPaymentType() != null) {
            wrapper.eq("payment_type", paymentInfo.getPaymentType());
        }
        RefundInfo refundInfo = baseMapper.selectOne(wrapper);
        if (refundInfo != null) {
            return refundInfo;
        }
        //添加记录
        refundInfo = new RefundInfo();
        refundInfo.setCreateTime(new Date());
        refundInfo.setOrderId(paymentInfo.getOrderId());
        refundInfo.setPaymentType(paymentInfo.getPaymentType());
        refundInfo.setOutTradeNo(paymentInfo.getOutTradeNo());
        refundInfo.setSubject(paymentInfo.getSubject());
        refundInfo.setTotalAmount(paymentInfo.getTotalAmount());
        baseMapper.insert(refundInfo);
        return refundInfo;
    }
}
