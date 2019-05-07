package com.rootchen.mall.service.impl;

import com.rootchen.mall.common.Const;
import com.rootchen.mall.common.SR;
import com.rootchen.mall.common.SRCode;
import com.rootchen.mall.entity.User;
import com.rootchen.mall.mapper.UserMapper;
import com.rootchen.mall.params.UserLoginParams;
import com.rootchen.mall.service.IUserService;
import com.rootchen.mall.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * <p>
 * 服务实现类
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
    public SR login(UserLoginParams userLoginParams, HttpSession session) {
        User user;
        if (userLoginParams.getUserName().contains("@")) {
            user = userMapper.selectByEmail(userLoginParams.getUserName(), MD5Util.MD5EncodeUtf8(userLoginParams.getPassword()));
            if (user != null) {
                session.setAttribute(Const.CURRENT_USER, user);
                return SR.okMsg("登陆成功");
            }
        } else {
            user = userMapper.selectByUserName(userLoginParams.getUserName(), MD5Util.MD5EncodeUtf8(userLoginParams.getPassword()));
            if (user != null) {
                session.setAttribute(Const.CURRENT_USER, user);
                return SR.okMsg("登陆成功");
            }
        }
        return SR.errorMsg("用户名或者密码不正确");
    }

    @Override
    public SR register(User user) {
        SR validRespons = this.checkValid(user.getUserName(), Const.USERNAME);
        if (!validRespons.success()) {
            return validRespons;
        }
        validRespons = this.checkValid(user.getEmail(), Const.EMAIL);
        if (!validRespons.success()) {
            return validRespons;
        }
        user.setRole(Const.Role.ROLE_CUSTOMER);
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int resultCount = userMapper.insert(user);
        if (resultCount > 0) {
            return SR.ok("注册成功");
        }
        return SR.error("注册失败");
    }

    /**
     * 校验
     *
     * @param str  用户名
     * @param type 类型
     * @return
     */
    private SR<String> checkValid(String str, String type) {
        if (StringUtils.isNotBlank(type)) {
            //校验
            if (Const.USERNAME.equals(type)) {
                int resultCount = userMapper.checkUserName(str);
                if (resultCount > 0) {
                    return SR.errorMsg("用户名已经存在");
                }
            }
            if (Const.EMAIL.equals(type)) {
                int resultCount = userMapper.checkEmail(str);
                if (resultCount > 0) {
                    return SR.errorMsg("邮箱已经存在");
                }
            }
        } else {
            return SR.errorMsg("参数错误");
        }
        return SR.okMsg("校验成功");
    }
}
