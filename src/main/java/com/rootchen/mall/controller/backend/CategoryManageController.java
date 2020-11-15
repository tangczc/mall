package com.rootchen.mall.controller.backend;


import com.rootchen.mall.common.CheckUser;
import com.rootchen.mall.common.SR;
import com.rootchen.mall.service.ICategoryService;
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
 * @since 2019-05-13
 */
@RestController
@RequestMapping("/api/manage_category/")
@Api(value = "/api/manage_category/", description = "商品信息")
public class CategoryManageController {
    @Autowired
    private ICategoryService iCategoryService;

    @RequestMapping(value = "add_category.do", method = RequestMethod.POST)
    @ApiOperation(value = "添加", notes = "添加商品分类")
    public SR addCategory(HttpServletRequest request, @RequestParam(value = "categoryName") String categoryName, @RequestParam(value = "parentId", defaultValue = "0") Integer parentId) {
        return iCategoryService.addCategory(request.getSession(), categoryName, parentId);
    }

    @RequestMapping(value = "update_category.do", method = RequestMethod.POST)
    @ApiOperation(value = "修改", notes = "更新商品分类名称")
    public SR updateCategory(HttpServletRequest request, @RequestParam(value = "categoryId") Long categoryId, @RequestParam(value = "categoryName") String categoryName) {
        return iCategoryService.updateCategoryName(request.getSession(), categoryId, categoryName);
    }

    @RequestMapping(value = "get_category.do", method = RequestMethod.GET)
    @ApiOperation(value = "查询", notes = "查询父节点下同一级子节点信息")
    public SR getChildrenCategory(HttpServletRequest request, @RequestParam(value = "parentId", defaultValue = "0") Integer parentId) {
        return iCategoryService.getCategory(request.getSession(), parentId);
    }

    @RequestMapping(value = "get_deep_category.do", method = RequestMethod.GET)
    @ApiOperation(value = "查询", notes = "递归查询父节点下的所有子节点信息")
    public SR getDeepCategory(HttpServletRequest request, @RequestParam(value = "categoryId", defaultValue = "0") Long categoryId) {
        SR sr = CheckUser.checkUser(request.getSession());
        if (!sr.success()) {
            return sr;
        }
        return iCategoryService.getDeepCategory(categoryId);
    }
}
