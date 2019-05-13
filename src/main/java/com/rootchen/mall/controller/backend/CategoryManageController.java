package com.rootchen.mall.controller.backend;


import com.rootchen.mall.common.SR;
import com.rootchen.mall.service.ICategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
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

    @RequestMapping(value = "add_category.do",method = RequestMethod.POST)
    @ApiOperation(value = "添加",notes = "添加商品分类")
    public SR addCategory(HttpSession session, @RequestParam(value = "categoryName") String categoryName,@RequestParam(value = "parentId",defaultValue = "0") Integer parentId){
        return iCategoryService.addCategory(session,categoryName,parentId);
    }

    @RequestMapping(value = "update_category.do", method = RequestMethod.POST)
    @ApiOperation(value = "修改", notes = "更新商品分类名称")
    public SR updateCategory(HttpSession session, @RequestParam(value = "categoryId") Long categoryId, @RequestParam(value = "categoryName") String categoryName) {
        return iCategoryService.updateCategoryName(session,categoryId,categoryName);
    }

}
