package com.atguigu.yygh.hosp.service.impl;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/5/2810:58
 */

import com.alibaba.fastjson.JSONObject;
import com.atguigu.yygh.cmn.client.DictFeignClient;
import com.atguigu.yygh.hosp.repository.HospitalRepository;
import com.atguigu.yygh.hosp.service.HospitalService;
import com.atguigu.yygh.model.hosp.Hospital;
import com.atguigu.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/5/28 10:58
 * @updateDate 2021/5/28 10:58
 **/
@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private MongoTemplate mongoTemplate = null;
    @Autowired
    private HospitalRepository hospitalRepository = null;
    @Autowired
    private DictFeignClient dictFeignClient = null;

    private Query getQuery(Criteria criteria) {
        Query query = new Query(criteria);
        return query;
    }

    @Override
    public void save(Map<String, Object> paramMap) {
        String mapString = JSONObject.toJSONString(paramMap);
        Hospital hospital = JSONObject.parseObject(mapString, Hospital.class);

        //判断是否存在数据
        String hoscode = hospital.getHoscode();
        Criteria criteria = Criteria.where("hoscode").is(hoscode);
        Query query = new Query(criteria);
        Hospital hospitalExist = mongoTemplate.findOne(query, Hospital.class);

        //如果存在，进行修改
        if (hospitalExist != null) {
            hospital.setStatus(hospitalExist.getStatus());
            hospital.setCreateTime(hospitalExist.getCreateTime());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
            mongoTemplate.save(hospital);
        } else {
            hospital.setStatus(0);
            hospital.setCreateTime(new Date());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
            mongoTemplate.save(hospital);
        }
    }

    @Override
    public Hospital getByHoscode(String hoscode) {
        Criteria criteria = Criteria.where("hoscode").is(hoscode);
        Hospital hospital = mongoTemplate.findOne(this.getQuery(criteria), Hospital.class);
        if (hospital != null) {
            return hospital;
        }
        return null;
    }

    @Override
    public Page<Hospital> selectHospPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo) {

        return null;
    }

    @Override
    public void updateStatus(String id, Integer status) {
        //根据id查询医院信息
        Criteria criteria = Criteria.where("id").is(id);
        Query query = new Query(criteria);
        Hospital hospital = mongoTemplate.findOne(query, Hospital.class);
        //设置修改的值
        hospital.setStatus(status);
        hospital.setUpdateTime(new Date());
        mongoTemplate.save(hospital);
    }

    @Override
    public Map<String, Object> getHospById(String id) {
        Map<String, Object> result = new HashMap<>();
        Hospital hospital = this.setHospitalHosType(hospitalRepository.findById(id).get());
        result.put("hospital", hospital);
        result.put("bookingRule", hospital.getBookingRule());
        hospital.setBookingRule(null);
        return result;
    }

    @Override
    public String getHospName(String hoscode) {
        Criteria criteria = Criteria.where("hoscode").is(hoscode);
        Hospital hospital = mongoTemplate.findOne(this.getQuery(criteria), Hospital.class);
        if (hospital != null) {
            return hospital.getHosname();
        }
        return null;
    }

    @Override
    public List<Hospital> findByHosname(String hosname) {
        return hospitalRepository.findHospitalByHosnameLike(hosname);
    }

    @Override
    public Map<String, Object> item(String hoscode) {
        Map<String, Object> result = new HashMap<>();
        //医院详情
        Hospital hospital = this.setHospitalHosType(this.getByHoscode(hoscode));
        result.put("hospital", hospital);
        //预约规则
        result.put("bookingRule", hospital.getBookingRule());
        hospital.setBookingRule(null);
        return result;
    }

    //获取查询list集合，遍历进行医院等级封装
    private Hospital setHospitalHosType(Hospital hospital) {
        //根据dictCode和value获取医院等级名称
        String hostypeString = dictFeignClient.getName("Hostype", hospital.getHostype());
        //查询省市区
        String provinceString = dictFeignClient.getName(hospital.getProvinceCode());
        String cityString = dictFeignClient.getName(hospital.getCityCode());
        String districtString = dictFeignClient.getName(hospital.getDistrictCode());


        hospital.getParam().put("fullAddress", provinceString + cityString + districtString);
        hospital.getParam().put("hostypeString", hostypeString);
        return hospital;
    }
}
