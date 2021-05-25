package com.atguigu.yygh.hosp.mapper;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/5/2510:27
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashSet;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/5/25 10:27
 * @updateDate 2021/5/25 10:27
 **/
@Repository
@Mapper
public interface HospitalSetMapper extends BaseMapper<HashSet> {
}
