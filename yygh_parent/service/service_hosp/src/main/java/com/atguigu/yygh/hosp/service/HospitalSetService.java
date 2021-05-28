package com.atguigu.yygh.hosp.service;

import com.atguigu.hospital.model.HospitalSet;
import com.atguigu.yygh.model.hosp.HospitalSetCopy;
import com.atguigu.yygh.vo.order.SignInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HospitalSetService extends IService<HospitalSetCopy> {

    //2 根据传递过来医院编码，查询数据库，查询签名
    String getSignKey(String hoscode);

    //获取医院签名信息
    SignInfoVo getSignInfoVo(String hoscode);

    List<HospitalSetCopy> list();
}
