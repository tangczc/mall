package com.rootchen.mall.controller.portal;


import com.rootchen.mall.common.SR;
import com.rootchen.mall.service.IProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@Api(value = "/api/product/", tags = {"前台产品信息"})
public class ProductController {
    @Autowired
    IProductService iProductService;

    @RequestMapping(value = "get_product_detail.do",method = RequestMethod.GET)
    @ApiOperation(value = "查询",notes = "获取产品详情")
    public SR getProductDetail(@RequestParam("productId") Long productId){
        return iProductService.getPortalProductDetail(productId);
    }

    @RequestMapping(value = "search_product.do",method = RequestMethod.GET)
    @ApiOperation(value = "查询",notes = "根据商品名搜索商品")
    public SR searchProduct(@RequestParam("productId") Long productId,
                            @RequestParam("productName") String productName,
                            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        return iProductService.searchProduct(productId,productName,pageNum,pageSize);
    }
    @RequestMapping(value = "list.do",method = RequestMethod.GET)
    @ApiOperation(value = "分页",notes = "商品分页排序")
    public SR list(@RequestParam(value = "keyword", required = false) String keyword,
                                        @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                        @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                        @RequestParam(value = "orderBy", defaultValue = "") String orderBy) {
        return iProductService.getProductByKeywordCategory(keyword, categoryId, pageNum, pageSize, orderBy);
    }

}
