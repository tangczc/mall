package com.rootchen.mall.service.impl;

import com.rootchen.mall.common.Const;
import com.rootchen.mall.common.SR;
import com.rootchen.mall.common.SRCode;
import com.rootchen.mall.entity.User;
import com.rootchen.mall.mapper.UserMapper;
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

    @Override
    public SR login(UserLoginParams userLoginParams, HttpSession session) {
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
        user.setStatus(Const.Email.EMAIL_INACTIVATE);
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int resultCount = userMapper.insert(user);
        if (resultCount > 0) {
            try {
                iSendMsgService.sendHtmlMail(user.getEmail(),
                        "慕课商城","<h1>" +
                                "来自"+ user.getUserName() +"的账号激活邮件，激活请点击一下链接：" +
                                "</h1>" +
                                "<h3>" +
                                "<a href=" +
                                "'http://localhost:4333/api/user/activate.do?userName=" + user.getUserName() + "'>点此链接</a></h3>");
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return SR.okMsg("注册成功");
        }
        return SR.errorMsg("注册失败");
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

    public SR emailActivate(String userName){
        Integer resoultCount = userMapper.updateByUserName(userName);
        if(resoultCount > 0){
            return SR.okMsg("激活成功");
        }
        return SR.errorMsg("激活失败");
    }
}
