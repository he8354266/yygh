package com.atguigu.yygh.hosp.controller;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/5/2514:17
 */

import com.atguigu.hospital.util.MD5;
import com.atguigu.hospital.util.Result;
import com.atguigu.yygh.hosp.mapper.HospitalSetCopyMapper;
import com.atguigu.yygh.hosp.service.HospitalService;
import com.atguigu.yygh.hosp.service.HospitalSetService;

import com.atguigu.yygh.model.hosp.HospitalSetCopy;
import com.atguigu.yygh.vo.hosp.HospitalQueryVo;
import com.atguigu.yygh.vo.hosp.HospitalSetQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javafx.application.HostServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.atguigu.hospital.model.HospitalSet;

import java.util.List;
import java.util.Random;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/5/25 14:17
 * @updateDate 2021/5/25 14:17
 **/
@Api(tags = "医院设置管理")
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
public class HospitalSetController {

    @Autowired
    private HospitalSetService hospitalSetService = null;

    //1 查询医院设置表所有信息
    @ApiOperation(value = "获取所有医院设置")
    @GetMapping("findAll")
    public Result findAllHospitalSet() {

        try {
            //调用service的方法
            List<HospitalSetCopy> hospitalSetCopies = hospitalSetService.list();
            return Result.ok(hospitalSetCopies);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("数据为空");
    }

    //2 逻辑删除医院设置
    @ApiOperation(value = "逻辑删除医院设置")
    @DeleteMapping("{id}")
    public Result removeHospSet(@PathVariable("id") Long id) {
        boolean flag = hospitalSetService.removeById(id);
        if (flag) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    //3 条件查询带分页
    @PostMapping("findPageHospSet/{current}/{limit}")
    public Result findPageHospSet(@PathVariable long current,
                                  @PathVariable long limit,
                                  @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo) {
        Page<HospitalSetCopy> page = new Page<>(current, limit);
        QueryWrapper<HospitalSetCopy> wrapper = new QueryWrapper<>();
        String hosname = hospitalSetQueryVo.getHosname();
        String hoscode = hospitalSetQueryVo.getHoscode();
        if (!StringUtils.isEmpty(hosname)) {
            wrapper.like("hosname", hosname);
        }
        if (!StringUtils.isEmpty(hoscode)) {
            wrapper.eq("hoscode", hoscode);
        }
        //调用方法实现分页查询
        IPage<HospitalSetCopy> pageHospitalSet = hospitalSetService.page(page, wrapper);
        return Result.ok(pageHospitalSet);
    }

    //4 添加医院设置
    @PostMapping("saveHospitalSet")
    public Result saveHospitalSet(@RequestBody HospitalSetCopy hospitalSetCopy) {
        //设置状态 1 使用 0 不能使用
        hospitalSetCopy.setStatus(1);
        //签名密钥
        Random random = new Random();
        hospitalSetCopy.setSignKey(MD5.encrypt(System.currentTimeMillis() + "" + random.nextInt(1000)));
        System.out.println(hospitalSetCopy.getSignKey());
        boolean save = hospitalSetService.save(hospitalSetCopy);
        if (save) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @GetMapping("getHospSet/{id}")
    public Result getHospSet(@PathVariable Long id) {
        HospitalSetCopy hospitalSetCopy = hospitalSetService.getById(id);
        return Result.ok(hospitalSetCopy);
    }

    @PostMapping("updateHospitalSet")
    public Result updateHospitalSet(@RequestBody HospitalSetCopy hospitalSetCopy) {
        boolean flag = hospitalSetService.updateById(hospitalSetCopy);
        if (flag) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @DeleteMapping("batchRemove")
    public Result batchRemoveHospitalSet(@RequestBody List<Long> idList) {
        boolean flag = hospitalSetService.removeByIds(idList);
        if (flag) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @PutMapping("lockHospitalSet/{id}/{status}")
    public Result lockHospitalSet(@PathVariable Long id,
                                  @PathVariable Integer status) {
        HospitalSetCopy hospitalSetCopy = hospitalSetService.getById(id);
        //设置状态
        hospitalSetCopy.setStatus(status);
        //调用方法
        hospitalSetService.updateById(hospitalSetCopy);
        return Result.ok();
    }

    @PostMapping("sendKey/{id}")
    public Result lockHospitalSet(@PathVariable Long id) {
        HospitalSetCopy hospitalSet = hospitalSetService.getById(id);
        String signKey = hospitalSet.getSignKey();
        String hoscode = hospitalSet.getHoscode();
        //TODO 发送短信
        return Result.ok();
    }
}
