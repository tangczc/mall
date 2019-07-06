package com.rootchen.mall.controller.portal;


import com.rootchen.mall.common.SR;
import com.rootchen.mall.service.IPayInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LC
 * @since 2019-06-25
 */
@RestController
@RequestMapping("/api/pay-info/")
@Api(value = "/api/pay-info/" ,description = "支付")
public class PayInfoController {

    @Autowired
    private IPayInfoService iPayInfoService;

    @RequestMapping(value = "pay.do",method = RequestMethod.POST)
    @ApiModelProperty(value = "支付",notes = "支付宝支付")
    public SR aliPay(HttpSession session, HttpServletRequest request, @RequestParam("orderNumber") Long orderNumber){
        return iPayInfoService.aliPay(session,request,orderNumber);
    }


    @RequestMapping(value = "pay_status.do",method = RequestMethod.GET)
    @ApiModelProperty(value = "查询",notes = "查询支付状态")
    public SR checkPayStatus(HttpSession session,Long orderNumber){
        return iPayInfoService.checkPayStatus(session,orderNumber);
    }
}
