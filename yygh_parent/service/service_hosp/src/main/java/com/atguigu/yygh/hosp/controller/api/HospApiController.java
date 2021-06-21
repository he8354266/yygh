package com.atguigu.yygh.hosp.controller.api;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/6/189:04
 */

import com.atguigu.hospital.util.Result;
import com.atguigu.yygh.hosp.service.DepartmentService;
import com.atguigu.yygh.hosp.service.HospitalService;
import com.atguigu.yygh.hosp.service.HospitalSetService;
import com.atguigu.yygh.hosp.service.ScheduleService;
import com.atguigu.yygh.model.hosp.Hospital;
import com.atguigu.yygh.model.hosp.Schedule;
import com.atguigu.yygh.vo.hosp.DepartmentVo;
import com.atguigu.yygh.vo.hosp.HospitalQueryVo;
import com.atguigu.yygh.vo.hosp.ScheduleOrderVo;
import com.atguigu.yygh.vo.order.SignInfoVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/6/18 9:04
 * @updateDate 2021/6/18 9:04
 **/
@RestController
@RequestMapping("/api/hosp/hospital")
public class HospApiController {
    @Autowired
    private HospitalService hospitalService = null;

    @Autowired
    private DepartmentService departmentService = null;

    @Autowired
    private ScheduleService scheduleService = null;

    @Autowired
    private HospitalSetService hospitalSetService = null;

    @ApiOperation(value = "查询医院列表")
    @GetMapping("findHospList/{page}/{limit}")
    public Result findHospList(@PathVariable Integer page,
                               @PathVariable Integer limit,
                               HospitalQueryVo hospitalQueryVo) {
        Page<Hospital> hospitals = hospitalService.selectHospPage(page, limit, hospitalQueryVo);
        return Result.ok(hospitals);
    }

    @ApiOperation(value = "根据医院名称查询")
    @GetMapping("findByHosName/{hosname}")
    public Result findByHosName(@PathVariable String hosname) {
        List<Hospital> list = hospitalService.findByHosname(hosname);
        return Result.ok(list);
    }

    @ApiOperation(value = "根据医院编号获取科室")
    @GetMapping("department/{hoscode}")
    public Result index(@PathVariable String hoscode) {
        List<DepartmentVo> list = departmentService.findDeptTree(hoscode);
        return Result.ok(list);
    }

    @ApiOperation(value = "根据医院编号获取医院预约挂号详情")
    @GetMapping("findHospDetail/{hoscode}")
    public Result item(@PathVariable String hoscode) {
        Map<String, Object> map = hospitalService.item(hoscode);
        return Result.ok(map);
    }

    @ApiOperation(value = "获取可预约排班数据")
    @GetMapping("auth/getBookingScheduleRule/{page}/{limit}/{hoscode}/{depcode}")
    public Result getBookingSchedule(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Integer page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Integer limit,
            @ApiParam(name = "hoscode", value = "医院code", required = true)
            @PathVariable String hoscode,
            @ApiParam(name = "depcode", value = "科室code", required = true)
            @PathVariable String depcode) throws ParseException {
        return Result.ok(scheduleService.getBookingScheduleRule(page, limit, hoscode, depcode));
    }

    @ApiOperation(value = "获取排班数据")
    @GetMapping("auth/findScheduleList/{hoscode}/{depcode}/{workDate}")
    public Result findScheduleList(
            @ApiParam(name = "hoscode", value = "医院code", required = true)
            @PathVariable String hoscode,
            @ApiParam(name = "depcode", value = "科室code", required = true)
            @PathVariable String depcode,
            @ApiParam(name = "workDate", value = "排班日期", required = true)
            @PathVariable String workDate) {
        return Result.ok(scheduleService.getDetailSchedule(hoscode, depcode, workDate));
    }

    @ApiOperation(value = "获取排班id获取排班数据")
    @GetMapping("getSchedule/{scheduleId}")
    public Result getSchedule(@PathVariable String scheduleId) {
        Schedule schedule = scheduleService.getScheduleId(scheduleId);
        return Result.ok(schedule);
    }

    @ApiOperation(value = "根据排班id获取预约下单数据")
    @GetMapping("inner/getScheduleOrderVo/{scheduleId}")
    public ScheduleOrderVo getScheduleOrderVo(
            @ApiParam(name = "scheduleId", value = "排班id", required = true)
            @PathVariable("scheduleId") String scheduleId) {
        return scheduleService.getScheduleOrderVo(scheduleId);
    }

    @ApiOperation(value = "获取医院签名信息")
    @GetMapping("inner/getSignInfoVo/{hoscode}")
    public SignInfoVo getSignInfoVo(
            @ApiParam(name = "hoscode", value = "医院code", required = true)
            @PathVariable("hoscode") String hoscode) {
        return hospitalSetService.getSignInfoVo(hoscode);
    }
}
