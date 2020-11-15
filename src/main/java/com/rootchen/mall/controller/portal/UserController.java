package com.rootchen.mall.controller.portal;


import com.rootchen.mall.common.Const;
import com.rootchen.mall.common.SR;
import com.rootchen.mall.params.RegisterUserParams;
import com.rootchen.mall.params.UpdateUserParams;
import com.rootchen.mall.params.UserLoginParams;
import com.rootchen.mall.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author LC
 * @since 2019-05-04
 */
@RestController
@RequestMapping("/api/user/")
@Api(value = "/api/user/", description = "用户信息")
public class UserController {

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ApiOperation(value = "登录", notes = "用户登录")
    public SR login(@RequestBody UserLoginParams userLoginParams, HttpServletRequest request) {
        return iUserService.login(userLoginParams, request.getSession());
    }

    @RequestMapping(value = "register.do", method = RequestMethod.POST)
    @ApiOperation(value = "注册", notes = "用户注册")
    public SR register(@RequestBody RegisterUserParams registerUserParams) {
        return iUserService.register(registerUserParams);
    }

    @RequestMapping(value = "login_out.do", method = RequestMethod.GET)
    @ApiOperation(value = "登出", notes = "用户登出")
    public SR loginOut(HttpServletRequest request) {
        request.getSession().removeAttribute(Const.CURRENT_USER);
        return SR.ok("退出成功");
    }

    @RequestMapping(value = "activate.do", method = RequestMethod.GET)
    @ApiOperation(value = "激活", notes = "邮箱激活")
    public SR activate(@RequestParam("userName") String userName) {
        return iUserService.emailActivate(userName);
    }

    @RequestMapping(value = "reset_password.do", method = RequestMethod.GET)
    @ApiOperation(value = "重置", notes = "发送邮箱密码重置链接")
    public SR resetPassword(@RequestParam("email") String email) {
        return iUserService.emailResetPassword(email);
    }

    @RequestMapping(value = "update_password.do", method = RequestMethod.POST)
    @ApiOperation(value = "更新", notes = "更新密码")
    public SR updatePassword(@RequestBody UpdateUserParams updateUserParams) {
        return iUserService.updatePassword(updateUserParams);
    }

    @RequestMapping(value = "update_user_info.do", method = RequestMethod.POST)
    @ApiOperation(value = "更新", notes = "更新用户信息")
    public SR updateUserInfo(@RequestBody UpdateUserParams updateUserParams, HttpServletRequest request) {
        return iUserService.updateUserInfo(updateUserParams, request.getSession());
    }

    @RequestMapping(value = "get_user_information.do", method = RequestMethod.POST)
    @ApiOperation(value = "查询", notes = "查询当前用户信息")
    public SR getUserInformation(HttpServletRequest request) {
        return iUserService.getUserInformation(request.getSession());
    }
}
