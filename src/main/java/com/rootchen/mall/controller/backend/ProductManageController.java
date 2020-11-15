package com.rootchen.mall.controller.backend;


import com.rootchen.mall.common.SR;
import com.rootchen.mall.entity.Product;
import com.rootchen.mall.service.IProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author LC
 * @since 2019-05-16
 */
@RestController
@RequestMapping("/api/manage_product/")
@Api(value = "/api/manage_product/", description = "产品信息")
public class ProductManageController {
    @Autowired
    private IProductService iProductService;

    @RequestMapping(value = "save_product.do", method = RequestMethod.POST)
    @ApiOperation(value = "添加", notes = "添加商品")
    public SR saveProduct(HttpServletRequest request, @RequestBody Product product) {
        return iProductService.addOrUpdateProduct(request.getSession(), product);
    }

    @RequestMapping(value = "set_product_status.do", method = RequestMethod.GET)
    @ApiOperation(value = "修改", notes = "修改产品上下架")
    public SR setProductStatus(HttpServletRequest request, @RequestParam("productId") Long productId, @RequestParam("status") Integer status) {
        return iProductService.setProductStatus(request.getSession(), productId, status);
    }

    @RequestMapping(value = "product_list.do", method = RequestMethod.GET)
    @ApiOperation(value = "查找", notes = "商品列表(分页)")
    public SR productList(HttpServletRequest request, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return iProductService.getProductList(request.getSession(), pageNum, pageSize);
    }

    @RequestMapping(value = "product_Search.do", method = RequestMethod.GET)
    @ApiOperation(value = "查找", notes = "商品搜索(分页)")
    public SR productSearch(HttpServletRequest request,
                            @RequestParam("productId") Long productId,
                            @RequestParam("productName") String productName,
                            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return iProductService.productSearch(request.getSession(), productId, productName, pageNum, pageSize);
    }

    @RequestMapping(value = "get_product_detail.do", method = RequestMethod.GET)
    @ApiOperation(value = "查找", notes = "查找产品详情")
    public SR getProductDetail(HttpServletRequest request, @RequestParam("productId") Long productId) {
        return iProductService.getProductDetail(request.getSession(), productId);
    }

    @RequestMapping(value = "upload.do", method = RequestMethod.POST)
    @ApiOperation(value = "上传", notes = "文件上传")
    public SR upload(@RequestParam(value = "upload_file", required = false) MultipartFile multipartFile, HttpServletRequest request) {
        return iProductService.upload(request.getSession(), multipartFile, request);
    }

    @RequestMapping(value = "rich_text_img_upload.do", method = RequestMethod.POST)
    @ApiOperation(value = "上传", notes = "针对Simditor的富文本文件上传")
    public SR simditorFileUpload(@RequestParam(value = "upload_file", required = false) MultipartFile multipartFile, HttpServletRequest request, HttpServletResponse response) {
        response.addHeader("Access_Control_Allow_Headers", "X-File-Name");
        return iProductService.upload(request.getSession(), multipartFile, request);
    }
}
