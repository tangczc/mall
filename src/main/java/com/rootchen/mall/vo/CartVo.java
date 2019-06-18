package com.rootchen.mall.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @description:
 * @author: LiChen
 * @create: 2019-03-04 14:14
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CartVo {

    @ApiModelProperty("购物车列表")
    private List<CartProductVo> cartProductVoList;

    @ApiModelProperty("购物车总价")
    private BigDecimal cartTotalPrice;

    @ApiModelProperty("全选")
    private Boolean allChecked;

    @ApiModelProperty("主图")
    private String imageHost;


}