package com.rootchen.mall.controller.portal;


import com.rootchen.mall.common.SR;
import com.rootchen.mall.service.IProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author LC
 * @since 2019-05-16
 */
@RestController
@RequestMapping("/api/product/")
@Api(value = "/api/product/", description = "前台产品信息")
public class ProductController {
    @Autowired
    IProductService iProductService;

    @RequestMapping(value = "get_product_detail.do")
    @ApiOperation(value = "查询",notes = "获取产品详情")
    public SR getProductDetail(HttpSession session,Long productId){
        return iProductService.getPortalProductDetail(session,productId);
    }
}
