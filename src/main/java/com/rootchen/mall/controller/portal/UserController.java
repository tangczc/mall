package com.rootchen.mall.controller.portal;


import com.rootchen.mall.common.Const;
import com.rootchen.mall.common.SR;
import com.rootchen.mall.entity.User;
import com.rootchen.mall.params.UserLoginParams;
import com.rootchen.mall.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author LC
 * @since 2019-05-04
 */
@RestController
@RequestMapping("/api/user")
@Api(value = "/api/user", description = "用户信息")
public class UserController {

    @Autowired
    IUserService iUserService;

    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ApiModelProperty(value = "登录", notes = "用户登录")
    public SR login(@RequestBody UserLoginParams userLoginParams, HttpSession session) {
        return iUserService.login(userLoginParams, session);
    }

    @RequestMapping(value = "register.do", method = RequestMethod.POST)
    @ApiModelProperty(value = "注册", notes = "用户注册")
    public SR register(@RequestBody User user) {
        return iUserService.register(user);
    }

    @RequestMapping(value = "login_out.do", method = RequestMethod.GET)
    @ApiModelProperty(value = "登出", notes = "用户登出")
    public SR loginOut(HttpSession session) {
        session.removeAttribute(Const.CURRENT_USER);
        return SR.ok("退出成功");
    }

    @RequestMapping(value = "activate.do")
    @ApiModelProperty(value = "激活" ,notes="邮箱激活")
    public SR activate(@Param("userName") String userName){
        return iUserService.emailActivate(userName);
    }

}
