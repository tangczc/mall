package com.rootchen.mall.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @description: 订单列表
 * @author: LiChen
 * @create: 2019-07-10 14:46
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderListVo {


    @ApiModelProperty("订单状态")
    private Integer status;

    @ApiModelProperty("订单明细")
    private List<OrderItemVo> orderItemVoList;

    @ApiModelProperty("订单总价")
    private BigDecimal payment;

    @ApiModelProperty("主图地址")
    private String imageHost;

}
