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
}