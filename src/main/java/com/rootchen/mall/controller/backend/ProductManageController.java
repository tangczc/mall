package com.rootchen.mall.controller.backend;


import com.rootchen.mall.common.SR;
import com.rootchen.mall.entity.Product;
import com.rootchen.mall.service.IProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LC
 * @since 2019-05-16
 */
@RestController
@RequestMapping("/api/manage_product/")
@Api(value = "/api/manage_product/",description = "产品信息")
public class ProductManageController {
    @Autowired
    private IProductService iProductService;

    @RequestMapping(value = "save_product.do",method = RequestMethod.POST)
    @ApiOperation(value = "添加",notes = "添加商品")
    public SR saveProduct(HttpSession session, @RequestBody Product product){
        return iProductService.addOrUpdateProduct(session,product);
    }

    @RequestMapping(value = "set_product_status",method = RequestMethod.GET)
    @ApiOperation(value = "修改",notes = "修改产品上下架")
    public SR setProductStatus(HttpSession session, @RequestParam("productId") Long productId,@RequestParam("status")Integer status){
        return iProductService.setProductStatus(session,productId,status);
    }

    @RequestMapping(value = "product_list.do",method = RequestMethod.GET)
    @ApiOperation(value = "分页",notes = "商品列表(分页)")
    public SR productList(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
        return iProductService.getProductList(session,pageNum,pageSize);
    }
}
