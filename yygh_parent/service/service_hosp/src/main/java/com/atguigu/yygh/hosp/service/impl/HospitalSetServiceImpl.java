package com.atguigu.yygh.hosp.service.impl;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/5/2514:24
 */


import com.atguigu.hospital.mapper.HospitalSetMapper;
import com.atguigu.hospital.util.ResultCodeEnum;
import com.atguigu.hospital.util.YyghException;
import com.atguigu.yygh.hosp.mapper.HospitalSetCopyMapper;
import com.atguigu.yygh.hosp.service.HospitalSetService;
import com.atguigu.yygh.model.hosp.HospitalSetCopy;
import com.atguigu.yygh.vo.order.SignInfoVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/5/25 14:24
 * @updateDate 2021/5/25 14:24
 **/
@Service
public class HospitalSetServiceImpl extends ServiceImpl<HospitalSetCopyMapper, HospitalSetCopy> implements HospitalSetService {
    @Autowired
    private HospitalSetCopyMapper hospitalSetCopyMapper = null;

    @Override
    public String getSignKey(String hoscode) {
        QueryWrapper<HospitalSetCopy> wrapper = new QueryWrapper<>();
        wrapper.eq("hoscode", hoscode);
        HospitalSetCopy hospitalSetCopy = hospitalSetCopyMapper.selectOne(wrapper);
        return hospitalSetCopy.getSignKey();
    }

    @Override
    public SignInfoVo getSignInfoVo(String hoscode) {
        QueryWrapper<HospitalSetCopy> wrapper = new QueryWrapper<>();
        wrapper.eq("hoscode", hoscode);
        HospitalSetCopy hospitalSetCopy = hospitalSetCopyMapper.selectOne(wrapper);
        if (hospitalSetCopy == null) {
            throw new YyghException(ResultCodeEnum.HOSPITAL_OPEN);
        }
        SignInfoVo signInfoVo = new SignInfoVo();
        signInfoVo.setApiUrl(hospitalSetCopy.getApiUrl());
        signInfoVo.setSignKey(hospitalSetCopy.getSignKey());
        return signInfoVo;
    }

    @Override
    public List<HospitalSetCopy> list() {
        return hospitalSetCopyMapper.selectList(null);
    }
}
