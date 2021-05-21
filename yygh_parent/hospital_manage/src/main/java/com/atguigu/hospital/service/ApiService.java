package com.atguigu.hospital.service;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/5/2111:09
 */

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.Map;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/5/21 11:09
 * @updateDate 2021/5/21 11:09
 **/
public interface ApiService {
    String getHoscode();

    String getSignKey();

    JSONObject getHospital();

    boolean saveHospital(String data);

    Map<String, Object> findDepartment(int pageNum, int pageSize);

    boolean saveDepartment(String data);

    boolean removeDepartment(String depcode);

    Map<String, Object> findSchedule(int pageNum, int pageSize);

    boolean saveSchedule(String data);

    boolean removeSchedule(String hosScheduleId);

    void saveBatchHospital() throws IOException;
}
