package com.atguigu.yygh.hosp.service.impl;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/5/2810:58
 */

import com.atguigu.yygh.hosp.service.HospitalService;
import com.atguigu.yygh.model.hosp.Hospital;
import com.atguigu.yygh.vo.hosp.HospitalQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

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

    private Query getQuery(Criteria criteria) {
        Query query = new Query(criteria);
        return query;
    }

    @Override
    public void save(Map<String, Object> paramMap) {

    }

    @Override
    public Hospital getByHoscode(String hoscode) {
        return null;
    }

    @Override
    public Page<Hospital> selectHospPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo) {
        return null;
    }

    @Override
    public void updateStatus(String id, Integer status) {

    }

    @Override
    public Map<String, Object> getHospById(String id) {
        return null;
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
        return null;
    }

    @Override
    public Map<String, Object> item(String hoscode) {
        return null;
    }
}
