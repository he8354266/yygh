package com.atguigu.yygh.model.hosp;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/5/2515:21
 */

import com.atguigu.yygh.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description zkjy
 * @updateRemark
 * @author zkjyCoding
 * @updateUser
 * @createDate 2021/5/25 15:21
 * @updateDate 2021/5/25 15:21     
 * @version 1.0
 **/
@Data
@ApiModel(description = "医院设置")
@TableName("hospital_set_copy")
public class HospitalSetCopy extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "医院名称")
    @TableField("hosname")
    private String hosname;

    @ApiModelProperty(value = "医院编号")
    @TableField("hoscode")
    private String hoscode;

    @ApiModelProperty(value = "api基础路径")
    @TableField("api_url")
    private String apiUrl;

    @ApiModelProperty(value = "签名秘钥")
    @TableField("sign_key")
    private String signKey;

    @ApiModelProperty(value = "联系人姓名")
    @TableField("contacts_name")
    private String contactsName;

    @ApiModelProperty(value = "联系人手机")
    @TableField("contacts_phone")
    private String contactsPhone;

    @ApiModelProperty(value = "状态")
    @TableField("status")
    private Integer status;
}
