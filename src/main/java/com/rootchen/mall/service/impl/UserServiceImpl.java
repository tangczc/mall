package com.rootchen.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rootchen.mall.common.CheckUser;
import com.rootchen.mall.common.Const;
import com.rootchen.mall.common.SR;
import com.rootchen.mall.common.SRCode;
import com.rootchen.mall.entity.User;
import com.rootchen.mall.mapper.UserMapper;
import com.rootchen.mall.params.RegisterUserParams;
import com.rootchen.mall.params.UpdateUserParams;
import com.rootchen.mall.params.UserLoginParams;
import com.rootchen.mall.service.ISendMsgService;
import com.rootchen.mall.service.IUserService;
import com.rootchen.mall.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
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

    @Autowired
    ISendMsgService iSendMsgService;

    /**
     * 登录
     *
     * @param userLoginParams 用户登录信息
     * @param session
     * @return user
     */
    @Override
    public SR<User> login(UserLoginParams userLoginParams, HttpSession session) {
        User user;
        if (userLoginParams.getUserName().contains("@")) {
            user = userMapper.selectByEmail(userLoginParams.getUserName(), MD5Util.MD5EncodeUtf8(userLoginParams.getPassword()));
            if (user != null) {
                session.setAttribute(Const.CURRENT_USER, user);
                return SR.ok("登陆成功", user);
            }
        } else {
            user = userMapper.selectByUserName(userLoginParams.getUserName(), MD5Util.MD5EncodeUtf8(userLoginParams.getPassword()));
            if (user != null) {
                session.setAttribute(Const.CURRENT_USER, user);
                return SR.ok("登陆成功", user);
            }
        }
        return SR.errorMsg("用户名或者密码不正确");
    }

    /**
     * 注册
     *
     * @param registerUserParams 用户注册信息
     * @return String
     */
    @Override
    public SR<String> register(RegisterUserParams registerUserParams) {
        SR validResponse = this.checkValid(registerUserParams.getUserName(), Const.USERNAME);
        if (!validResponse.success()) {
            return validResponse;
        }
        validResponse = this.checkValid(registerUserParams.getEmail(), Const.EMAIL);
        if (!validResponse.success()) {
            return validResponse;
        }

        User user = User.builder()
                .password(MD5Util.MD5EncodeUtf8(registerUserParams.getPassword()))
                .email(registerUserParams.getEmail())
                .userName(registerUserParams.getUserName())
                .phone(registerUserParams.getPhone())
                .role(Const.Role.ROLE_CUSTOMER)
                .status(Const.Email.EMAIL_INACTIVATE)
                .build();
        int resultCount = userMapper.insert(user);
        if (resultCount > 0) {
            try {
                iSendMsgService.sendHtmlMail(user.getEmail(),
                        "慕课商城", "<h1>" +
                                "来自" + user.getUserName() + "的账号激活邮件，激活请点击一下链接：" +
                                "</h1>" +
                                "<h3>" +
                                "<a href=" +
                                "'http://localhost:4333/api/user/activate.do?userName=" + user.getUserName() + "'>点此链接</a></h3>");
            } catch (MessagingException e) {
                System.out.println("发送失败" + e);
            }
            return SR.okMsg("注册成功");
        }
        return SR.errorMsg("注册失败");
    }

    /**
     * 邮箱激活
     *
     * @param userName 用户名
     * @return String
     */
    @Override
    public SR<String> emailActivate(String userName) {
        Integer resultCount = userMapper.updateByUserName(userName);
        if (resultCount > 0) {
            return SR.okMsg("激活成功");
        }
        return SR.errorMsg("激活失败");
    }

    /**
     * 发生邮箱充值密码链接
     *
     * @param email 邮箱
     * @return String
     */
    @Override
    public SR<String> emailResetPassword(String email) {
        try {
            iSendMsgService.sendHtmlMail(email, "密码重置", "<h1>这是一份重置密码邮件如不是本人操作请忽略<br>，充值密码点击以下链接：</h1>" +
                    "<h3><a href='http://localhost:4333/api/user/activate.do?email='" +
                    email + ">点此链接</a></h3>");
            return SR.okMsg("密码重置链接以发送您的邮箱请注意查收");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return SR.errorMsg("邮箱发送失败，请查看邮箱是否填写正确");
    }

    /**
     * 更新用户密码
     *
     * @param updateUserParams 用户更新信息
     * @return String
     */
    @Override
    public SR<String> updatePassword(UpdateUserParams updateUserParams) {
        User user = User.builder()
                .password(MD5Util.MD5EncodeUtf8(updateUserParams.getPassword()))
                .build();
        int resultCount = userMapper.update(user, new QueryWrapper<User>().lambda().eq(User::getEmail, updateUserParams.getEmail()));
        if (resultCount > 0) {
            return SR.okMsg("密码修改成功");
        }
        return SR.errorMsg("密码修改失败");
    }

    /**
     * 更新用户信息
     *
     * @param updateUserParams 用户更新信息
     * @param session          HttpSession
     * @return Object
     */
    @Override
    public SR<Object> updateUserInfo(UpdateUserParams updateUserParams, HttpSession session) {
        if (!CheckUser.isLoginSuccess(session)) {
            return SR.error(SRCode.NEED_LOGIN.getCode(), "请先登录");
        }
        SR checkValidResponse = checkValid(updateUserParams.getUserName(), Const.USERNAME);
        if (!checkValidResponse.success()) {
            return checkValidResponse;
        }
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        user.setUserName(updateUserParams.getUserName());
        if (updateUserParams.getPassword() != null) {
            user.setPassword(MD5Util.MD5EncodeUtf8(updateUserParams.getPassword()));
        }
        int resultCount = userMapper.updateById(user);
        if (resultCount > 0) {
            session.setAttribute(Const.CURRENT_USER, user);
            return SR.okMsg("更新成功");
        }
        return SR.errorMsg("更新失败");
    }


    /**
     * 查询当前用户信息
     *
     * @param session
     * @return
     */
    public SR<Object> getUserInformation(HttpSession session) {
        if (!CheckUser.isLoginSuccess(session)) {
            return SR.error(SRCode.NEED_LOGIN.getCode(), "请先登录");
        }
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        user = userMapper.selectById(user.getId());
        if (user != null) {
            return SR.ok("查询成功", user);
        }
        return SR.error("该用户不存在", null);
    }

    /**
     * 校验
     *
     * @param str  用户名
     * @param type 类型
     * @return String
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
