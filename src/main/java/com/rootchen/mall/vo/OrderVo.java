package com.rootchen.mall.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @description: 返回前端订单信息
 * @author: LiChen
 * @create: 2019-07-08 14:59
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderVo {


    @ApiModelProperty("订单号")
    private Long orderNo;

    @ApiModelProperty("实际付款金额,单位是元,保留两位小数")
    private BigDecimal payment;

    @ApiModelProperty("支付类型,1-在线支付")
    private Integer paymentType;

    @ApiModelProperty("支付描述")
    private String paymentTypeDesc;

    @ApiModelProperty("运费,单位是元")
    private Integer postage;

    @ApiModelProperty("订单状态:0-已取消-10-未付款，20-已付款，40-已发货，50-交易成功，60-交易关闭")
    private Integer status;

    @ApiModelProperty("订单状态描述")
    private String statusDesc;

    @ApiModelProperty("支付时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh",timezone = "GMT-8")
    private Date paymentTime;

    @ApiModelProperty("发货时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh",timezone = "GMT-8")
    private Date sendTime;

    @ApiModelProperty("交易完成时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh",timezone = "GMT-8")
    private Date endTime;

    @ApiModelProperty("交易关闭时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh",timezone = "GMT-8")
    private Date closeTime;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh",timezone = "GMT-8")
    private Date createTime;

    @ApiModelProperty("订单明细")
    List<OrderItemVo> orderItemList;

    @ApiModelProperty("图片")
    private String imageHost;

    @ApiModelProperty("购物车id")
    private Long shippingId;

    @ApiModelProperty("收货人姓名")
    private String receiverName;

    @ApiModelProperty("收获地址详情")
    private ShippingVo shippingVo;


}
