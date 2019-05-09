package com.rootchen.mall.params;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @description: 用户信息更新
 * @author: LiChen
 * @create: 2019-05-09 09:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateUserParams {
    @ApiModelProperty("用户密码，MD5加密")
    @NotBlank
    private String password;

    @ApiModelProperty("用户名")
    @NotBlank
    private String userName;

    @ApiModelProperty("email")
    @NotBlank
    private String email;

}