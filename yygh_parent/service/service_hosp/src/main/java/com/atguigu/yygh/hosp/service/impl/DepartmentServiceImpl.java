package com.atguigu.yygh.hosp.service.impl;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/5/2511:15
 */

import com.alibaba.fastjson.JSONObject;
import com.atguigu.yygh.hosp.repository.DepartmentRepository;
import com.atguigu.yygh.hosp.service.DepartmentService;
import com.atguigu.yygh.model.hosp.Department;
import com.atguigu.yygh.vo.hosp.DepartmentQueryVo;
import com.atguigu.yygh.vo.hosp.DepartmentVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.stereotype.Service;

import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/5/25 11:15
 * @updateDate 2021/5/25 11:15
 **/
@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private MongoTemplate mongoTemplate = null;
    @Autowired
    private DepartmentRepository departmentRepository = null;

    @Override
    public void save(Map<String, Object> paramMap) {
        //paramMap 转换department对象
        String paramMapString = JSONObject.toJSONString(paramMap);
        Department department = JSONObject.parseObject(paramMapString, Department.class);
        //根据医院编号和科室编号查询
        Department departmentExit = departmentRepository.getDepartmentByHoscodeAndDepcode(department.getHoscode(), department.getDepcode());
        if (departmentExit != null) {
            departmentExit.setUpdateTime(new Date());
            departmentExit.setIsDeleted(0);
            departmentRepository.save(departmentExit);
        } else {
            department.setCreateTime(new Date());
            department.setUpdateTime(new Date());
            department.setIsDeleted(0);
            departmentRepository.save(department);
        }

    }

    @Override
    public org.springframework.data.domain.Page<Department> findPageDepartment(int page, int limit, DepartmentQueryVo departmentQueryVo) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        //创建example对象
        Department department = new Department();
        BeanUtils.copyProperties(departmentQueryVo, department);
        department.setIsDeleted(0);


        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true);
        Example<Department> example = Example.of(department, matcher);

        org.springframework.data.domain.Page<Department> all = departmentRepository.findAll(example, pageable);
        return all;
    }

    @Override
    public void remove(String hoscode, String depcode) {
        Department department = departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);
        if (department != null) {
            departmentRepository.deleteById(department.getId());
        }
    }

    @Override
    public List<DepartmentVo> findDeptTree(String hoscode) {
        //创建list集合，用于最终数据封装
        List<DepartmentVo> result = new ArrayList<>();

        //根据医院编号，查询医院所有科室信息
        Criteria criteria = Criteria.where("hoscode").is(hoscode);
        Query query = new Query(criteria);
        List<Department> departmentList = mongoTemplate.find(query, Department.class);

        Map<String, List<Department>> collect = departmentList.stream().collect(Collectors.groupingBy(Department::getBigcode));
        for (Map.Entry<String, List<Department>> entry : collect.entrySet()) {
            //大科室编号
            String bigCode = entry.getKey();
            //获取大科室编号对应的全部数据
            List<Department> departmentList1 = entry.getValue();
            //封装大科室
            DepartmentVo departmentVo = new DepartmentVo();
            departmentVo.setDepcode(bigCode);
            departmentVo.setDepname(departmentList1.get(0).getBigname());

            //封装小科室
            List<DepartmentVo> children = new ArrayList<>();
            for (Department department : departmentList1) {
                DepartmentVo departmentVo1 = new DepartmentVo();
                departmentVo1.setDepcode(department.getDepcode());
                departmentVo1.setDepname(department.getDepname());
                children.add(departmentVo1);
            }
            departmentVo.setChildren(children);
            result.add(departmentVo);
        }
        return result;
    }

    //根据科室编号，和医院编号，查询科室名称
    @Override
    public String getDepName(String hoscode, String depcode) {
        Criteria criteria = Criteria.where("hoscode").is(hoscode).and("depcode").is(depcode);
        Query query = new Query(criteria);
        Department department = mongoTemplate.findOne(query, Department.class);
        if (department != null) {
            return department.getDepname();
        }
        return null;
    }

    @Override
    public Department getDepartment(String hoscode, String depcode) {
        Criteria criteria = Criteria.where("hoscode").is(hoscode).and("depcode").is(depcode);
        Query query = new Query(criteria);
        Department department = mongoTemplate.findOne(query, Department.class);
        return department;
    }
}
