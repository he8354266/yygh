package com.atguigu.yygh.order.client;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/6/1515:02
 */

import com.atguigu.yygh.vo.order.OrderCountQueryVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/6/15 15:02
 * @updateDate 2021/6/15 15:02
 **/
@FeignClient(value = "service-order")
@Repository
public interface OrderFeignClient {
    /**
     * 根据排班id获取预约下单数据
     */
    @PostMapping("/api/order/orderInfo/inner/getCountMap")
    public Map<String, Object> getCountMap(@RequestBody OrderCountQueryVo orderCountQueryVo);
}
