package com.rootchen.mall.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @description: 分页列表显示内容
 * @author: LiChen
 * @create: 2019-06-04 09:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductListVo {
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("分类id,对应mall_category表的主键")
    private Integer categoryId;

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("商品副标题")
    private String subtitle;

    @ApiModelProperty("产品主图,url相对地址")
    private String mainImage;

    @ApiModelProperty("价格,单位-元保留两位小数")
    private BigDecimal price;

    @ApiModelProperty("图片服务器url前缀")
    private String imageHost;
}