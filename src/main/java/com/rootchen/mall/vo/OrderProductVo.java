package com.rootchen.mall.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @description: 购物车明细
 * @author: LiChen
 * @create: 2019-07-10 13:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderProductVo {

    @ApiModelProperty("订单明细列表")
    private List<OrderItemVo> orderItemVoList;

    @ApiModelProperty("总价")
    private BigDecimal productTotalPrice;

    @ApiModelProperty("主图地址")
    private String imageHost;

}
