package com.atguigu.yygh.hosp.service.impl;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/5/2511:15
 */

import com.atguigu.yygh.hosp.service.DepartmentService;
import com.atguigu.yygh.model.hosp.Department;
import com.atguigu.yygh.vo.hosp.DepartmentQueryVo;
import com.atguigu.yygh.vo.hosp.DepartmentVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Example;
import org.springframework.data.domain.*;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    @Override
    public void save(Map<String, Object> paramMap) {

    }

    @Override
    public Page<Department> findPageDepartment(int page, int limit, DepartmentQueryVo departmentQueryVo) {
        return null;
    }

    @Override
    public void remove(String hoscode, String depcode) {

    }

    @Override
    public List<DepartmentVo> findDeptTree(String hoscode) {
        //创建list集合，用于最终数据封装
//        List<DepartmentVo> result = new ArrayList<>();
//
//        //根据医院编号，查询所有医院信息
//        Department departmentQuery = new Department();
//        departmentQuery.setHoscode(hoscode);
//        Example example = Example.of(departmentQuery);
        return null;
    }

    @Override
    public String getDepName(String hoscode, String depcode) {
        return null;
    }

    @Override
    public Department getDepartment(String hoscode, String depcode) {
        return null;
    }
}
