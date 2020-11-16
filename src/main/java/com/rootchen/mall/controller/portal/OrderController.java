package com.rootchen.mall.controller.portal;


import com.rootchen.mall.common.SR;
import com.rootchen.mall.service.IOrderService;
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
 * @since 2019-06-27
 */
@RestController
@RequestMapping("/api/order/")
@Api(value = "/api/order/", tags = {"订单信息"})
public class OrderController {

    @Autowired
    private IOrderService iOrderService;

    @RequestMapping(value = "create.do", method = RequestMethod.GET)
    @ApiOperation(value = "创建", notes = "创建订单")
    public SR create(HttpServletRequest request, @RequestParam("shippingId") Long shippingId) {
        return iOrderService.create(request.getSession(), shippingId);
    }

    @RequestMapping(value = "cancel.do", method = RequestMethod.GET)
    @ApiOperation(value = "取消", notes = "取消订单")
    public SR cancel(HttpServletRequest request, @RequestParam("orderNumber") Long orderNumber) {
        return iOrderService.cancel(request.getSession(), orderNumber);
    }

    @RequestMapping(value = "get_order_cart_product.do", method = RequestMethod.GET)
    @ApiOperation(value = "获取购物车产品", notes = "购物车明细")
    public SR getOrderCartProduct(HttpServletRequest request, Long orderNumber) {
        return iOrderService.getOrderCartProduct(request.getSession(), orderNumber);
    }

    @RequestMapping(value = "order_list", method = RequestMethod.GET)
    @ApiOperation(value = "查询", notes = "用户订单列表")
    public SR getOrderList(HttpServletRequest request, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return iOrderService.getOrderList(request.getSession(), pageNum, pageSize);
    }

    @RequestMapping(value = "order_detail", method = RequestMethod.GET)
    @ApiOperation(value = "查询", notes = "获取订单详情")
    public SR getOrderDetail(HttpServletRequest request, @RequestParam("orderNumber") Long orderNumber) {
        return iOrderService.getOrderDetail(request.getSession(), orderNumber);
    }
}
