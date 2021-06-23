package com.atguigu.hospital.controller;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/6/2216:31
 */

import com.atguigu.hospital.service.ApiService;
import com.atguigu.hospital.service.HospitalService;
import com.atguigu.hospital.util.HttpRequestHelper;
import com.atguigu.hospital.util.Result;
import com.atguigu.hospital.util.ResultCodeEnum;
import com.atguigu.hospital.util.YyghException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/6/22 16:31
 * @updateDate 2021/6/22 16:31
 **/
@Api(tags = "医院管理接口")
@RestController
public class HospitalController {
    @Autowired
    private HospitalService hospitalService = null;
    @Autowired
    private ApiService apiService = null;

    /**
     * 预约下单
     *
     * @param request
     * @return
     */
    @PostMapping("/order/submitOrder")
    public Result AgreeAccountLendProject(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
            Map<String, Object> resultMap = hospitalService.submitOrder(paramMap);
            return Result.ok(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail().message(e.getMessage());
        }
    }


    /**
     * 更新支付状态
     *
     * @param request
     * @return
     */
    @PostMapping("/order/updatePayStatus")
    public Result updatePayStatus(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
            if (HttpRequestHelper.isSignEquals(paramMap, apiService.getSignKey())) {
                throw new YyghException(ResultCodeEnum.SIGN_ERROR);
            }
            hospitalService.updatePayStatus(paramMap);
            return Result.ok();


        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail().message(e.getMessage());
        }
    }

    /**
     * 更新取消预约状态
     *
     * @param request
     * @return
     */
    @PostMapping("/order/updateCancelStatus")
    public Result updateCancelStatus(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());

            hospitalService.updatePayStatus(paramMap);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail().message(e.getMessage());
        }
    }
}
