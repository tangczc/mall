package com.rootchen.mall.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @description: 用户登录参数
 * @author: LiChen
 * @create: 2019-05-04 21:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginParams {
    @ApiModelProperty("用户名或邮箱")
    @NotBlank
    private String userName;
    @ApiModelProperty("用户密码")
    @NotBlank
    private String password;
}