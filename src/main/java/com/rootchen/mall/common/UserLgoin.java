package com.rootchen.mall.common;

import com.google.gson.Gson;
import com.rootchen.mall.entity.User;

import javax.servlet.http.HttpSession;

/**
 * @description: 用于用户是否登录判断
 * @author: LiChen
 * @create: 2019-05-10 09:17
 */
public class UserLgoin {
    public static Boolean isLoginSuccess(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        System.out.println(new Gson().toJson(user));
        if(user != null){
            return true;
        }
        return false;
    }
}