package com.rootchen.mall.controller.backend;

import com.rootchen.mall.common.Const;
import com.rootchen.mall.common.SR;
import com.rootchen.mall.entity.User;
import com.rootchen.mall.params.UserLoginParams;
import com.rootchen.mall.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 管理员前端控制器
 * @author: LiChen
 * @create: 2019-05-10 15:22
 */
@RestController
@RequestMapping("/api/manage_user/")
@Api(value = "/api/manage_user/", description = "管理员用户信息")
public class UserManagerController {
    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ApiOperation(value = "登录", notes = "用户登录")
    public SR login(@RequestBody UserLoginParams userLoginParams, HttpServletRequest request) {
        SR response = iUserService.login(userLoginParams, request.getSession());
        if (!response.success()) {
            return response;
        }
        User user = (User) response.getData();
        if (user.getRole() != Const.Role.ROLE_ADMIN) {
            return SR.errorMsg("请用管理员账户登录");
        }
        return response;
    }
    @PostMapping(value = "logout.do")
    @ApiOperation(value = "登出",notes = "退出登陆")
    public SR logout(HttpServletRequest request)
    {
        request.getSession().removeAttribute(Const.CURRENT_USER);
        return SR.okMsg("退出成功");
    }


}