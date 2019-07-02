package com.rootchen.mall.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.rootchen.mall.common.mp3.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * <p>
 *
 * </p>
 *
 * @author LC
 * @since 2019-06-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("mall_order_item")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItem extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户id")
    private Long userId;

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


}
