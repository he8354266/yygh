package com.atguigu.hospital.service.impl;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/6/2117:44
 */

import com.alibaba.fastjson.JSONObject;
import com.atguigu.hospital.mapper.OrderInfoMapper;
import com.atguigu.hospital.mapper.ScheduleMapper;
import com.atguigu.hospital.model.OrderInfo;
import com.atguigu.hospital.model.Patient;
import com.atguigu.hospital.model.Schedule;
import com.atguigu.hospital.service.HospitalService;
import com.atguigu.hospital.util.ResultCodeEnum;
import com.atguigu.hospital.util.YyghException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/6/21 17:44
 * @updateDate 2021/6/21 17:44
 **/
@Service
@Slf4j
public class HospitalServiceImpl implements HospitalService {
    @Autowired
    private ScheduleMapper scheduleMapper = null;
    @Autowired
    private OrderInfoMapper orderInfoMapper = null;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> submitOrder(Map<String, Object> paramMap) {
        log.info(JSONObject.toJSONString(paramMap));
        String hoscode = (String) paramMap.get("hoscode");
        String depcode = (String) paramMap.get("depcode");
        String hosScheduleId = (String) paramMap.get("hosScheduleId");
        String reserveDate = (String) paramMap.get("reserveDate");
        String reserveTime = (String) paramMap.get("reserveTime");
        String amount = (String) paramMap.get("amount");

        Schedule schedule = this.getSchedule("1L");
        if (schedule == null) {
            throw new YyghException(ResultCodeEnum.DATA_ERROR);
        }
        if (!schedule.getHoscode().equals(hoscode) || !schedule.getDepcode().equals(depcode) || schedule.getAmount().toString().equals(amount)) {
            throw new YyghException(ResultCodeEnum.DATA_ERROR);
        }
        //???????????????
        Patient patient = JSONObject.parseObject(JSONObject.toJSONString(paramMap), Patient.class);
        log.info(JSONObject.toJSONString(patient));
        //?????????????????????
        Long patientId = this.savePatient(patient);

        Map<String, Object> resultMap = new HashMap<>();
        int availableNumber = schedule.getAvailableNumber().intValue() - 1;
        if (availableNumber > 0) {
            schedule.setAvailableNumber(availableNumber);
            scheduleMapper.updateById(schedule);

            //??????????????????
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setPatientId(patientId);
            orderInfo.setScheduleId(1L);
            int number = schedule.getReservedNumber().intValue() - schedule.getAvailableNumber().intValue();
            orderInfo.setNumber(number);
            orderInfo.setAmount(new BigDecimal(amount));
            String fetchTime = "0".equals(reserveDate) ? "09:30???" : "14:00???";
            orderInfo.setFetchTime(reserveTime + fetchTime);
            orderInfo.setFetchAddress("??????9?????????");

            //?????? ?????????
            orderInfo.setOrderStatus(0);
            orderInfoMapper.insert(orderInfo);

            resultMap.put("resultCode", "0000");
            resultMap.put("resultMsg", "????????????");
            //??????????????????????????????????????????????????????
            resultMap.put("hosRecordId", orderInfo.getId());
            //????????????
            resultMap.put("number", number);
            //????????????
            resultMap.put("fetchTime", reserveDate + "09:00???");
            //????????????
            resultMap.put("fetchAddress", "??????114??????");
            //??????????????????
            resultMap.put("reservedNumber", schedule.getReservedNumber());
            //?????????????????????
            resultMap.put("availableNumber", schedule.getAvailableNumber());
        } else {
            throw new YyghException(ResultCodeEnum.DATA_ERROR);
        }
        return resultMap;
    }

    @Override
    public void updatePayStatus(Map<String, Object> paramMap) {
        String hoscode = (String) paramMap.get("hoscode");
        String hosRecordId = (String) paramMap.get("hosRecordId");


        OrderInfo orderInfo = orderInfoMapper.selectById(hosRecordId);
        if (orderInfo == null) {
            throw new YyghException(ResultCodeEnum.DATA_ERROR);
        }
        //?????????
        orderInfo.setOrderStatus(1);
        orderInfo.setPayTime(new Date());
        orderInfoMapper.updateById(orderInfo);

    }

    @Override
    public void updateCancelStatus(Map<String, Object> paramMap) {
        String hoscode = (String) paramMap.get("hoscode");
        String hosRecordId = (String) paramMap.get("hosRecordId");

        OrderInfo orderInfo = orderInfoMapper.selectById(hosRecordId);
        if (orderInfo == null) {
            throw new YyghException(ResultCodeEnum.DATA_ERROR);
        }
        //?????????
        orderInfo.setOrderStatus(-1);
        orderInfo.setQuitTime(new Date());
        orderInfoMapper.updateById(orderInfo);
    }

    /**
     * ???????????????????????????
     *
     * @param patient
     */
    private Long savePatient(Patient patient) {
        return 1L;
    }

    private Schedule getSchedule(String frontSchId) {
        return scheduleMapper.selectById(frontSchId);
    }
}
