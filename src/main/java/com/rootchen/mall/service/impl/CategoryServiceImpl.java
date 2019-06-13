package com.rootchen.mall.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.rootchen.mall.common.CheckUser;
import com.rootchen.mall.common.SR;
import com.rootchen.mall.entity.Category;
import com.rootchen.mall.mapper.CategoryMapper;
import com.rootchen.mall.service.ICategoryService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author LC
 * @since 2019-05-13
 */
@Service("iCategoryService")
@Log4j2
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 添加商品分类
     *
     * @param session
     * @param categoryName 商品名称
     * @param parentId     父类id
     * @return
     */
    @Override
    public SR<String> addCategory(HttpSession session, String categoryName, Integer parentId) {
        SR sr = CheckUser.checkUser(session);
        if (!sr.success()) {
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
        if (resultCount > 0) {
            return SR.okMsg("添加成功");
        }
        return SR.errorMsg("添加失败");
    }

    /**
     * 更新商品分类名称
     *
     * @param session
     * @param categoryId   商品分类id
     * @param categoryName 商品分类名称
     * @return
     */
    @Override
    public SR<String> updateCategoryName(HttpSession session, Long categoryId, String categoryName) {
        SR sr = CheckUser.checkUser(session);
        if (!sr.success()) {
            return sr;
        }
        if (StringUtils.isBlank(categoryName) || categoryId == null) {
            return SR.errorMsg("产品名称或id不能为空");
        }
        Category category = new Category();
        category.setName(categoryName);
        category.setId(categoryId);
        int resultCount = categoryMapper.updateById(category);
        if (resultCount > 0) {
            return SR.okMsg("修改成功");
        }
        return SR.errorMsg("修改失败");
    }

    /**
     * 查询父类下子节点的信息
     *
     * @param parentId 父节点id
     * @return
     */
    @Override
    public SR<List<Category>> getCategory(HttpSession session, Integer parentId) {
        SR sr = CheckUser.checkUser(session);
        if (!sr.success()) {
            return sr;
        }
        List<Category> categoryList = categoryMapper.selectByParentId(parentId);
        if (CollectionUtils.isEmpty(categoryList)) {
            log.info("该品类下无商品");
            return SR.ok("该品类下无商品", categoryList);
        }
        return SR.ok(categoryList);

    }

    /**
     * 递归查询父节点下的所有子节点信息
     *
     * @param categoryId 父节点id
     * @return
     */
    @Override
    public SR<List<Long>> getDeepCategory(Long categoryId) {

        Set<Category> categorySet = Sets.newHashSet();
        findChildrenCategory(categorySet, categoryId);
        List<Long> categoryIdList = Lists.newArrayList();
        if (categoryId != null) {
            for (Category categoryItem : categorySet) {
                categoryIdList.add(categoryItem.getId());
            }
        }
        return SR.ok(categoryIdList);
    }


    /**
     * 递归查询子节点
     *
     * @param categorySet
     * @param categoryId
     * @return
     */
    private Set<Category> findChildrenCategory(Set<Category> categorySet, Long categoryId) {
        //通过categoryId查询出当前的category 放入set集合
        Category category = categoryMapper.selectById(categoryId);
        if (category != null) {
            categorySet.add(category);
        }
        //通过查询出来的category，查询当前category下是否还有子节点
        List<Category> categoryList = categoryMapper.selectByParentId(categoryId.intValue());
        //如果有递归继续，如果没有返回categorySet
        for (Category categoryItem : categoryList) {
            findChildrenCategory(categorySet, categoryItem.getId());
        }
        return categorySet;
    }
}
