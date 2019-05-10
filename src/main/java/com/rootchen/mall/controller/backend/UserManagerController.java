package com.rootchen.mall.controller.backend;

import com.rootchen.mall.common.SR;
import com.rootchen.mall.entity.User;
import com.rootchen.mall.params.UserLoginParams;
import com.rootchen.mall.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @description: 管理员前端控制器
 * @author: LiChen
 * @create: 2019-05-10 15:22
 */
@RestController
@RequestMapping("/api/manage_user")
@Api(value = "/api/manage_user", description = "管理员用户信息")
public class UserManagerController {
    @Autowired
    IUserService iUserService;

    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ApiOperation(value = "登录", notes = "用户登录")
    public SR login(@RequestBody UserLoginParams userLoginParams, HttpSession session) {
        SR sr =  iUserService.login(userLoginParams, session);
        if(!sr.success()){
            return SR.errorMsg("用户名密码输入有误");
        }
        User user = (User) sr.getData();
        if (user.getRole() != 1){
            return SR.errorMsg("请用管理员账户登录");
        }
        return sr;
    }
}