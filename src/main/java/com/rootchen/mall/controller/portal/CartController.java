package com.rootchen.mall.controller.portal;


import com.rootchen.mall.common.CheckUser;
import com.rootchen.mall.common.Const;
import com.rootchen.mall.common.SR;
import com.rootchen.mall.common.SRCode;
import com.rootchen.mall.entity.User;
import com.rootchen.mall.service.ICartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author LC
 * @since 2019-06-17
 */
@RestController
@RequestMapping("/api/cart/")
@Api(value = "/api/cart/", description = "购物车信息")
public class CartController {
    @Autowired
    private ICartService iCartService;

    @RequestMapping(value = "add_cart.do", method = RequestMethod.POST)
    @ApiOperation(value = "添加", notes = "购物车添加")
    public SR addCart(HttpServletRequest request, @RequestParam("count") Integer count, @RequestParam("productId") Long productId) {
        return iCartService.addCart(request.getSession(), count, productId);
    }

    @RequestMapping(value = "search_cart.do", method = RequestMethod.GET)
    @ApiOperation(value = "查询", notes = "查询购物车")
    public SR searchCart(HttpServletRequest request) {
        if (!CheckUser.isLoginSuccess(request.getSession())) {
            return SR.error(SRCode.NEED_LOGIN.getCode(), SRCode.NEED_LOGIN.getDesc());
        }
        User user = (User) request.getSession().getAttribute(Const.CURRENT_USER);
        return iCartService.list(user.getId());
    }

    @RequestMapping(value = "update_cart.do", method = RequestMethod.POST)
    @ApiOperation(value = "更新", notes = "更新购物车")
    public SR updateCart(HttpServletRequest request, @RequestParam("count") Integer count, @RequestParam("productId") Long productId) {
        return iCartService.updateCart(request.getSession(), count, productId);
    }


    @RequestMapping(value = "delete_product", method = RequestMethod.POST)
    @ApiOperation(value = "删除", notes = "删除购物车商品")
    public SR deleteProduct(HttpServletRequest request, String productIds) {
        return iCartService.deleteProduct(request.getSession(), productIds);
    }

    @RequestMapping(value = "select_all.do", method = RequestMethod.GET)
    @ApiOperation(value = "选择", notes = "全选")
    public SR selectAll(HttpServletRequest request) {
        return iCartService.selectOrUnselectAll(request.getSession(), null, Const.Cart.CHECKED);
    }

    @RequestMapping(value = "un_select_all.do", method = RequestMethod.GET)
    @ApiOperation(value = "选择", notes = "全反选")
    public SR unSelectAll(HttpServletRequest request) {
        return iCartService.selectOrUnselectAll(request.getSession(), null, Const.Cart.UN_CHECKED);
    }

    @RequestMapping(value = "select.do", method = RequestMethod.GET)
    @ApiOperation(value = "选择", notes = "单选")
    public SR select(HttpServletRequest request, Long productId) {
        return iCartService.selectOrUnselectAll(request.getSession(), productId, Const.Cart.CHECKED);
    }

    @RequestMapping(value = "un_select.do", method = RequestMethod.GET)
    @ApiOperation(value = "选择", notes = "单反选")
    public SR unSelect(HttpServletRequest request, Long productId) {
        return iCartService.selectOrUnselectAll(request.getSession(), productId, Const.Cart.UN_CHECKED);
    }

    @RequestMapping(value = "get_cart_product_count.do", method = RequestMethod.GET)
    @ApiOperation(value = "查询", notes = "获取购物车商品总数")
    public SR getCartProductCount(HttpServletRequest request) {
        return iCartService.getCartProductCount(request.getSession());
    }
}
