package com.atguigu.yygh.vo.task;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/6/1514:04
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/6/15 14:04
 * @updateDate 2021/6/15 14:04
 **/
@Data
@ApiModel(description = "就医提醒")
public class TaskVo implements Serializable {
    @ApiModelProperty(value = "就医提醒")
    private String remind;


}
