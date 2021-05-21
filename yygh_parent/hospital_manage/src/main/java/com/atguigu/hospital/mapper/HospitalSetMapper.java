package com.atguigu.hospital.mapper;

import com.atguigu.hospital.model.HospitalSet;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface HospitalSetMapper extends BaseMapper<HospitalSet> {

}
