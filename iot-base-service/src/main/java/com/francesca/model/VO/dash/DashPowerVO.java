package com.francesca.model.VO.dash;

/**
 * @Author francesca

 * 2025-05-15
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@ApiModel("仪表板 - 能源")
public class DashPowerVO {

    @ApiModelProperty(" 当日用电量  " )
    private String todayPower;

    @ApiModelProperty(" 实时功率 " )
    private String currentPower;

    @ApiModelProperty("节能率" )
    private String powerSave;

    @ApiModelProperty("碳减排" )
    private String co2;

    @ApiModelProperty("等效植树" )
    private String tree;

    @ApiModelProperty(" 电量趋势 ")
    private List<DashPowerCountVO> powerList;

    @ApiModelProperty(" 空调用电量  " )
    private String acPower;

    @ApiModelProperty(" 照明用电量  " )
    private String lightPower;

    @ApiModelProperty(" 厨电用电量  " )
    private String electPower;

    public  void   clear(){
        todayPower = "0";
        currentPower = "0 ";
        acPower = "0";
        lightPower = "0";
        electPower = "0";
    }


}
