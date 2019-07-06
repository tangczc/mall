package com.rootchen.mall.controller.portal;


import com.rootchen.mall.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LC
 * @since 2019-06-27
 */
@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private IOrderService iOrderService;


}
