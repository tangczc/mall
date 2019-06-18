package com.rootchen.mall.controller.portal;


import com.rootchen.mall.common.SR;
import com.rootchen.mall.service.ICartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LC
 * @since 2019-06-17
 */
@RestController
@RequestMapping("/api/cart/")
@Api(value = "/api/cart/",description = "购物车信息")
public class CartController {
    @Autowired
    private ICartService iCartService;

    @RequestMapping(value = "add_cart",method = RequestMethod.POST)
    @ApiOperation(value = "添加",notes = "购物车添加")
    public SR addCart(HttpSession session, @Param("count") Integer count, @Param("productId") Long productId){
        return iCartService.addCart(session,count,productId);
    }

}
