package com.rootchen.mall.service;

import com.rootchen.mall.common.SR;
import com.rootchen.mall.entity.User;
import com.rootchen.mall.params.UpdateUserParams;
import com.rootchen.mall.params.UserLoginParams;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LC
 * @since 2019-05-04
 */
public interface IUserService {
    /**
     * 用户登录
     * @param userLoginParams 用户登录信息
     * @return
     */
    SR login(UserLoginParams userLoginParams,HttpSession session);

    /**
     * 用户注册
     * @param user 用户信息
     * @return
     */
    SR register(User user);

    /**
     * 邮箱激活
     * @param userName 用户名
     * @return
     */
    SR emailActivate(String userName);

    /**
     * 发送邮箱重置密码链接
     * @param email 邮箱
     * @return
     */
    SR emailResetPassword(String email);

    /**
     * 更新用户密码
     * @param updateUserParams 用户更新信息
     * @return
     */
    SR updatePassword(UpdateUserParams updateUserParams);
}
