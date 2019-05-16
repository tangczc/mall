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
 * @since 2019-05-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("mall_product")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product extends BaseModel {

    private static final long serialVersionUID = 1L;

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


}
