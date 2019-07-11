package com.rootchen.mall.controller.portal;


import com.rootchen.mall.common.SR;
import com.rootchen.mall.service.IOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author LC
 * @since 2019-06-27
 */
@RestController
@RequestMapping("/api/order/")
@Api(value = "/api/order/", description = "订单信息")
public class OrderController {

    @Autowired
    private IOrderService iOrderService;

    @RequestMapping(value = "create.do", method = RequestMethod.GET)
    @ApiOperation(value = "创建", notes = "创建订单")
    public SR create(HttpSession session, @RequestParam("shippingId") Long shippingId) {
        return iOrderService.create(session, shippingId);
    }

    @RequestMapping(value = "cancel.do", method = RequestMethod.GET)
    @ApiModelProperty(value = "取消", notes = "取消订单")
    public SR cancel(HttpSession session, @RequestParam("orderNumber") Long orderNumber) {
        return iOrderService.cancel(session, orderNumber);
    }

    @RequestMapping(value = "get_order_cart_product.do", method = RequestMethod.GET)
    @ApiOperation(value = "获取购物车产品", notes = "购物车明细")
    public SR getOrderCartProduct(HttpSession session, Long orderNumber) {
        return iOrderService.getOrderCartProduct(session, orderNumber);
    }

    @RequestMapping(value = "order_list", method = RequestMethod.GET)
    @ApiOperation(value = "查询", notes = "用户订单列表")
    public SR getOrderList(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize) {
        return iOrderService.getOrderList(session, pageNum, pageSize);
    }
}
