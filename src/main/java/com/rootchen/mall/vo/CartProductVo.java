package com.rootchen.mall.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @description:
 * @author: LiChen
 * @create: 2019-06-18 15:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartProductVo {

    @ApiModelProperty("购物车id")
    private Long id;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("商品id")
    private Long productId;

    @ApiModelProperty("购物车中此商品的数量")
    private Integer quantity;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("商品副标题")
    private String productSubtitle;

    @ApiModelProperty("商品主图")
    private String productMainImage;

    @ApiModelProperty("商品价格")
    private BigDecimal productPrice;

    @ApiModelProperty("商品状态.1-在售 2-下架 3-删除")
    private Integer productStatus;

    @ApiModelProperty("总价")
    private BigDecimal productTotalPrice;

    @ApiModelProperty("商品库存")
    private Integer productStock;

    @ApiModelProperty("此商品是否勾选")
    private Integer productChecked;

    @ApiModelProperty("限制数量")
    private String limitQuantity;
}