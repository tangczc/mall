package com.rootchen.mall.controller.portal;


import com.rootchen.mall.common.SR;
import com.rootchen.mall.entity.User;
import com.rootchen.mall.params.UserLoginParams;
import com.rootchen.mall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LC
 * @since 2019-05-04
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    IUserService iUserService;

    @RequestMapping(value = "login.do" ,method = RequestMethod.POST)
    public SR login(@RequestBody UserLoginParams userLoginParams){
        return iUserService.login(userLoginParams);
    }

    @RequestMapping(value = "register.do",method = RequestMethod.POST)
    public SR register(@RequestBody User user){
        return iUserService.register(user);
    }
}
