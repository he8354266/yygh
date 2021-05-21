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
import com.atguigu.hospital.util.HttpRequestHelper;
import com.atguigu.hospital.util.YyghException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
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

    private String getApiUrl() {
        HospitalSet hospitalSet = hospitalSetMapper.selectById(1);
        return hospitalSet.getApiUrl();
    }

    @Override
    public String getSignKey() {
        HospitalSet hospitalSet = hospitalSetMapper.selectById(1);
        return hospitalSet.getSignKey();
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
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("hoscode", this.getHoscode());
        paramMap.put("page", pageNum);
        paramMap.put("limit", pageSize);
        paramMap.put("timestamp", HttpRequestHelper.getTimestamp());
        paramMap.put("sign", HttpRequestHelper.getSign(paramMap, this.getSignKey()));
        JSONObject response = HttpRequestHelper.sendRequest(paramMap, this.getApiUrl() + "/api/hosp/department/list");
        if (response != null && response.getIntValue("code") == 200) {
            JSONObject jsonObject = response.getJSONObject("data");
            result.put("total", jsonObject.getLong("totalElements"));
            result.put("pageNum", pageNum);
            result.put("list", jsonObject.getJSONArray("content"));
        } else {
            throw new YyghException(response.getString("message"), 201);
        }
        return result;
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
