package com.rootchen.mall.service.impl;

import com.rootchen.mall.common.CheckUser;
import com.rootchen.mall.common.SR;
import com.rootchen.mall.common.SRCode;
import com.rootchen.mall.entity.Category;
import com.rootchen.mall.mapper.CategoryMapper;
import com.rootchen.mall.service.ICategoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LC
 * @since 2019-05-13
 */
@Service("iCategoryService")
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 添加商品分类
     * @param session
     * @param categoryName 商品名称
     * @param parentId  父类id
     * @return
     */
    @Override
    public SR addCategory(HttpSession session, String categoryName, Integer parentId){
        SR sr = checkUser(session);
        if (!sr.success()){
            return sr;
        }
        if (StringUtils.isBlank(categoryName) || parentId == null) {
            return SR.errorMsg("产品名称不能为空");
        }
        Category category = Category.builder()
                .name(categoryName)
                .parentId(parentId)
                .status(true)
                .build();
        int resultCount = categoryMapper.insert(category);
        if (resultCount > 0){
            return SR.okMsg("添加成功");
        }
        return SR.errorMsg("添加失败");
    }

    /**
     * 修改商品名称
     * @param session
     * @param categoryId    商品分类id
     * @param categoryName  商品分类名称
     * @return
     */
    @Override
    public SR updateCategoryName(HttpSession session,Long categoryId,String categoryName){
        SR sr  =checkUser(session);
        if (!sr.success()){
            return sr;
        }
        if (StringUtils.isBlank(categoryName) || categoryId == null) {
            return SR.errorMsg("产品名称或id不能为空");
        }
        Category category = new Category();
        category.setName(categoryName);
        category.setId(categoryId);
        int resultCount = categoryMapper.updateById(category);
        if (resultCount > 0){
            return SR.okMsg("修改成功");
        }
        return SR.errorMsg("修改失败");
    }

    /**
     * 检查用户登录状态
     * @param session
     * @return
     */
    private SR<Object> checkUser(HttpSession session) {
        if (!CheckUser.isLoginSuccess(session)) {
            return SR.error(SRCode.NEED_LOGIN.getCode(), "请登录");
        }
        if (!CheckUser.isAdmin(session)) {
            return SR.errorMsg("请用管理员身份操作");
        }
        return SR.ok();
    }
}
