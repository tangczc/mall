package com.rootchen.mall.service;

import com.rootchen.mall.common.SR;
import com.rootchen.mall.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.models.auth.In;

import javax.servlet.http.HttpSession;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author LC
 * @since 2019-05-13
 */
public interface ICategoryService {
    /**
     * 添加商品分类
     * @param session
     * @param categoryName 商品名称
     * @param parentId  父类id
     * @return
     */
    SR addCategory(HttpSession session, String categoryName, Integer parentId);

    /**
     * 更新商品分类名称
     * @param session
     * @param categoryId    商品分类id
     * @param categoryName  商品分类名称
     * @return
     */
    SR updateCategoryName(HttpSession session,Long categoryId,String categoryName);
}
