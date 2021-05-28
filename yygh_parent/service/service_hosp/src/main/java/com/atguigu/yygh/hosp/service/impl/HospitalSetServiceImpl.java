package com.atguigu.yygh.hosp.service.impl;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/5/2514:24
 */


import com.atguigu.hospital.mapper.HospitalSetMapper;
import com.atguigu.yygh.hosp.mapper.HospitalSetCopyMapper;
import com.atguigu.yygh.hosp.service.HospitalSetService;
import com.atguigu.yygh.model.hosp.HospitalSetCopy;
import com.atguigu.yygh.vo.order.SignInfoVo;
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
        return null;
    }

    @Override
    public SignInfoVo getSignInfoVo(String hoscode) {
        return null;
    }

    @Override
    public List<HospitalSetCopy> list() {
        return hospitalSetCopyMapper.selectList(null);
    }
}
