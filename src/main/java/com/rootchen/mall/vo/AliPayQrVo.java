package com.rootchen.mall.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 返回支付宝二维码链接
 * @author: LiChen
 * @create: 2019-06-27 22:02
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AliPayQrVo {
    @ApiModelProperty("订单号")
    private String orderNumber;
    @ApiModelProperty("支付宝返回二维码链接")
    private String qrPath;
}
