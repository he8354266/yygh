package com.atguigu.yygh.hosp.mapper;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/5/2510:28
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/5/25 10:28
 * @updateDate 2021/5/25 10:28
 **/
@Repository
@Mapper
public interface ScheduleMapper extends BaseMapper<Scheduled> {
}
