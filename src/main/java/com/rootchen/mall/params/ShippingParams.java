package com.rootchen.mall.params;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 
 * </p>
 *
 * @author LC
 * @since 2019-06-18
 */
@Data
@TableName("mall_shipping")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShippingParams {

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
