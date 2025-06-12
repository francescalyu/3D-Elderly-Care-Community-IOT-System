package com.francesca.model.DTO;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author francesca

 * 2025-06-12
 */

@Data
@TableName("people")
public class PeopleEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 出生年份
     */
    @ApiModelProperty(" 出生年 " )
    private String birth;

    /**
     * 性别（1-男，2-女）
     */
    @ApiModelProperty(" 性别（1-男，2-女）" )
    private Integer sex;

    /**
     * 人员类型（1-租户，2-医生，3-社区）
     */
    @ApiModelProperty(" （1-租户，2-医生，3-社区）" )
    private Integer type;

    /**
     * 别名（中文）
     */
    @ApiModelProperty(" 状态 " )
    private String alias;

    /**
     * 别名（英文）
     */
    @ApiModelProperty(" 状态英文 " )
    private String aliasen;

    /**
     * 手机号
     */
    private String cellphone;

    /**
     * 通知方式（1-短信，2-微信）
     */
    @ApiModelProperty(" 通知方式（1-短信，2-微信） " )
    private Integer inform;

    /**
     * 关联租户ID
     */
    private Integer link;

    /**
     * 描述信息
     */
    private String description;




}
