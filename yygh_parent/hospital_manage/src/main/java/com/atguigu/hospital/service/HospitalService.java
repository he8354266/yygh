package com.atguigu.hospital.service;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/6/2117:43
 */

import java.util.Map;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/6/21 17:43
 * @updateDate 2021/6/21 17:43
 **/
public interface HospitalService {
    /**
     * 预约下单
     *
     * @param paramMap
     * @return
     */
    Map<String, Object> submitOrder(Map<String, Object> paramMap);

    /**
     * 更新支付状态
     *
     * @param paramMap
     */
    void updatePayStatus(Map<String, Object> paramMap);

    /**
     * 更新取消预约状态
     *
     * @param paramMap
     */
    void updateCancelStatus(Map<String, Object> paramMap);
}
