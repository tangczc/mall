package com.rootchen.mall.params;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String userName;
    private String password;
}