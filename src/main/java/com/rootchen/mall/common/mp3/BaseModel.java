package com.rootchen.mall.common.mp3;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class BaseModel extends Model {
    private Long id;

    @TableLogic
    @JsonIgnore
    private Integer deleted;

    @JsonIgnore
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @JsonIgnore
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;
}
