package com.rootchen.mall.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.rootchen.mall.common.mp3.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

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
@TableName("mall_order")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单号")
    private Long orderNo;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("购物车id")
    private Long shippingId;

    @ApiModelProperty("实际付款金额,单位是元,保留两位小数")
    private BigDecimal payment;

    @ApiModelProperty("支付类型,1-在线支付")
    private Integer paymentType;

    @ApiModelProperty("运费,单位是元")
    private Integer postage;

    @ApiModelProperty("订单状态:0-已取消-10-未付款，20-已付款，40-已发货，50-交易成功，60-交易关闭")
    private Integer status;

    @ApiModelProperty("支付时间")
    private Date paymentTime;

    @ApiModelProperty("发货时间")
    private Date sendTime;

    @ApiModelProperty("交易完成时间")
    private Date endTime;

    @ApiModelProperty("交易关闭时间")
    private Date closeTime;


}
