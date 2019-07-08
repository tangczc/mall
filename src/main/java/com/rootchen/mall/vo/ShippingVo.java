package com.rootchen.mall.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 返给前端收获地址
 * @author: LiChen
 * @create: 2019-07-08 15:14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShippingVo {


    @ApiModelProperty("收货姓名")
    private String receiverName;

    @ApiModelProperty("收货固定电话")
    private String receiverPhone;

    @ApiModelProperty("收货移动电话")
    private String receiverMobile;

    @ApiModelProperty("省份")
    private String receiverProvince;

    @ApiModelProperty("城市")
    private String receiverCity;

    @ApiModelProperty("区/县")
    private String receiverDistrict;

    @ApiModelProperty("详细地址")
    private String receiverAddress;

    @ApiModelProperty("邮编")
    private String receiverZip;

}
