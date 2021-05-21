package com.atguigu.hospital.controller;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/5/2010:48
 */

import com.atguigu.hospital.mapper.HospitalSetMapper;
import com.atguigu.hospital.model.HospitalSet;
import com.atguigu.hospital.service.ApiService;
import com.atguigu.hospital.util.YyghException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/5/20 10:48
 * @updateDate 2021/5/20 10:48
 **/
@Api(tags = "医院管理接口")
@Controller
@RequestMapping
public class ApiController extends BaseController {
    @Autowired
    private HospitalSetMapper hospitalSetMapper = null;
    @Autowired
    private ApiService apiService = null;

    @RequestMapping("/hospitalSet/index")
    public String getHospitalSet(ModelMap model, RedirectAttributes redirectAttributes) {
        HospitalSet hospitalSet = hospitalSetMapper.selectById(1);
        model.addAttribute("hospitalSet", hospitalSet);
        return "hospitalSet/index";
    }

    @RequestMapping(value = "/hospitalSet/save")
    public String createHospitalSet(ModelMap model, HospitalSet hospitalSet) {
        hospitalSetMapper.updateById(hospitalSet);
        return "redirect:/hospitalSet/index";
    }

    @RequestMapping("/hospital/index")
    public String getHospital(ModelMap model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            HospitalSet hospitalSet = hospitalSetMapper.selectById(1);
            if (hospitalSet == null || StringUtils.isEmpty(hospitalSet.getHoscode())) {
                this.failureMessage("先设置医院code与签名key", redirectAttributes);
                return "redirect:/hospitalSet/index";
            }
            model.addAttribute("hospital", apiService.getHospital());
        } catch (YyghException e) {
            this.failureMessage(e.getMessage(), request);
        } catch (Exception e) {
            e.printStackTrace();
            this.failureMessage("数据异常", request);
        }
        return "hospital/index";
    }

    @RequestMapping(value = "/hospital/create")
    public String createHospital(ModelMap model) {
        return "hospital/create";
    }

    @RequestMapping(value = "/hospital/save", method = RequestMethod.POST)
    public String saveHospital(String data, HttpServletRequest request) {
        try {
            apiService.saveHospital(data);
        } catch (YyghException e) {
            return this.failurePage(e.getMessage(), request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.successPage(null, request);
    }

    @RequestMapping("/department/list")
    public String findDepartment(ModelMap model,
                                 @RequestParam(defaultValue = "1") int pageNum,
                                 @RequestParam(defaultValue = "10") int pageSize,
                                 HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            HospitalSet hospitalSet = hospitalSetMapper.selectById(1);
            if (hospitalSet == null || StringUtils.isEmpty(hospitalSet.getHoscode())) {
                this.failureMessage("先设置医院code与签名key", redirectAttributes);
                return "redirect:/hospitalSet/index";
            }
            model.addAttribute(apiService.findDepartment(pageNum, pageSize));

        } catch (YyghException e) {
            this.failureMessage(e.getMessage(), request);
        } catch (Exception e) {
            this.failurePage("数据异常", request);
        }
        return "department/index";
    }
}
