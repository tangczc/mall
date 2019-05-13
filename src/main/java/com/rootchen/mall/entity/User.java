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

    @ApiModelProperty("用户名")
    @NotBlank
    private String userName;

    @ApiModelProperty("用户密码，MD5加密")
    @NotBlank
    @JsonIgnore
    private String password;

    @ApiModelProperty("email")
    @NotBlank
    private String email;

    @ApiModelProperty("手机号")
    @NotBlank
    private String phone;

    @ApiModelProperty("角色1-管理员,0-普通用户")
    @NotBlank
    @JsonIgnore
    private Integer role;

    @ApiModelProperty("邮箱激活状态:0-未激活，1-激活")
    @NotBlank
    private Integer status;

}
