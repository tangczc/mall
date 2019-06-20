package com.rootchen.mall.controller.portal;


import com.rootchen.mall.common.SR;
import com.rootchen.mall.params.ShippingParams;
import com.rootchen.mall.service.IShippingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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
 * @since 2019-06-18
 */
@RestController
@RequestMapping("/api/shipping/")
@Api(value = "/api/shipping/",description = "收货地址")
public class ShippingController {
    @Autowired
    private IShippingService iShippingService;

    @RequestMapping(value = "add_shipping.do",method = RequestMethod.POST)
    @ApiOperation(value = "添加",notes = "添加收获地址")
    public SR addShipping(HttpSession session, @RequestBody ShippingParams shippingParams){
        return iShippingService.addShipping(session,shippingParams);
    }

}
