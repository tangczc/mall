package com.rootchen.mall.params;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @description: 用户注册
 * @author: LiChen
 * @create: 2019-05-10 10:38
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserParams {
    @ApiModelProperty("用户名")
    @NotBlank
    private String userName;

    @ApiModelProperty("用户密码，MD5加密")
    @NotBlank
    private String password;

    @ApiModelProperty("email")
    @NotBlank
    private String email;

    @ApiModelProperty("手机号")
    @NotBlank
    private String phone;

    @ApiModelProperty("邮箱激活状态:0-未激活，1-激活")
    @NotBlank
    @JsonIgnore
    private Integer status;
}