package com.rootchen.mall.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * @since 2019-05-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("mmall_user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    @NotBlank
    private String userName;

    /**
     * 用户密码，MD5加密
     */
    @ApiModelProperty("用户密码，MD5加密")
    @NotBlank
    private String password;

    /**
     * 邮箱
     */
    @ApiModelProperty("email")
    @NotBlank
    private String email;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    @NotBlank
    private String phone;

    /**
     * 找回密码问题
     */
    @ApiModelProperty("找回密码问题")
    @NotBlank
    private String question;

    /**
     * 找回密码答案
     */
    @ApiModelProperty("找回密码答案")
    @NotBlank
    private String answer;

    /**
     * 角色0-管理员,1-普通用户
     */
    @ApiModelProperty("角色0-管理员,1-普通用户")
    @NotBlank
    @JsonIgnore
    private Integer role;

}
