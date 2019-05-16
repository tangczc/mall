package com.rootchen.mall.common;

import com.google.gson.Gson;
import com.rootchen.mall.entity.User;

import javax.servlet.http.HttpSession;

/**
 * @description: 用户状态
 * @author: LiChen
 * @create: 2019-05-10 09:17
 */
public class CheckUser {
    /**
     * 用户是否登录
     * @param session
     * @return Boolean
     */
    public static Boolean isLoginSuccess(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        System.out.println(new Gson().toJson(user));
        if(user != null){
            return true;
        }
        return false;
    }

    /**
     * 用户是否是管理员
     * @param session
     * @return Boolean
     */
    public static Boolean isAdmin(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user.getRole() == Const.Role.ROLE_ADMIN){
            return true;
        }
        return false;
    }

    /**
     * 检查用户登录状态以及是否是管理员
     *
     * @param session
     * @return
     */
    public static SR<String> checkUser(HttpSession session) {
        if (!CheckUser.isLoginSuccess(session)) {
            return SR.error(SRCode.NEED_LOGIN.getCode(), "请登录");
        }
        if (!CheckUser.isAdmin(session)) {
            return SR.errorMsg("请用管理员身份操作");
        }
        return SR.okMsg("校验成功");
    }
}