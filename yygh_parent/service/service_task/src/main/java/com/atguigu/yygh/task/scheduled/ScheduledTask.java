package com.atguigu.yygh.task.scheduled;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/6/1510:51
 */

import com.alibaba.fastjson.JSONObject;
import com.atguigu.common.rabbit.constant.MqConst;
import com.atguigu.yygh.vo.msm.MsmVo;
import com.atguigu.yygh.vo.order.OrderMqVo;
import com.atguigu.yygh.vo.task.TaskVo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/6/15 10:51
 * @updateDate 2021/6/15 10:51
 **/
@Component
@EnableScheduling
public class ScheduledTask {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Scheduled(cron = "0/30 * * * * ?")
    public void taskPatient() {
        TaskVo taskVo = new TaskVo();
        taskVo.setRemind("=====就医提醒====");
        String jsonString = JSONObject.toJSONString(taskVo);
        rabbitTemplate.convertAndSend(MqConst.EXCHANGE_DIRECT_TASK, MqConst.ROUTING_TASK_8, jsonString);
    }
}
