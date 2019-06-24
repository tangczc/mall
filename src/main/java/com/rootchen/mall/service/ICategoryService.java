package com.rootchen.mall.service;

import com.rootchen.mall.common.SR;

import javax.servlet.http.HttpSession;
import java.util.List;

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
     *
     * @param session
     * @param categoryName 商品名称
     * @param parentId     父类id
     * @return
     */
    SR addCategory(HttpSession session, String categoryName, Integer parentId);

    /**
     * 更新商品分类名称
     *
     * @param session
     * @param categoryId   商品分类id
     * @param categoryName 商品分类名称
     * @return
     */
    SR updateCategoryName(HttpSession session, Long categoryId, String categoryName);

    /**
     * 查询父类下同一级子节点的信息
     *
     * @param parentId 父节点id
     * @param session  session
     * @return
     */
    SR getCategory(HttpSession session, Integer parentId);

    /**
     * 递归查询父节点下的所有子节点信息
     *
     * @param categoryId 父节点id
     * @return
     */
    SR<List<Long>> getDeepCategory(Long categoryId);
}
