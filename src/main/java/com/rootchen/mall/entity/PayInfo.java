package com.rootchen.mall.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.rootchen.mall.common.mp3.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author LC
 * @since 2019-06-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("mall_pay_info")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayInfo extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("订单号")
    private Long orderNo;

    @ApiModelProperty("支付平台:1-支付宝,2-微信")
    private Integer payPlatform;

    @ApiModelProperty("支付宝支付流水号")
    private String platformNumber;

    @ApiModelProperty("支付宝支付状态")
    private String platformStatus;


}
