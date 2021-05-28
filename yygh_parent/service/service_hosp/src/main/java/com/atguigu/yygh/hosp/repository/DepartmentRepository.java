package com.atguigu.yygh.hosp.repository;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/5/2616:40
 */

import com.atguigu.yygh.model.hosp.Department;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/5/26 16:40
 * @updateDate 2021/5/26 16:40
 **/
@Repository
public interface DepartmentRepository extends MongoRepository<Department, String> {
    //上传科室接口
    Department getDepartmentByHoscodeAndDepcode(String hoscode, String depcode);
}
