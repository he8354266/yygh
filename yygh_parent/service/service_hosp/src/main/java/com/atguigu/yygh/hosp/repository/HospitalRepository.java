package com.atguigu.yygh.hosp.repository;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/6/810:31
 */

import com.atguigu.yygh.model.hosp.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/6/8 10:31
 * @updateDate 2021/6/8 10:31
 **/
@Repository
public interface HospitalRepository extends MongoRepository<Hospital, String> {
    //判断是否存在数据
    Hospital getHospitalByHoscode(String hoscode);

    //根据医院名称查询
    List<Hospital> findHospitalByHosnameLike(String hosname);
}
