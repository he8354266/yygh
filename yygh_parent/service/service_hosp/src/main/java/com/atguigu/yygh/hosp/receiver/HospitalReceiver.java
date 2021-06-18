package com.atguigu.yygh.hosp.receiver;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/6/1515:06
 */

import com.atguigu.yygh.vo.msm.MsmVo;
import com.atguigu.yygh.vo.order.OrderMqVo;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.atguigu.yygh.model.hosp.Schedule;
import com.atguigu.yygh.hosp.service.ScheduleService;
import com.atguigu.common.rabbit.constant.MqConst;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/6/15 15:06
 * @updateDate 2021/6/15 15:06
 **/
@Component
public class HospitalReceiver {
    @Autowired
    private ScheduleService scheduleService = null;
    @Autowired
    private RabbitTemplate rabbitTemplate = null;

    @RabbitListener(bindings = @QueueBinding(value = @Queue(
            value = MqConst.QUEUE_ORDER, durable = "true"),
            exchange = @Exchange(value = MqConst.EXCHANGE_DIRECT_ORDER),
            key = {MqConst.ROUTING_ORDER}
    ))
    public void receiver(OrderMqVo orderMqVo, Message message, Channel channel) {
        if (orderMqVo.getReservedNumber() != null) {
            //下单成功更新预约数
            Schedule schedule = scheduleService.getScheduleId(orderMqVo.getScheduleId());
            schedule.setReservedNumber(orderMqVo.getReservedNumber());
            schedule.setAvailableNumber(orderMqVo.getAvailableNumber());
            scheduleService.update(schedule);

        } else {
            //取消预约更新预约数
            Schedule schedule = scheduleService.getScheduleId(orderMqVo.getScheduleId());
            int availableNumber = schedule.getAvailableNumber().intValue() + 1;
            schedule.setAvailableNumber(availableNumber);
            scheduleService.update(schedule);
        }

        //发送短信
        MsmVo msmVo = orderMqVo.getMsmVo();
        if (msmVo != null) {
            rabbitTemplate.convertAndSend(MqConst.EXCHANGE_DIRECT_MSM,MqConst.ROUTING_MSM_ITEM, msmVo);
        }
    }
}
