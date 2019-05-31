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
 * @create: 2019-05-31 12:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDetailVo {

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

    @ApiModelProperty("图片地址,json格式,扩展用")
    private String subImages;

    @ApiModelProperty("商品详情")
    private String detail;

    @ApiModelProperty("价格,单位-元保留两位小数")
    private BigDecimal price;

    @ApiModelProperty("库存数量")
    private Integer stock;

    @ApiModelProperty("商品状态.1-在售 2-下架 3-删除")
    private Integer status;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh",timezone = "GMT+8") //时间格式化
    private Date createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh",timezone = "GMT+8") //时间格式化
    private Date updateTime;

    @ApiModelProperty("图片服务器url前缀")
    private String imageHost;

    @ApiModelProperty("父分类id")
    private Integer parentCategoryId;

}