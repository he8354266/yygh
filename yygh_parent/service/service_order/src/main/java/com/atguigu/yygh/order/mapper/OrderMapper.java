package com.atguigu.yygh.order.mapper;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/6/317:28
 */

import com.atguigu.yygh.model.order.OrderInfo;
import com.atguigu.yygh.vo.order.OrderCountQueryVo;
import com.atguigu.yygh.vo.order.OrderCountVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/6/3 17:28
 * @updateDate 2021/6/3 17:28
 **/
@Repository
public interface OrderMapper extends BaseMapper<OrderInfo> {
    //查询预约统计数据的方法
    List<OrderCountVo> selectOrderCount(@Param("vo") OrderCountQueryVo orderCountQueryVo);
}
