package com.atguigu.hospital.service.impl;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/5/2111:11
 */

import com.alibaba.fastjson.JSONObject;
import com.atguigu.hospital.mapper.HospitalSetMapper;
import com.atguigu.hospital.mapper.ScheduleMapper;
import com.atguigu.hospital.model.HospitalSet;
import com.atguigu.hospital.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/5/21 11:11
 * @updateDate 2021/5/21 11:11
 **/
@Service
public class ApiServiceImpl implements ApiService {
    @Autowired
    private ScheduleMapper scheduleMapper = null;
    @Autowired
    private HospitalSetMapper hospitalSetMapper = null;

    @Value("classpath:hospital.json")
    private Resource hospitalResource;

    @Override
    public String getHoscode() {
        HospitalSet hospitalSet = hospitalSetMapper.selectById(1);
        return hospitalSet.getHoscode();
    }

    @Override
    public String getSignKey() {
        return null;
    }

    @Override
    public JSONObject getHospital() {
        return null;
    }

    @Override
    public boolean saveHospital(String data) {
        return false;
    }

    @Override
    public Map<String, Object> findDepartment(int pageNum, int pageSize) {
        return null;
    }

    @Override
    public boolean saveDepartment(String data) {
        return false;
    }

    @Override
    public boolean removeDepartment(String depcode) {
        return false;
    }

    @Override
    public Map<String, Object> findSchedule(int pageNum, int pageSize) {
        return null;
    }

    @Override
    public boolean saveSchedule(String data) {
        return false;
    }

    @Override
    public boolean removeSchedule(String hosScheduleId) {
        return false;
    }

    @Override
    public void saveBatchHospital() throws IOException {

    }
}
