package com.rootchen.mall.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.rootchen.mall.common.mp3.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author LC
 * @since 2019-06-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("mall_shipping")
public class Shipping extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户id")
    private Long userId;

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
