package com.rootchen.mall.controller.portal;


import com.rootchen.mall.common.SR;
import com.rootchen.mall.params.ShippingParams;
import com.rootchen.mall.service.IShippingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 前端控制器
 * </p>·
 *
 * @author LC
 * @since 2019-06-18
 */
@RestController
@RequestMapping("/api/shipping/")
@Api(value = "/api/shipping/", tags = {"收货地址"})
public class ShippingController {
    @Autowired
    private IShippingService iShippingService;

    @RequestMapping(value = "add_shipping.do", method = RequestMethod.POST)
    @ApiOperation(value = "添加", notes = "添加收获地址")
    public SR addShipping(HttpServletRequest request, @RequestBody ShippingParams shippingParams) {
        return iShippingService.addShipping(request.getSession(), shippingParams);
    }

    @RequestMapping(value = "list_shipping.do", method = RequestMethod.GET)
    @ApiOperation(value = "查询", notes = "收获地址列表")
    public SR showShipping(HttpServletRequest request, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                           @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return iShippingService.showShipping(request.getSession(), pageNum, pageSize);
    }

    @RequestMapping(value = "update_shipping.do", method = RequestMethod.POST)
    @ApiOperation(value = "更新", notes = "更新收获地址")
    public SR updateShipping(HttpServletRequest request, @RequestBody ShippingParams shippingParams) {
        return iShippingService.updateShipping(request.getSession(), shippingParams);
    }

    @RequestMapping(value = "show_shipping.do", method = RequestMethod.GET)
    @ApiOperation(value = "查询", notes = "收获地址详情")
    public SR showShipping(HttpServletRequest request, @RequestParam("shippingId") Long shippingId) {
        return iShippingService.showShippingInfo(request.getSession(), shippingId);
    }
}
