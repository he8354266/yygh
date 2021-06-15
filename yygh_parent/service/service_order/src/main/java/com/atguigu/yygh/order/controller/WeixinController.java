package com.atguigu.yygh.order.controller;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/6/414:43
 */

import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.order.services.PaymentService;
import com.atguigu.yygh.order.services.WeixinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/6/4 14:43
 * @updateDate 2021/6/4 14:43
 **/
@RestController
@RequestMapping("/api/order/weixin")
public class WeixinController {
    @Autowired
    private WeixinService weixinService = null;
    @Autowired
    private PaymentService paymentService = null;

    //生成微信支付二维码
    @GetMapping("/createNative/{orderId}")
    public Result createNative(@PathVariable Long orderId) throws Exception {
        Map map = weixinService.createNative(orderId);
        return Result.ok(map);
    }

    //查询支付状态
    @GetMapping("/queryPayStatus/{orderId}")
    public Result queryPayStatus(@PathVariable Long orderId) throws Exception {
        //调用微信接口实现支付状态查询
        Map<String, String> resultMap = weixinService.queryPayStatus(orderId);
        if (resultMap == null) {
            return Result.fail().message("支付出错");
        }
        if (resultMap.get("trade_state").equals("SUCCESS")) {
            //更新订单状态
            String out_trade_no = resultMap.get("out_trade_no");//订单编码
            paymentService.paySuccess(out_trade_no, resultMap);
            return Result.ok().message("支付成功");
        }
        return Result.ok().message("支付中");
    }
}
