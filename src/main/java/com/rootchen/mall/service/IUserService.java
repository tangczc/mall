package com.rootchen.mall.service;

import com.rootchen.mall.common.SR;
import com.rootchen.mall.entity.User;
import com.rootchen.mall.params.UserLoginParams;

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
     * @param userLoginParams
     * @return
     */
    SR login(UserLoginParams userLoginParams);

    /**
     * 用户注册
     * @param user
     * @return
     */
    SR register(User user);
}
