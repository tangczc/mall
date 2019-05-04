package com.rootchen.mall.service.impl;

import com.rootchen.mall.entity.User;
import com.rootchen.mall.mapper.UserMapper;
import com.rootchen.mall.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LC
 * @since 2019-05-04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
