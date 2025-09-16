package com.francesca.model.VO.about;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("关于 - 作者对象")
public class AuthorVO {

    @ApiModelProperty("作者照片")
    private String picUrl;

    @ApiModelProperty("作者介绍")
    private String author;
}
