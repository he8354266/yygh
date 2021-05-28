package com.atguigu.yygh.hosp.repository;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/5/2714:54
 */

import com.atguigu.yygh.model.hosp.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/5/27 14:54
 * @updateDate 2021/5/27 14:54
 **/
@Repository
public interface ScheduleRepository extends MongoRepository<Schedule, String> {
    //根据医院编号 和 排班编号查询
    Schedule getScheduleByHoscodeAndHosScheduleId(String hoscode, String hosScheduleId);

    //根据医院编号 、科室编号和工作日期，查询排班详细信息
    List<Schedule> findScheduleByHoscodeAndDepcodeAndWorkDate(String hoscode, String depcode, Date toDate);
}
