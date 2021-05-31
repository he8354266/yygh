package com.atguigu.yygh.hosp.controller;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/5/2710:45
 */

import com.atguigu.hospital.util.Result;
import com.atguigu.yygh.hosp.service.ScheduleService;
import com.atguigu.yygh.model.hosp.Schedule;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @createDate 2021/5/27 10:45
 * @updateDate 2021/5/27 10:45
 **/
@RestController
@RequestMapping("/admin/hosp/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService = null;

    //根据医院编号 和 科室编号 ，查询排班规则数据
    @ApiOperation(value = "查询排班规则数据")
    @GetMapping("getScheduleRule/{page}/{limit}/{hoscode}/{depcode}")
    public Result getScheduleRule(@PathVariable long page,
                                  @PathVariable long limit,
                                  @PathVariable String hoscode,
                                  @PathVariable String depcode) throws ParseException {
        Map<String, Object> ruleSchedule = scheduleService.getRuleSchedule(page, limit, hoscode, depcode);
        return Result.ok(ruleSchedule);
    }

    @GetMapping("remove/{hoscode}/{hosScheduleId}")
    public Result getScheduleRule(
            @PathVariable String hoscode,
            @PathVariable String hosScheduleId) {
        scheduleService.remove(hoscode, hosScheduleId);
        return Result.ok();
    }

    @GetMapping("ruleSchedule/{hoscode}/{depcode}")
    public Result getRuleSchedule(
            @PathVariable String hoscode,
            @PathVariable String depcode) throws ParseException {
        Map<String, Object> ruleSchedule = scheduleService.getRuleSchedule(1, 10, hoscode, depcode);
        return Result.ok(ruleSchedule);
    }

    @GetMapping("detailSchedule/{hoscode}/{depcode}/{workDate}")
    public Result getDetailSchedule(
            @PathVariable String hoscode,
            @PathVariable String depcode, @PathVariable String workDate) throws ParseException {
        List<Schedule> detailSchedule = scheduleService.getDetailSchedule(hoscode, depcode, workDate);
        return Result.ok(detailSchedule);
    }


    @GetMapping("bookingScheduleRule/{hoscode}/{depcode}")
    public Result getBookingScheduleRule(
            @PathVariable String hoscode,
            @PathVariable String depcode) throws ParseException {
        Map<String, Object> bookingScheduleRule = scheduleService.getBookingScheduleRule(1, 10, hoscode, depcode);
        return Result.ok(bookingScheduleRule);
    }

}
