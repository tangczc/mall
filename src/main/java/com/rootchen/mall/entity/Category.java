package com.rootchen.mall.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.rootchen.mall.common.mp3.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 *
 * </p>
 *
 * @author LC
 * @since 2019-05-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("mmall_category")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 父类别id当id=0时说明是根节点,一级类别
     */

    @ApiModelProperty("父类别id当id=0时说明是根节点,一级类别")
    @NotBlank
    private Integer parentId;

    /**
     * 类别名称
     */
    @ApiModelProperty("类别名称")
    @NotBlank
    private String name;

    /**
     * 类别状态1-正常,2-已废弃
     */
    @ApiModelProperty("类别状态1-正常,2-已废弃")
    @NotBlank
    private Boolean status;

    /**
     * 排序编号,同类展示顺序,数值相等则自然排序
     */
    @ApiModelProperty("排序编号,同类展示顺序,数值相等则自然排序")
    private Integer sortOrder;


}
