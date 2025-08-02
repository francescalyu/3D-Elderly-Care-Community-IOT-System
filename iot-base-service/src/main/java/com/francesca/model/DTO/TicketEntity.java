package com.francesca.model.DTO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.Date;

@Data
@TableName("ticket")
public class TicketEntity {

    @TableId(type = IdType.AUTO)
    private BigInteger id;

    @ApiModelProperty("工单名称")
    private String name;

    @ApiModelProperty("工单别名")
    private String alias;

    /**
     * 1-auto  2-manual
     */
    @ApiModelProperty("类型 1-自动工单  2-手动 ")
    private Integer tktype;

    /**
     * ticket inform description
     */
    @ApiModelProperty("通知内容 ")
    private String inform;

    /**
     * link warn record
     */
    @ApiModelProperty("关联告警号 ")
    private BigInteger warnrecord;

    /**
     * link people
     */
    @ApiModelProperty("关联维护人员号 ")
    private Integer peopleid;

    /**
     * 1=open  2=inform  3=close
     */
    @ApiModelProperty("工单状态  1-打开  2-已通知 3-关闭")
    private Integer status;

    /**
     * exec product
     */
    @ApiModelProperty("操作产品id ")
    private Integer prodid;

    /**
     * exec define dev
     */
    @ApiModelProperty("操作设备id ")
    private Integer devid;

    /**
     * cmd point
     */
    @ApiModelProperty("设备点ID ")
    private Integer pid;

    @ApiModelProperty("下发指令值 ")
    private Float cmdvalue;

    /**
     * snd cmd count
     */
    @ApiModelProperty("工单超时时间 ")
    private Integer expire;

    /**
     * cmd exec result
     */

    private String result;

    @ApiModelProperty("工单创建时间 ")
    private Date createtime;


    private Date informtime;

    private Date closetime;
}
