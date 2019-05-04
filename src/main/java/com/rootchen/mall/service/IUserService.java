package com.rootchen.mall.service;

import com.rootchen.mall.common.SR;
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
    SR login(UserLoginParams userLoginParams);
}
