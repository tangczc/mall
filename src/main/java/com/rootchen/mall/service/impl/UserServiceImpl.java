package com.rootchen.mall.service.impl;

import com.rootchen.mall.common.SR;
import com.rootchen.mall.mapper.UserMapper;
import com.rootchen.mall.params.UserLoginParams;
import com.rootchen.mall.service.IUserService;
import com.rootchen.mall.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LC
 * @since 2019-05-04
 */
@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public SR login(UserLoginParams userLoginParams){
       String password =  userMapper.selectByUserName(userLoginParams.getUserName());

       if(StringUtils.isNotBlank(password) &&  MD5Util.MD5EncodeUtf8(userLoginParams.getPassword()).equals(password)){
           return SR.ok("登陆成功");
       }
        return SR.error("用户名或者密码不正确");
    }

}
