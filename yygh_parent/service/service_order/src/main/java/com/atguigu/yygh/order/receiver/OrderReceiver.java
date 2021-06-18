package com.atguigu.yygh.order.receiver;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/6/1515:55
 */

import com.atguigu.common.rabbit.constant.MqConst;
import com.atguigu.yygh.order.services.OrderService;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/6/15 15:55
 * @updateDate 2021/6/15 15:55
 **/
@Component
public class OrderReceiver {
    @Autowired
    private OrderService orderService = null;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConst.QUEUE_TASK_8, durable = "true"),
            exchange = @Exchange(value = MqConst.EXCHANGE_DIRECT_TASK),
            key = {MqConst.ROUTING_TASK_8}
    ))
    public void patientTips(Message message, Channel channel) {
        orderService.patientTips();
    }
}
