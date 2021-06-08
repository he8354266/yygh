package com.atguigu.yygh.msm.receiver;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/6/710:00
 */

import com.atguigu.common.rabbit.constant.MqConst;
import com.atguigu.yygh.msm.service.MsmService;
import com.atguigu.yygh.vo.msm.MsmVo;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.plugin2.message.Message;

import java.nio.channels.Channel;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/6/7 10:00
 * @updateDate 2021/6/7 10:00
 **/
@Component
public class MsmReceiver {
    @Autowired
    private MsmService service;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConst.QUEUE_MSM_ITEM, durable = "true"),
            exchange = @Exchange(value = MqConst.EXCHANGE_DIRECT_MSM),
            key = {MqConst.ROUTING_MSM_ITEM}
    ))
    public void send(MsmVo msmVo, Message message, Channel channel) {
        service.send(msmVo);
    }
}
