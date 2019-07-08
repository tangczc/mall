package com.rootchen.mall.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description:
 * @author: LiChen
 * @create: 2019-07-08 15:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemVo {

    @ApiModelProperty("订单号")
    private Long orderNo;

    @ApiModelProperty("商品id")
    private Long productId;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("商品图片地址")
    private String productImage;

    @ApiModelProperty("生成订单时的商品单价，单位是元,保留两位小数")
    private BigDecimal currentUnitPrice;

    @ApiModelProperty("商品数量")
    private Integer quantity;

    @ApiModelProperty("商品总价,单位是元,保留两位小数")
    private BigDecimal totalPrice;

    @ApiModelProperty("订单创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh",timezone = "GMT-8")
    private Date createTime;


}
