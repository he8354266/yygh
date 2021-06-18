package com.atguigu.yygh.user.mapper;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/6/1517:25
 */

import com.atguigu.yygh.model.user.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/6/15 17:25
 * @updateDate 2021/6/15 17:25
 **/
@Repository
public interface UserInfoMapper extends BaseMapper<UserInfo> {
}
