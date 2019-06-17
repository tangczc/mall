package com.rootchen.mall.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.rootchen.mall.common.mp3.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author LC
 * @since 2019-06-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("mall_cart")
public class Cart extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 是否选择,1=已勾选,0=未勾选
     */
    private Integer checked;


}
