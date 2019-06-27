package com.rootchen.mall.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.rootchen.mall.common.mp3.BaseModel;
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

    private Long userId;

    private Long orderNo;

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品图片地址
     */
    private String productImage;

    /**
     * 生成订单时的商品单价，单位是元,保留两位小数
     */
    private BigDecimal currentUnitPrice;

    /**
     * 商品数量
     */
    private Integer quantity;

    /**
     * 商品总价,单位是元,保留两位小数
     */
    private BigDecimal totalPrice;


}
